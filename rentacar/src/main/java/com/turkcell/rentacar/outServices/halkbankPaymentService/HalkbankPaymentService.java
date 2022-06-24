package com.turkcell.rentacar.outServices.halkbankPaymentService;

import java.time.LocalDate;

public interface HalkbankPaymentService {

	public boolean payment(LocalDate expirationDate, String cvv, String creditCardNo, String cardHolder);

}
