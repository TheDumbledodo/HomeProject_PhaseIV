package dev.dumble.homeproject.HomeProject_PhaseIV.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

	@Size(
			min = 10, max = 250,
			message = "The descriptions' length should be between 10 to 250 characters.")
	private String description;

	@NotNull @Min(value = 1) @Max(value = 5)
	private Integer rating;
}
