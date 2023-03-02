package dev.dumble.homeproject.HomeProject_PhaseIV.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDTO {

	@NotNull @NotBlank @Email(message = "The email address you provided isn't valid.")
	private String emailAddress;

	@NotNull @NotBlank
	@Pattern(
			regexp = "^\\S{4,20}$",
			message = "The username you entered isn't valid.")
	private String username;

	@NotNull @NotBlank @Pattern(
			regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}",
			message = """
					The password must contain at least 8
					characters containing an uppercase, a lowercase.
					""")
	private String password;
}
