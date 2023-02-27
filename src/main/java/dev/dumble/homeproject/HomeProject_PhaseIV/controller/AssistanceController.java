package dev.dumble.homeproject.HomeProject_PhaseIV.controller;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.AssistanceDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.services.Assistance;
import dev.dumble.homeproject.HomeProject_PhaseIV.mappers.AssistanceMapper;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.AssistanceGroupService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.AssistanceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j @RestController
@AllArgsConstructor
@RequestMapping("/api/v1/assistance")
public class AssistanceController {

	private AssistanceService assistanceService;
	private AssistanceGroupService groupService;

	@PostMapping("/create")
	public ResponseEntity<Assistance> createAssistance(@RequestBody @Valid AssistanceDTO assistanceDTO,
													   @RequestParam(value = "assistance_group_id") Long groupId) {
		var assistance = AssistanceMapper.getInstance().map(assistanceDTO);
		var group = groupService.findById(groupId);

		assistance.setGroup(group);
		assistanceService.create(assistance);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/{id}/update")
	public ResponseEntity<Assistance> updateAssistance(@PathVariable(name = "id") Long assistanceId,
													   @RequestBody @Valid AssistanceDTO assistanceDTO) {
		var assistance = assistanceService.findById(assistanceId);
		assistance.setDescription(assistanceDTO.getDescription());
		assistance.setMinimumPrice(assistanceDTO.getMinimumPrice());

		assistanceService.update(assistance);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/all-assistances")
	public ResponseEntity<List<Assistance>> findAllAssistancesFromGroup(@RequestParam(value = "assistance_group_id") Long groupId) {
		var group = groupService.findById(groupId);
		var optionalAssistanceList = Optional.of(group.getAssistanceList());

		return ResponseEntity.of(optionalAssistanceList);
	}
}
