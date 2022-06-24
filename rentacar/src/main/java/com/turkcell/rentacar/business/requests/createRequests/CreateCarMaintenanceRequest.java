package com.turkcell.rentacar.business.requests.createRequests;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarMaintenanceRequest {

	@NotNull
	@Size(min = 2, max = 50)
	private String description;
	
	private LocalDate returnDate;
	
	@NotNull
	@Min(1)
	private int carId;

}
