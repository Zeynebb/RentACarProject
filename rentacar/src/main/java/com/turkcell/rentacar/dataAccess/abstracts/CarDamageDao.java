package com.turkcell.rentacar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentacar.entities.concretes.CarDamage;

public interface CarDamageDao extends JpaRepository<CarDamage, Integer> {

}
