package com.turkcell.rentacar.exceptions;

import org.springframework.stereotype.Service;

@Service
public class BusinessException extends Exception{
	public BusinessException() {
		
	}	
	public BusinessException(String message) {
		super(message);
	}
}
