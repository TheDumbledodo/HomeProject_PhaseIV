package dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl;

public class InvalidProfessionException extends Error {

	public InvalidProfessionException() {
		super("Your profession doesn't match this request.");
	}
}