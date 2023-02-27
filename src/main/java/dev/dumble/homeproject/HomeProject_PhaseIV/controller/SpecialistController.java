package dev.dumble.homeproject.HomeProject_PhaseIV.controller;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.LoginDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.dto.UserDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members.Specialist;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Request;
import dev.dumble.homeproject.HomeProject_PhaseIV.mappers.SpecialistMapper;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.RequestService;
import dev.dumble.homeproject.HomeProject_PhaseIV.service.impl.SpecialistService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
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

	@PostMapping("/login")
	public ResponseEntity<Specialist> loginSpecialist(@RequestBody @Valid LoginDTO loginDTO) {
		specialistService.login(loginDTO.getUsername(), loginDTO.getPassword());

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/change-password")
	public ResponseEntity<Specialist> updateSpecialistPassword(
			@RequestParam(value = "new_password") @NonNull @NotBlank
			@Pattern(
					regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&#^])[A-Za-z0-9@$!%*?&]{8,10}$",
					message = """
							The password must contain at least 8 to 10 characters containing an uppercase,
							a lowercase, one number and one of these @$!%*?&] characters.
							""")
			String recentPassword,
			@RequestBody @Valid LoginDTO loginDTO) {

		var specialist = specialistService.login(loginDTO.getUsername(), loginDTO.getPassword());
		specialistService.changePassword(specialist, recentPassword);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/{id}/matching-requests")
	public ResponseEntity<Set<Request>> findMatchingRequests(@PathVariable(name = "id") Long specialistId) {
		var specialist = specialistService.findById(specialistId);

		var matchingRequests = requestService.findMatchingRequests(specialist);
		var optionalRequests = Optional.of(matchingRequests);

		return ResponseEntity.of(optionalRequests);
	}

	@GetMapping("/{id}/rating")
	public ResponseEntity<Long> findSpecialistRating(@PathVariable(name = "id") Long specialistId) {
		var specialist = specialistService.findById(specialistId);
		var optionalRating = Optional.of(specialist.getRating());

		return ResponseEntity.of(optionalRating);
	}
}
