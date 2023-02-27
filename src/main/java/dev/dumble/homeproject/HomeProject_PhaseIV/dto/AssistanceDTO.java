package dev.dumble.homeproject.HomeProject_PhaseIV.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssistanceDTO {

	@NotNull(message = "The assistance name shouldn't be null.")
	@NotBlank(message = "The assistance name shouldn't be blank.")
	private String name;

	@NotNull(message = "The assistance description shouldn't be null.")
	@NotBlank(message = "The assistance description shouldn't be null.")
	private String description;

	@NotNull(message = "The assistance minimum price shouldn't be null.")
	@Min(value = 1, message = "The minimum price you entered doesn't match the requirements.")
	private Long minimumPrice;
}
