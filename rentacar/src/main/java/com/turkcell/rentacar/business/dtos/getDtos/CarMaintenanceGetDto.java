package com.turkcell.rentacar.business.dtos.getDtos;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarMaintenanceGetDto {

	private String description;
	private LocalDate returnDate;
	private int brandName;
	private double dailyPrice;
	private int modelYear;
	private String colorName;

}
