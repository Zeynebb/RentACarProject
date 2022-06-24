package com.turkcell.rentacar.business.dtos.getDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalProductGetDto {

	private String additionalProductName;
	
	private double additionalProductUnitPrice;
}
