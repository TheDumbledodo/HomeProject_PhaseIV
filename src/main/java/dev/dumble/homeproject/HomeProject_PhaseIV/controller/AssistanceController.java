package dev.dumble.homeproject.HomeProject_PhaseIV.controller;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.AssistanceDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.mapper.AssistanceMapper;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.AssistanceGroupService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.AssistanceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/assistance")
public class AssistanceController {

	private AssistanceService assistanceService;
	private AssistanceGroupService groupService;

	@PostMapping("/create")
	@PreAuthorize("hasRole('MANAGER')")
	public void createAssistance(@RequestBody @Valid AssistanceDTO assistanceDTO,
								 @RequestParam(value = "assistance_group_id") Long groupId) {
		var assistance = AssistanceMapper.getInstance().map(assistanceDTO);
		var group = groupService.findById(groupId);

		assistance.setGroup(group);
		assistanceService.create(assistance);
	}

	@PostMapping("/{id}/update")
	@PreAuthorize("hasRole('MANAGER')")
	public void updateAssistance(@PathVariable(name = "id") Long assistanceId,
								 @RequestBody @Valid AssistanceDTO assistanceDTO) {
		var assistance = assistanceService.findById(assistanceId);
		assistance.setDescription(assistanceDTO.getDescription());
		assistance.setMinimumPrice(assistanceDTO.getMinimumPrice());

		assistanceService.update(assistance);
	}

	@GetMapping("/all-assistances")
	@PreAuthorize("permitAll()")
	public ResponseEntity<Set<AssistanceDTO>> findAllAssistancesFromGroup(@RequestParam(value = "assistance_group_id") Long groupId) {
		var group = groupService.findById(groupId);
		var optionalAssistanceList = Optional.of(assistanceService.findAllSerialized(group));

		return ResponseEntity.of(optionalAssistanceList);
	}
}
