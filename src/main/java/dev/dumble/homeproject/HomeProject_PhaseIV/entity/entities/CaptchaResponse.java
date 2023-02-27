package dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaResponse {

	boolean success;

	LocalDateTime challenge_ts;
	String hostname;

	@JsonProperty("error-codes")
	List<String> errorCodes;
}