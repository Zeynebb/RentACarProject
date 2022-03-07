package com.turkcell.rentacar.business.dtos.listDtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentListDto {

	private LocalDate rentDate;
	private LocalDate rentReturnDate;
	private boolean rentStatus;
	private String brandName;

}
