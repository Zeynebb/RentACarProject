package com.turkcell.rentacar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentacar.entities.concretes.OrderedAdditionalProduct;

public interface OrderedAdditionalProductDao extends JpaRepository<OrderedAdditionalProduct, Integer>{

}
