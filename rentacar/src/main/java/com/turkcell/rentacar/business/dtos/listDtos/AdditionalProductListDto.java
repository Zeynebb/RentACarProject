package com.turkcell.rentacar.business.dtos.listDtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalProductListDto {
	
	private String additionalProductName;
	
	private double additionalProductUnitPrice;

}
