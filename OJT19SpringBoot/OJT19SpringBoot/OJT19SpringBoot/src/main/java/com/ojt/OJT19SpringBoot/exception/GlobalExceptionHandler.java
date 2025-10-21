package com.ojt.OJT19SpringBoot.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex){
		Map<String, String> response = new HashMap<>();
		List<FieldError> errors = ex.getBindingResult().getFieldErrors();
		errors.forEach(
				error -> response.put(error.getField(), error.getDefaultMessage())
				);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

}
