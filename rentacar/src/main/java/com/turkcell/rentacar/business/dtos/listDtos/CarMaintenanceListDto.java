package com.turkcell.rentacar.business.dtos.listDtos;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarMaintenanceListDto {
	
	private String description;
	private LocalDate returnDate;
	private String brandName;

}
