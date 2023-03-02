package dev.dumble.homeproject.HomeProject_PhaseIV.controller;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.ManagerDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Client;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Manager;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Specialist;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.SpecialistStatus;
import dev.dumble.homeproject.HomeProject_PhaseIV.mappers.ManagerMapper;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.AssistanceService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.ClientService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.ManagerService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.SpecialistService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kaczmarzyk.spring.data.jpa.domain.*;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
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
	public List<Client> searchForClients(
			@And(value = {
					@Spec(path = "id", params = "id", spec = Equal.class),
					@Spec(path = "userRole", params = "userRole", spec = EqualIgnoreCase.class),
					@Spec(path = "emailAddress", params = "emailAddress", spec = Like.class),
					@Spec(path = "password", params = "password", spec = Like.class),
					@Spec(path = "username", params = "username", spec = Like.class),
					@Spec(path = "firstName", params = "firstName", spec = Like.class),
					@Spec(path = "lastName", params = "lastName", spec = Like.class),
					@Spec(path = "credit", params = "credit", spec = Equal.class)
			}) Specification<Client> specification) {

		return clientService.findAll(specification);
	}

	@GetMapping(value = "/search-specialist")
	public List<Specialist> searchForSpecialists(
			@And(value = {
					@Spec(path = "id", params = "id", spec = Equal.class),
					@Spec(path = "userRole", params = "userRole", spec = EqualIgnoreCase.class),
					@Spec(path = "emailAddress", params = "emailAddress", spec = Like.class),
					@Spec(path = "password", params = "password", spec = Like.class),
					@Spec(path = "username", params = "username", spec = Like.class),
					@Spec(path = "firstName", params = "firstName", spec = Like.class),
					@Spec(path = "lastName", params = "lastName", spec = Like.class),
					@Spec(path = "rating", params = "rating", spec = Equal.class),
					@Spec(path = "status", params = "status", spec = Like.class),
					@Spec(path = "credit", params = "credit", spec = GreaterThanOrEqual.class),
					@Spec(path = "credit", params = "credit", spec = LessThanOrEqual.class)
			}) Specification<Specialist> specification) {

		return specialistService.findAll(specification);
	}
}
