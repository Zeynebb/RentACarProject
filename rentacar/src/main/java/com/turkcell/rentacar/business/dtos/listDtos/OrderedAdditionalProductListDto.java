package com.turkcell.rentacar.business.dtos.listDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedAdditionalProductListDto {

	private String brandName;

	private String additionalProductName;

}
