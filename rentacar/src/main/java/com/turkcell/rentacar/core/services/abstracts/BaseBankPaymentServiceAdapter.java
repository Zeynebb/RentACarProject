package com.turkcell.rentacar.core.services.abstracts;

import org.springframework.stereotype.Service;

import com.turkcell.rentacar.entities.concretes.Payment;

@Service
public interface BaseBankPaymentServiceAdapter {
	
	public boolean payment(Payment payment);

}
