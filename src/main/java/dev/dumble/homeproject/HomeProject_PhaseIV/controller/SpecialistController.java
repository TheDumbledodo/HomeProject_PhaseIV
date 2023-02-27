package dev.dumble.homeproject.HomeProject_PhaseIV.controller;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Specialist;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Request;
import dev.dumble.homeproject.HomeProject_PhaseIV.mapper.LoginDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.mapper.SpecialistDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.RequestService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.SpecialistService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/specialist")
public class SpecialistController {

	private SpecialistService specialistService;
	private RequestService requestService;

	@PostMapping("/register")
	public ResponseEntity<Specialist> registerSpecialist(@Valid @ModelAttribute SpecialistDTO specialistDTO,
														 @RequestPart("profile_picture") MultipartFile profilePicture) {
		var specialist = specialistDTO.toSpecialist();
		specialist.setProfilePicture(profilePicture);

		var optionalSpecialist = Optional.ofNullable(specialistService.register(specialist));

		return ResponseEntity.of(optionalSpecialist);
	}

	@PostMapping("/login")
	public ResponseEntity<Specialist> loginSpecialist(@RequestBody LoginDTO loginDTO) {
		var specialist = specialistService.login(loginDTO.getUsername(), loginDTO.getPassword());
		var optionalSpecialist = Optional.of(specialist);

		return ResponseEntity.of(optionalSpecialist);
	}

	@PostMapping("/change-password")
	public ResponseEntity<Specialist> updateSpecialistPassword(@RequestParam(value = "new_password") String newPassword,
															   @RequestBody LoginDTO loginDTO) {
		var specialist = specialistService.login(loginDTO.getUsername(), loginDTO.getPassword());
		var updatedSpecialist = specialistService.changePassword(specialist, newPassword);
		var optionalSpecialist = Optional.ofNullable(updatedSpecialist);

		return ResponseEntity.of(optionalSpecialist);
	}

	@GetMapping("/matching-requests")
	public ResponseEntity<List<Request>> findMatchingRequests(@RequestParam(value = "specialist_id") Long id) {
		var specialist = specialistService.findById(id);

		var matchingRequests = requestService.findMatchingRequests(specialist);
		var optionalRequests = Optional.of(matchingRequests);

		return ResponseEntity.of(optionalRequests);
	}

	@GetMapping("/rating")
	public ResponseEntity<Long> findSpecialistRating(@RequestParam(value = "specialist_id") Long id) {
		var specialist = specialistService.findById(id);
		var optionalRating = Optional.of(specialist.getRating());

		return ResponseEntity.of(optionalRating);
	}
}
