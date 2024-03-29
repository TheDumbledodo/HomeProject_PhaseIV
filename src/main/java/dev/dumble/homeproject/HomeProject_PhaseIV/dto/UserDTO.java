package dev.dumble.homeproject.HomeProject_PhaseIV.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

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

	@NotNull @NotBlank
	@Size(
			min = 2, max = 24,
			message = "Your first names' length should be between 2 to 24 characters.")
	private String firstName;

	@NotNull @NotBlank
	@Size(
			min = 3, max = 36,
			message = "Your last names' length should be between 2 to 24 characters.")
	private String lastName;

	@NotNull @Min(value = 1, message = "The credit you provided doesn't match the requirements.")
	private Long credit;
}
