package dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl;

public class InvalidCaptchaException extends Error {

	public InvalidCaptchaException(String message) {
		super(message);
	}

	public InvalidCaptchaException() {
		this("The captcha hasn't been solved yet.");
	}
}
