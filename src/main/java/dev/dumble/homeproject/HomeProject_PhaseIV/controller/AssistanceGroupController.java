package dev.dumble.homeproject.HomeProject_PhaseIV.controller;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.services.AssistanceGroup;
import dev.dumble.homeproject.HomeProject_PhaseIV.mapper.AssistanceGroupDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.AssistanceGroupService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/assistance-group")
public class AssistanceGroupController {

	private AssistanceGroupService groupService;

	@PostMapping("/create")
	public ResponseEntity<AssistanceGroup> createAssistanceGroup(@RequestBody AssistanceGroupDTO groupDTO) {
		var assistanceGroup = groupDTO.toAssistanceGroup();
		var optionalGroup = Optional.ofNullable(groupService.create(assistanceGroup));

		return ResponseEntity.of(optionalGroup);
	}

	@GetMapping("/all-groups")
	public ResponseEntity<List<AssistanceGroup>> createAssistanceGroup() {
		var groupList = groupService.findAllGroups();
		var optionalGroupList = Optional.ofNullable(groupList);

		return ResponseEntity.of(optionalGroupList);
	}
}
