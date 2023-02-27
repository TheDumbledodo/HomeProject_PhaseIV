package dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl;

public class InvalidRequestStatusException extends Error {

	public InvalidRequestStatusException() {
		super("The requests status should be awaiting \nselection/suggestion for you to offer a deal.");
	}
}
