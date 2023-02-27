package dev.dumble.homeproject.HomeProject_PhaseIV.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfferDTO {

	@NotNull @FutureOrPresent(message = "The date you entered is in the past.")
	private LocalDateTime startTime;

	@NotNull @NotBlank
	@Size(
			min = 10, max = 250,
			message = "The descriptions' length should be between 10 to 250 characters.")
	private String description;

	@NotNull @Min(value = 1) @Max(value = 365)
	private Integer practicalDays;

	@NotNull @Min(value = 1, message = "The price you offered doesn't match the requirements.")
	private Long offeredPrice;

	@NotNull @Min(value = 1)
	private Long specialistId;

	@NotNull @Min(value = 1)
	private Long requestId;
}
