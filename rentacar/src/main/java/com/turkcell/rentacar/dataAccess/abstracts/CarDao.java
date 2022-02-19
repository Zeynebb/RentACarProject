package com.turkcell.rentacar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.turkcell.rentacar.entities.concretes.Car;

@Repository
public interface CarDao extends JpaRepository<Car, Integer> {
	
	boolean existsByCarId(int carId);
	
	List<Car> findByDailyPriceLessThanEqual(double dailyPrice);
	
	@Modifying
	@Query("update Car set color.colorId=?2 where carId=?1")
	int updateColorToCarByCarId(int carId, int colorId);
	
	@Modifying
	@Query("update Car set brand.brandId=?2 where carId=?1")
	int updateBrandToCarByCarId(int carId, int brandId);

}
