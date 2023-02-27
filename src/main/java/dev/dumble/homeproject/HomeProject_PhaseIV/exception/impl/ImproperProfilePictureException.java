package dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl;

public class ImproperProfilePictureException extends Error {

	public ImproperProfilePictureException(String message) {
		super(message);
	}

	public ImproperProfilePictureException() {
		this("This specialists profile pictures format isn't jpg.");
	}
}
