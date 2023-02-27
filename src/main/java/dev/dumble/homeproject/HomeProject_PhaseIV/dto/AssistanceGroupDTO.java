package dev.dumble.homeproject.HomeProject_PhaseIV.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssistanceGroupDTO {

	@NotNull @NotBlank
	private String name;
}
