package com.turkcell.rentacar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentacar.entities.concretes.CarMaintenance;

public interface CarMaintenanceDao extends JpaRepository<CarMaintenance, Integer> {
	
	boolean existsByCar_CarId(int carId);

	List<CarMaintenance> getByCar_CarId(int carId);
	
	
	
	

}
