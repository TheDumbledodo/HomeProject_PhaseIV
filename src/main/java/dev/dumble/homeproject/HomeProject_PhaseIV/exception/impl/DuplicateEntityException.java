package dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl;

public class DuplicateEntityException extends Error {

	public DuplicateEntityException() {
		super("This entity is already in the database.");
	}
}
