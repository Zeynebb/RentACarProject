package com.turkcell.rentacar.exceptions.businessExceptions;

import org.springframework.stereotype.Service;

@Service
public class BusinessException extends RuntimeException{
	public BusinessException() {
		
	}	
	public BusinessException(String message) {
		super(message);
	}
}
