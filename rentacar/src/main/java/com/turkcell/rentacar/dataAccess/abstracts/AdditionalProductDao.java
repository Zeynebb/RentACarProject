package com.turkcell.rentacar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentacar.entities.concretes.AdditionalProduct;

public interface AdditionalProductDao extends JpaRepository<AdditionalProduct, Integer>{

	boolean existsByAdditionalProductId(int additionalProductId);
	
	boolean existsByAdditionalProductName(String additionalProductName);
}
