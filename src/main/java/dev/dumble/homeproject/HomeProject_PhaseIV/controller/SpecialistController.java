package dev.dumble.homeproject.HomeProject_PhaseIV.controller;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.ChangePasswordDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.dto.UserDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Specialist;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Request;
import dev.dumble.homeproject.HomeProject_PhaseIV.mappers.SpecialistMapper;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.RequestService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.SpecialistService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.Set;

@Slf4j @RestController
@AllArgsConstructor
@RequestMapping("/api/v1/specialist")
public class SpecialistController {

	private SpecialistService specialistService;
	private RequestService requestService;

	@PostMapping("/register")
	public ResponseEntity<Specialist> registerSpecialist(@RequestPart("profile_picture") MultipartFile profilePicture,
														 @ModelAttribute @Valid UserDTO userDTO) {
		var specialist = SpecialistMapper.getInstance().map(userDTO);
		specialist.setProfilePicture(profilePicture);

		specialistService.create(specialist);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/change-password")
	public ResponseEntity<Specialist> updateSpecialistPassword(@RequestBody @Valid ChangePasswordDTO passwordDTO) {
		var specialist = (Specialist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		specialistService.changePassword(specialist, passwordDTO);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/matching-requests")
	public ResponseEntity<Set<Request>> findMatchingRequests() {
		var specialist = (Specialist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		var matchingRequests = requestService.findMatchingRequests(specialist);
		var optionalRequests = Optional.of(matchingRequests);

		return ResponseEntity.of(optionalRequests);
	}

	@GetMapping("/rating")
	public ResponseEntity<Long> findSpecialistRating() {
		var specialist = (Specialist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		var optionalRating = Optional.of(specialist.getRating());

		return ResponseEntity.of(optionalRating);
	}
}
