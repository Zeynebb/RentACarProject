package com.turkcell.rentacar.business.requests.createRequests;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentRequest {

	@NotNull
	private LocalDate rentDate;
	@NotNull
	private LocalDate rentReturnDate;
	@NotNull
	private boolean rentStatus;
	@NotNull
	private int carId;

}
