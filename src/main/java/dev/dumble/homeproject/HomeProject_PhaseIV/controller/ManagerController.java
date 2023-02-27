package dev.dumble.homeproject.HomeProject_PhaseIV.controller;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Manager;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Specialist;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.SpecialistStatus;
import dev.dumble.homeproject.HomeProject_PhaseIV.mapper.ManagerDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.AssistanceService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.ClientService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.ManagerService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.SpecialistService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/manager")
public class ManagerController {

	private ManagerService managerService;
	private SpecialistService specialistService;
	private ClientService clientService;
	private AssistanceService assistanceService;

	@PostMapping("/create")
	public ResponseEntity<Manager> registerClient(@RequestBody ManagerDTO managerDTO) {
		var manager = managerDTO.toManager();
		var optionalManager = Optional.ofNullable(managerService.create(manager));

		return ResponseEntity.of(optionalManager);
	}

	@PostMapping("/accept-specialist")
	public ResponseEntity<Specialist> acceptSpecialist(@RequestParam(value = "specialist_id") Long id) {
		var specialist = specialistService.findById(id);

		var updatedSpecialist = specialistService.changeStatus(specialist, SpecialistStatus.ACCEPTED);
		var optionalSpecialist = Optional.ofNullable(updatedSpecialist);

		return ResponseEntity.of(optionalSpecialist);
	}

	@PostMapping("/add-assistance")
	public ResponseEntity<Specialist> addAssistances(@RequestParam(value = "specialist_id") Long specialistId,
													 @RequestParam(value = "assistance_id") Long assistanceId) {
		var specialistById = specialistService.findById(specialistId);
		var assistanceById = assistanceService.findById(assistanceId);
		specialistById.addAssistance(assistanceById);

		var specialist = specialistService.addAssistance(specialistById, assistanceById);
		var optionalSpecialist = Optional.of(specialist);

		return ResponseEntity.of(optionalSpecialist);
	}

	@PostMapping("/remove-assistance")
	public ResponseEntity<Specialist> removeAssistances(@RequestParam(value = "specialist_id") Long specialistId,
														@RequestParam(value = "assistance_id") Long assistanceId) {
		var specialistById = specialistService.findById(specialistId);
		var assistanceById = assistanceService.findById(assistanceId);

		var specialist = specialistService.removeAssistance(specialistById, assistanceById);
		var optionalSpecialist = Optional.of(specialist);

		return ResponseEntity.of(optionalSpecialist);
	}
}
