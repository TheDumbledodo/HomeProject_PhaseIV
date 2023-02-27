package dev.dumble.homeproject.HomeProject_PhaseIV.mapper;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {

	@NonNull @Pattern(regexp = "\\d{16}")
	private Long cardNumber;

	@NonNull @Pattern(regexp = "^(13|14)\\d{2}$")
	private Integer expiryYear;

	@NonNull @Pattern(regexp = "^(0?[1-9]|1[012])$")
	private Integer expiryMonth;

	@NonNull @Pattern(regexp = "^\\d{3,4}$")
	private Integer cvc;

	@NonNull @NotBlank private String captchaResponse;
}
