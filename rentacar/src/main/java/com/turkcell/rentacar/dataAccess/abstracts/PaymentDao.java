package com.turkcell.rentacar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentacar.entities.concretes.Payment;

public interface PaymentDao extends JpaRepository<Payment, Integer> {
	
	
	


}
