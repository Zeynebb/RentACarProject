package com.turkcell.rentacar.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.turkcell.rentacar.entities.concretes.Invoice;

@Repository
public interface InvoiceDao extends JpaRepository<Invoice, Integer> {

	Invoice getByRent_RentId(int rentId);
	
	boolean existsByRent_RentId(int rentId);
	
	boolean existsByUser_UserId(int userId);

	List<Invoice> getByUser_UserId(int userId);

	// @Query("FROM Invoice i WHERE i.invoiceCreateDate BETWEEN ?1 AND ?2")
	@Query("SELECT i FROM Invoice i WHERE i.invoiceCreateDate BETWEEN :startDate AND :endDate")
	List<Invoice> getAllByBetweenStartDateAndEndDate(LocalDate startDate, LocalDate endDate);
}
