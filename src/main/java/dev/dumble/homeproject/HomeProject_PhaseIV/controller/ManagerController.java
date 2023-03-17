package dev.dumble.homeproject.HomeProject_PhaseIV.controller;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.ManagerDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.dto.RequestDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.dto.UserDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.filter.request.SearchRequest;
import dev.dumble.homeproject.HomeProject_PhaseIV.mapper.ManagerMapper;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/manager")
public class ManagerController {

	private ClientService clientService;
	private ManagerService managerService;
	private SpecialistService specialistService;
	private AssistanceService assistanceService;
	private RequestService requestService;

	@PostMapping("/create")
	public void registerClient(@RequestBody @Valid ManagerDTO managerDTO) {
		var manager = ManagerMapper.getInstance().map(managerDTO);

		managerService.create(manager);
	}

	@PostMapping("/accept-specialist")
	public void acceptSpecialist(@RequestParam(value = "specialist_id") Long specialistId) {
		var specialist = specialistService.findById(specialistId);

		specialistService.acceptSpecialist(specialist);
	}

	@PostMapping("/add-assistance")
	public void addAssistances(@RequestParam(value = "specialist_id") Long specialistId,
							   @RequestParam(value = "assistance_id") Long assistanceId) {
		var specialistById = specialistService.findById(specialistId);
		var assistanceById = assistanceService.findById(assistanceId);

		specialistService.addAssistance(specialistById, assistanceById);
	}

	@PostMapping("/remove-assistance")
	public void removeAssistances(@RequestParam(value = "specialist_id") Long specialistId,
								  @RequestParam(value = "assistance_id") Long assistanceId) {
		var specialistById = specialistService.findById(specialistId);
		var assistanceById = assistanceService.findById(assistanceId);

		specialistService.removeAssistance(specialistById, assistanceById);
	}

	@GetMapping(value = "/search-client")
	public Set<UserDTO> searchForClients(@RequestBody SearchRequest request) {
		return clientService.findAll(request);
	}

	@GetMapping(value = "/search-specialist")
	public Set<UserDTO> searchForSpecialists(@RequestBody SearchRequest request) {
		return specialistService.findAll(request);
	}

	@GetMapping(value = "/search-request")
	public Set<RequestDTO> searchForRequestsByClient(@RequestBody SearchRequest request,
													 @RequestParam(value = "client_id", required = false) Long clientId,
													 @RequestParam(value = "specialist_id", required = false) Long specialistId,
													 @RequestParam(value = "assistance_id", required = false) Long assistanceId,
													 @RequestParam(value = "group_id", required = false) Long groupId) {
		return requestService.findAllFilteredRequests(request, clientId, specialistId, assistanceId, groupId);
	}
}
