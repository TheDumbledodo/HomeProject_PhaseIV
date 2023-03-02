package dev.dumble.homeproject.HomeProject_PhaseIV.controller;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.AssistanceGroupDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.AssistanceGroup;
import dev.dumble.homeproject.HomeProject_PhaseIV.mapper.AssistanceGroupMapper;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.AssistanceGroupService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j @RestController
@AllArgsConstructor
@RequestMapping("/api/v1/assistance-group")
public class AssistanceGroupController {

	private AssistanceGroupService groupService;

	@PreAuthorize("hasRole('MANAGER')")
	@PostMapping("/create")
	public ResponseEntity<AssistanceGroup> createAssistanceGroup(@RequestBody @Valid AssistanceGroupDTO groupRequest) {
		var assistanceGroup = AssistanceGroupMapper.getInstance().map(groupRequest);
		groupService.create(assistanceGroup);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PreAuthorize("hasAnyRole('MANAGER', 'CLIENT')")
	@GetMapping("/all-groups")
	public ResponseEntity<List<AssistanceGroup>> findAllAssistanceGroups() {
		var groupList = groupService.findAll();
		var optionalGroupList = Optional.ofNullable(groupList);

		return ResponseEntity.of(optionalGroupList);
	}
}
