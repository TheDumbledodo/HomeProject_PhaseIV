package dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl;

public class InvalidDateException extends Error {

	public InvalidDateException(String message) {
		super(message);
	}

	public InvalidDateException() {
		super("The date you entered is before the present time.");
	}
}
