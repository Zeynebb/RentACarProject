package com.turkcell.rentacar.ziraatBankPaymentService;

import java.time.LocalDate;

public interface ZiraatBankPaymentService {

	public boolean payment(String creditCardNo, String cardHolder, LocalDate expirationDate, String cvv);

}
