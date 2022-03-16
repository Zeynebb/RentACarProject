package com.turkcell.rentacar.business.dtos.listDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDamageListDto {

	private String damageInfo;

	private String modelYear;
	private String description;

	private String brandName;

}
