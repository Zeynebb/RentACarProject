package com.turkcell.rentacar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.turkcell.rentacar.entities.concretes.OrderedAdditionalProduct;
import com.turkcell.rentacar.entities.concretes.Rent;

public interface RentDao extends JpaRepository<Rent, Integer> {

	List<OrderedAdditionalProduct> getOrderedAdditionalProductsByRentId(int rentId);

	boolean existsByCar_CarId(int carId);

	List<Rent> getByCar_CarId(int carId);

	@Modifying
	@Query("update Rent r set r.endedKilometerInfo = ?2 where r.rentId = ?1")
	int updateEndedKilometerInfoToRentByRentId(int rentId, String endedKilometerInfo);

}
