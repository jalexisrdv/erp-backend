package com.erp.configuration;

import com.erp.shared.domain.DomainError;
import com.erp.shared.domain.ResponseWrapper;
import com.erp.shared.exeption.UnexpectedException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DomainError.class)
	public ResponseEntity<?> handleException(DomainError e) {
		return ResponseWrapper.create(e.getHttpCode(), e.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception e) {
		e.printStackTrace();
		return ResponseWrapper.internalServerError(UnexpectedException.MESSAGE);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleJsonParseError(HttpMessageNotReadableException e) {
		Throwable cause = e.getCause();

		if (cause instanceof UnrecognizedPropertyException ex) {
			String message = String.format(
					"Campo '%s' desconocido, envía únicamente los campos definidos en la API.",
					ex.getPropertyName()
			);
			return ResponseWrapper.badRequest(message);
		}

		return ResponseWrapper.badRequest("JSON inválido");
	}

}
