package com.turkcell.rentacar.business.dtos.listDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedAdditionalProductListDto {

	
	private int orderedAdditionalProductAmount;
	
	private int additionalProductId;
	private String additionalProductName;
	private double additionalProductUnitPrice;
	
	
	
	

}
