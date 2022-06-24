package com.turkcell.rentacar.business.dtos.getDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentCityGetDto {

	private String brandName;

	private double additionalServicePrice;

	private String rentalCityName;

	private String returnCityname;

}
