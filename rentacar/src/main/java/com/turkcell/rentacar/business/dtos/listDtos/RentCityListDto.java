package com.turkcell.rentacar.business.dtos.listDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentCityListDto {

	private String brandName;

	private double additionalServicePrice;

	private String rentalCityName;

	private String returnCityname;

}
