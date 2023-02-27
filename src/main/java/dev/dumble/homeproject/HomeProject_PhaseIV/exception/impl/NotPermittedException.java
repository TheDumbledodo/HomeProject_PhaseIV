package dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl;

public class NotPermittedException extends Error {

	public NotPermittedException() {
		super("This user doesn't have the required permissions.");
	}
}
