package com.turkcell.rentacar.business.dtos.getDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedAdditionalProductGetDto {

	private int orderedAdditionalProductAmount;

	private String brandName;

	private String additionalProductName;

	private double additionalProductUnitPrice;

}
