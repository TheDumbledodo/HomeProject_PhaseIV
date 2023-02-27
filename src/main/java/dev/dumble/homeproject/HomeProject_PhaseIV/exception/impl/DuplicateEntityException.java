package dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl;

public class DuplicateEntityException extends Error {

	public DuplicateEntityException(String message) {
		super(message);
	}

	public DuplicateEntityException() {
		this("This entity is already in the database.");
	}
}
