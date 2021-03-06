package com.turkcell.rentacar.business.dtos.getDtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentGetDto {

	private int rentId;

	private LocalDate rentDate;

	private LocalDate rentReturnDate;

	private boolean rentStatus;

	private int carId;

	private String brandName;

	private double dailyPrice;

	private String modelYear;

	private String colorName;

	private double additionalServicePrice;

	private String rentalCity;

	private String returnCity;

}
