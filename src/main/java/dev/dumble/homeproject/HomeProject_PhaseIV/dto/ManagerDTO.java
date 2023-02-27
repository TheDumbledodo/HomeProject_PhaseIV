package dev.dumble.homeproject.HomeProject_PhaseIV.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDTO extends LoginDTO {

	@NotNull @NotBlank @Email(message = "The email address you provided isn't valid.")
	private String emailAddress;
}
