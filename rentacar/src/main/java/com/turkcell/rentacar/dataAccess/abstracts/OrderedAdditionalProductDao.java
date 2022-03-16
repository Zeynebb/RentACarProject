package com.turkcell.rentacar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentacar.entities.concretes.OrderedAdditionalProduct;

public interface OrderedAdditionalProductDao extends JpaRepository<OrderedAdditionalProduct, Integer> {

	List<OrderedAdditionalProduct> getByRent_RentId(int rentId);
	
	boolean existsByRent_RentId(int rentId);

}
