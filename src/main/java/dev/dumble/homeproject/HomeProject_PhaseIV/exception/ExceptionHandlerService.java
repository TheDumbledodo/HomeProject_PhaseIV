package dev.dumble.homeproject.HomeProject_PhaseIV.exception;

import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.*;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerService {

	@ExceptionHandler(DuplicateEntityException.class)
	public ResponseEntity<?> handleDuplicateException(DuplicateEntityException exception) {
		return this.handleError(exception, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ImproperProfilePictureException.class)
	public ResponseEntity<?> handleImproperProfilePictureException(ImproperProfilePictureException exception) {
		return this.handleError(exception, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(InsufficientFundsException.class)
	public ResponseEntity<?> handleInsufficientFundsException(InsufficientFundsException exception) {
		return this.handleError(exception, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(InvalidCaptchaException.class)
	public ResponseEntity<?> handleInvalidCaptchaException(InvalidCaptchaException exception) {
		return this.handleError(exception, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(InvalidEntityException.class)
	public ResponseEntity<?> handleInvalidEntityException(InvalidEntityException exception) {
		return this.handleError(exception, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidProfessionException.class)
	public ResponseEntity<?> handleInvalidProfessionException(InvalidProfessionException exception) {
		return this.handleError(exception, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidRequestStatusException.class)
	public ResponseEntity<?> handleInvalidRequestStatusException(InvalidRequestStatusException exception) {
		return this.handleError(exception, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NotPermittedException.class)
	public ResponseEntity<?> handleNotPermittedException(NotPermittedException exception) {
		return this.handleError(exception, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, List<String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		return this.handleBindException(exception);
	}

	@ExceptionHandler(BindException.class)
	public ResponseEntity<Map<String, List<String>>> handleBindException(BindException exception) {
		var errors = exception.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.toList();

		var errorResponse = new HashMap<String, List<String>>();
		errorResponse.put("errors", errors);

		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(SizeLimitExceededException.class)
	public ResponseEntity<?> handleSizeLimitExceededException(SizeLimitExceededException exception) {
		return this.handleError(exception, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException exception) {
		return this.handleError(exception, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<?> handleError(Throwable throwable, HttpStatus status) {
		return ResponseEntity.status(status).body(throwable.getMessage());
	}
}
