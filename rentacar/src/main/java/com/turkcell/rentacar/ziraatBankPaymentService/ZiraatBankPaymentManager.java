package com.turkcell.rentacar.ziraatBankPaymentService;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

@Service
public class ZiraatBankPaymentManager implements ZiraatBankPaymentService{

	@Override
	public boolean payment(String creditCardNo, String cardHolder, LocalDate expirationDate, String cvv) {
		return true;
	}
}
