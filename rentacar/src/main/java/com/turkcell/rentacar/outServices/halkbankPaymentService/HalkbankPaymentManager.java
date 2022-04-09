package com.turkcell.rentacar.outServices.halkbankPaymentService;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

@Service
public class HalkbankPaymentManager implements HalkbankPaymentService {

	@Override
	public boolean payment(LocalDate expirationDate, String cvv, String creditCardNo, String cardHolder) {
		return true;
	}

}
