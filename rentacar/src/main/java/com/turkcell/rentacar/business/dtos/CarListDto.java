package com.turkcell.rentacar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarListDto {

	private double dailyPrice;
	private String modelYear;
	private String description;

	private String brandName;
	private String colorName;

}
