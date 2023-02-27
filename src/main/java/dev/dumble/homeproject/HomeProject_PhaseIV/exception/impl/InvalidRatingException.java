package dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl;

public class InvalidRatingException extends Error {

	public InvalidRatingException(String message) {
		super(message);
	}

	public InvalidRatingException() {
		super("The rating you provided should be between 0-5.");
	}
}
