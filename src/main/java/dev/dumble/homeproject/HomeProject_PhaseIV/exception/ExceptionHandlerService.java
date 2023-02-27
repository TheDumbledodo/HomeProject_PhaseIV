package dev.dumble.homeproject.HomeProject_PhaseIV.exception;

import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerService {

	@ExceptionHandler(DuplicateEntityException.class)
	public ResponseEntity<?> handleDuplicateException(DuplicateEntityException exception) {
		return this.handleError(exception, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(ImproperProfilePictureException.class)
	public ResponseEntity<?> handleImproperProfilePictureException(ImproperProfilePictureException exception) {
		return this.handleError(exception, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(InsufficientFundsException.class)
	public ResponseEntity<?> handleInsufficientFundsException(InsufficientFundsException exception) {
		return this.handleError(exception, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(InvalidCaptchaException.class)
	public ResponseEntity<?> handleInvalidCaptchaException(InvalidCaptchaException exception) {
		return this.handleError(exception, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(InvalidDateException.class)
	public ResponseEntity<?> handleInvalidDateException(InvalidDateException exception) {
		return this.handleError(exception, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(InvalidEntityException.class)
	public ResponseEntity<?> handleInvalidEntityException(InvalidEntityException exception) {
		return this.handleError(exception, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidProfessionException.class)
	public ResponseEntity<?> handleInvalidProfessionException(InvalidProfessionException exception) {
		return this.handleError(exception, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(InvalidRatingException.class)
	public ResponseEntity<?> handleInvalidRatingException(InvalidRatingException exception) {
		return this.handleError(exception, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(InvalidRequestStatusException.class)
	public ResponseEntity<?> handleInvalidRequestStatusException(InvalidRequestStatusException exception) {
		return this.handleError(exception, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MissingInformationException.class)
	public ResponseEntity<?> handleMissingInformationException(MissingInformationException exception) {
		return this.handleError(exception, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NotPermittedException.class)
	public ResponseEntity<?> handleNotPermittedException(NotPermittedException exception) {
		return this.handleError(exception, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(WeekPasswordException.class)
	public ResponseEntity<?> handleWeekPasswordException(WeekPasswordException exception) {
		return this.handleError(exception, HttpStatus.NOT_ACCEPTABLE);
	}

	public ResponseEntity<?> handleError(Error error, HttpStatus status) {
		return ResponseEntity.status(status).body(error.getMessage());
	}
}
