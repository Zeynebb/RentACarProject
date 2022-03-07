package com.turkcell.rentacar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentacar.entities.concretes.Rent;

public interface RentDao extends JpaRepository<Rent, Integer> {

	boolean existsByCar_CarId(int carId);
	Rent getByCar_CarId(int carId);

}
