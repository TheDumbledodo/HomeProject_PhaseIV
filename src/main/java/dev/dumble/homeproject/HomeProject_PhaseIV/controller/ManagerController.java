package dev.dumble.homeproject.HomeProject_PhaseIV.controller;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.ManagerDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.users.Client;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.users.Manager;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.users.Specialist;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.SpecialistStatus;
import dev.dumble.homeproject.HomeProject_PhaseIV.filter.request.SearchRequest;
import dev.dumble.homeproject.HomeProject_PhaseIV.mappers.ManagerMapper;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.AssistanceService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.ClientService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.ManagerService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.SpecialistService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j @RestController
@AllArgsConstructor
@RequestMapping("/api/v1/manager")
public class ManagerController {

	private ClientService clientService;
	private ManagerService managerService;
	private SpecialistService specialistService;
	private AssistanceService assistanceService;

	@PostMapping("/create")
	public ResponseEntity<Manager> registerClient(@RequestBody @Valid ManagerDTO managerDTO) {
		var manager = ManagerMapper.getInstance().map(managerDTO);
		managerService.create(manager);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/accept-specialist")
	public ResponseEntity<Specialist> acceptSpecialist(@RequestParam(value = "specialist_id") Long specialistId) {
		var specialist = specialistService.findById(specialistId);

		specialistService.changeStatus(specialist, SpecialistStatus.ACCEPTED);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/add-assistance")
	public ResponseEntity<Specialist> addAssistances(@RequestParam(value = "specialist_id") Long specialistId,
													 @RequestParam(value = "assistance_id") Long assistanceId) {
		var specialistById = specialistService.findById(specialistId);
		var assistanceById = assistanceService.findById(assistanceId);

		specialistService.addAssistance(specialistById, assistanceById);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/remove-assistance")
	public ResponseEntity<Specialist> removeAssistances(@RequestParam(value = "specialist_id") Long specialistId,
														@RequestParam(value = "assistance_id") Long assistanceId) {
		var specialistById = specialistService.findById(specialistId);
		var assistanceById = assistanceService.findById(assistanceId);

		specialistService.removeAssistance(specialistById, assistanceById);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping(value = "/search-client")
	public List<Client> searchForClients(@RequestBody SearchRequest request) {
		return clientService.findAll(request);
	}

	@GetMapping(value = "/search-specialist")
	public List<Specialist> searchForSpecialists(@RequestBody SearchRequest request) {
		return specialistService.findAll(request);
	}
}
