package com.turkcell.rentacar.core.services.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.turkcell.rentacar.core.services.abstracts.BaseBankPaymentServiceAdapter;
import com.turkcell.rentacar.entities.concretes.Payment;
import com.turkcell.rentacar.outServices.ziraatBankPaymentService.ZiraatBankPaymentService;


@Component
@Qualifier("ziraatBank")
public class ZiraatBankPaymentManagerAdapter implements BaseBankPaymentServiceAdapter {

	private ZiraatBankPaymentService ziraatBankPaymentService;

	@Autowired
	public ZiraatBankPaymentManagerAdapter(ZiraatBankPaymentService ziraatBankPaymentService) {
		this.ziraatBankPaymentService = ziraatBankPaymentService;
	}

	@Override
	public boolean payment(Payment payment) {
		return this.ziraatBankPaymentService.payment(payment.getCreditCardNo(), payment.getCardHolder(),
				payment.getExpirationDate(), payment.getCvv());
	}

}
