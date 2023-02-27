package dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl;

public class InvalidProfessionException extends Error {

	public InvalidProfessionException(String message) {
		super(message);
	}

	public InvalidProfessionException() {
		this("Your professions list doesn't match this request.");
	}
}