package dev.dumble.homeproject.HomeProject_PhaseIV.service.impl;

import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.CaptchaResponse;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.InvalidCaptchaException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class CaptchaService {

	private final RestTemplate template;

	@Value("${google.recaptcha.verification.endpoint}") String recaptchaEndpoint;
	@Value("${google.recaptcha.secret}") String recaptchaSecret;

	public CaptchaService(RestTemplateBuilder templateBuilder) {
		this.template = templateBuilder.build();
	}

	@SneakyThrows
	public void validateCaptcha(String captchaResponse) {
		var parameters = new LinkedMultiValueMap<>();
		parameters.add("secret", recaptchaSecret);
		parameters.add("response", captchaResponse);

		var apiResponse = template.postForObject(recaptchaEndpoint, parameters, CaptchaResponse.class);
		if (apiResponse != null && apiResponse.isSuccess()) return;

		throw new InvalidCaptchaException();
	}
}