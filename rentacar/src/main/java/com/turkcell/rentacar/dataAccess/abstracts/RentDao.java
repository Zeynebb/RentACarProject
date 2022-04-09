package com.turkcell.rentacar.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.turkcell.rentacar.entities.concretes.OrderedAdditionalProduct;
import com.turkcell.rentacar.entities.concretes.Rent;

public interface RentDao extends JpaRepository<Rent, String> {

	List<OrderedAdditionalProduct> getOrderedAdditionalProductsByRentId(String rentId);

	boolean existsByCar_CarId(int carId);

	List<Rent> getByCar_CarId(int carId);
	
	//boolean existsByRentNo(String rentNo);

	@Modifying
	@Query("update Rent r set r.endedKilometerInfo = ?2 where r.rentId = ?1")
	int updateEndedKilometerInfoToRentByRentId(String rentId, String endedKilometerInfo);

	@Modifying
	@Query("update Rent r set r.deliveryDate = ?2 where r.rentId = ?1")
	int updateDeliveryDateToRentByRentId(String rentId, LocalDate deliveryDate);

}
