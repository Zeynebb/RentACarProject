package com.turkcell.rentacar.halkbankPaymentService;

import java.time.LocalDate;

public interface HalkbankPaymentService {

	public boolean payment(LocalDate expirationDate, String cvv, String creditCardNo, String cardHolder);

}
