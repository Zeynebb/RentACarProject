package com.turkcell.rentacar.core.services.concretes;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.turkcell.rentacar.core.services.abstracts.BaseBankPaymentServiceAdapter;
import com.turkcell.rentacar.entities.concretes.Payment;

@Component
@Qualifier("fakeBankPayment")
public class FakeBankPaymentManager implements BaseBankPaymentServiceAdapter {

	@Override
	public boolean payment(Payment payment) {
		return true;
	}

}
