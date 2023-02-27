package dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl;

public class NotPermittedException extends Error {

	public NotPermittedException(String message) {
		super(message);
	}

	public NotPermittedException() {
		this("This user doesn't have the required permissions.");
	}
}
