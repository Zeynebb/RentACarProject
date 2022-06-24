package com.turkcell.rentacar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentacar.entities.concretes.CorporateCustomer;

public interface CorporateCustomerDao extends JpaRepository<CorporateCustomer, Integer> {

	boolean existsByUserId(int userId);

	boolean existsByEmail(String email);

}
