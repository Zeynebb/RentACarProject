package com.turkcell.rentacar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentacar.entities.concretes.IndividualCustomer;

public interface IndividualCustomerDao extends JpaRepository<IndividualCustomer, Integer>{
	
	boolean existsByUserId(int userId);

}
