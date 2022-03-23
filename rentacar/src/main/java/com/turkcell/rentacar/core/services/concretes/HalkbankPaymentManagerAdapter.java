package com.turkcell.rentacar.core.services.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.turkcell.rentacar.core.services.abstracts.BaseBankPaymentServiceAdapter;
import com.turkcell.rentacar.entities.concretes.Payment;
import com.turkcell.rentacar.halkbankPaymentService.HalkbankPaymentService;

@Component
@Qualifier("halkbank")
public class HalkbankPaymentManagerAdapter implements BaseBankPaymentServiceAdapter {

	private HalkbankPaymentService halkbankPaymentService;

	@Autowired
	public HalkbankPaymentManagerAdapter(HalkbankPaymentService halkbankPaymentService) {
		this.halkbankPaymentService = halkbankPaymentService;
	}

	@Override
	public boolean payment(Payment payment) {
		System.out.println("halkbank");
		return this.halkbankPaymentService.payment(payment.getExpirationDate(), payment.getCvv(),
				payment.getCreditCardNo(), payment.getCardHolder());
	}

}
