package dev.dumble.homeproject.HomeProject_PhaseIV.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {

	@NotNull
	@Min(
			value = 1000_0000_0000_0000L,
			message = "The card number you entered doesn't meet the requirements.")
	@Min(
			value = 9999_9999_9999_9999L,
			message = "The card number you entered exceeded the limit.")
	private Long cardNumber;

	@NotNull
	@Min(
			value = 1300,
			message = "The expiry year you entered doesn't meet the requirements.")
	@Min(
			value = 1499,
			message = "The expiry year you entered exceeded the limit.")
	private Integer expiryYear;

	@NotNull
	@Min(
			value = 1,
			message = "The month you entered should start from 1.")
	@Min(
			value = 12,
			message = "The month you entered should end at 12.")
	private Integer expiryMonth;

	@NotNull
	@Min(
			value = 100,
			message = "The cvc you entered should be at least 3 characters long.")
	@Min(
			value = 9999,
			message = "The cvc you entered should be at maximum 4 characters long.")
	private Integer cvc;

	@NotNull
	private String captchaResponse;
}
