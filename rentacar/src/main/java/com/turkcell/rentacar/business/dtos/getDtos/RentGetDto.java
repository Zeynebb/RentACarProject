package com.turkcell.rentacar.business.dtos.getDtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentGetDto {
	private LocalDate rentDate;
	private LocalDate rentReturnDate;
	private boolean rentStatus;
	private String brandName;
	private String modelYear;
	private String colorName;

}
