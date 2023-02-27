package dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl;

public class InvalidEntityException extends Error {

	public InvalidEntityException(String message) {
		super(message);
	}

	public InvalidEntityException() {
		this("This entity doesn't exist in the database!");
	}
}
