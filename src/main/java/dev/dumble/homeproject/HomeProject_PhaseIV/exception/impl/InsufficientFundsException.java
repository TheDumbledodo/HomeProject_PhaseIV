package dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl;

public class InsufficientFundsException extends Error {

	public InsufficientFundsException(String message) {
		super(message);
	}

	public InsufficientFundsException() {
		super("You didn't enter enough money for this request.");
	}
}
