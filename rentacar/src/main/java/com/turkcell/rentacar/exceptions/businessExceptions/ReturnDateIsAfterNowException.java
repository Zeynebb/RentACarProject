package com.turkcell.rentacar.exceptions.businessExceptions;

public class ReturnDateIsAfterNowException extends BusinessException {

	public ReturnDateIsAfterNowException(String message) {
		super(message);
	}

}
