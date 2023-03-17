package dev.dumble.homeproject.HomeProject_PhaseIV.controller;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.AssistanceGroupDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.mapper.AssistanceGroupMapper;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.AssistanceGroupService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/assistance-group")
public class AssistanceGroupController {

	private AssistanceGroupService groupService;

	@PostMapping("/create")
	@PreAuthorize("hasRole('MANAGER')")
	public void createAssistanceGroup(@RequestBody @Valid AssistanceGroupDTO groupRequest) {
		var assistanceGroup = AssistanceGroupMapper.getInstance().map(groupRequest);
		groupService.create(assistanceGroup);
	}

	@GetMapping("/all-groups")
	@PreAuthorize("permitAll()")
	public ResponseEntity<Set<AssistanceGroupDTO>> findAllAssistanceGroups() {
		var groupList = groupService.findAllSerialized();
		var optionalGroupList = Optional.ofNullable(groupList);

		return ResponseEntity.of(optionalGroupList);
	}
}
