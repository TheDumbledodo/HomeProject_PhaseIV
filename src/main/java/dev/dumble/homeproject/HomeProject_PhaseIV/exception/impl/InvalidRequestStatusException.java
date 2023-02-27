package dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl;

public class InvalidRequestStatusException extends Error {

	public InvalidRequestStatusException(String message) {
		super(message);
	}

	public InvalidRequestStatusException() {
		this("The requests status must be awaiting selection/suggestion to offer a deal.");
	}
}
