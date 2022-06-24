package com.turkcell.rentacar;

import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.turkcell.rentacar.core.utilities.results.ErrorDataResult;
import com.turkcell.rentacar.exceptions.businessExceptions.BusinessException;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@RestControllerAdvice
public class RentacarApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentacarApplication.class, args);
	}

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

	@Bean
	public BusinessException getBusinessException() {
		return new BusinessException();
	}

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

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ErrorDataResult<Object> handleBusinessException(BusinessException businessException) {
		ErrorDataResult<Object> errorResults = new ErrorDataResult<>(businessException.getMessage(), "Validate.Error");
		return errorResults;
	}

}
