package dev.dumble.homeproject.HomeProject_PhaseIV.controller;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.services.Assistance;
import dev.dumble.homeproject.HomeProject_PhaseIV.mapper.AssistanceDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.AssistanceGroupService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.AssistanceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/assistance")
public class AssistanceController {

	private AssistanceService assistanceService;
	private AssistanceGroupService groupService;

	@PostMapping("/create")
	public ResponseEntity<Assistance> createAssistance(@RequestBody AssistanceDTO assistanceDTO) {
		var assistance = assistanceDTO.toAssistance();
		var optionalGroup = Optional.ofNullable(assistanceService.create(assistance));

		return ResponseEntity.of(optionalGroup);
	}

	@PostMapping("/update")
	public ResponseEntity<Assistance> updateAssistance(@RequestParam(value = "assistance_id") Long id,
													   @RequestBody AssistanceDTO assistanceDTO) {
		var assistance = assistanceService.findById(id);
		assistance.setDescription(assistanceDTO.getDescription());
		assistance.setMinimumPrice(assistanceDTO.getMinimumPrice());

		var updatedAssistance = assistanceService.update(assistance);
		var optionalGroup = Optional.ofNullable(updatedAssistance);

		return ResponseEntity.of(optionalGroup);
	}

	@GetMapping("/all-assistances")
	public ResponseEntity<List<Assistance>> updateAssistance(@RequestParam(value = "assistance_group_id") Long id) {
		var group = groupService.findById(id);
		var optionalAssistanceList = Optional.of(groupService.findAllAssistances(group));

		return ResponseEntity.of(optionalAssistanceList);
	}
}
