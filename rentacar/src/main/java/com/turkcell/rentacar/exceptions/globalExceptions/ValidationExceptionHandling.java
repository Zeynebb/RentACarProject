package com.turkcell.rentacar.exceptions.globalExceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.turkcell.rentacar.core.utilities.results.ErrorDataResult;

public class ValidationExceptionHandling {

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ErrorDataResult<Object> handleValidationException(
			MethodArgumentNotValidException argumentNotValidException) {
		Map<String, String> validationErrors = new HashMap<String, String>();
		for (FieldError fieldError : argumentNotValidException.getBindingResult().getFieldErrors()) {
			validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		ErrorDataResult<Object> errorResults = new ErrorDataResult<>(validationErrors, "Validate.Error");
		return errorResults;
	}

}
