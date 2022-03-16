package com.turkcell.rentacar.business.requests.createRequests;

import java.time.LocalDate;

import javax.validation.constraints.Min;
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
	@Min(1)
	private int carId;

	@NotNull
	@Min(1)
	private int rentalCityId;

	@NotNull
	@Min(1)
	private int returnCityId;

	@NotNull
	@Min(1)
	private int userId;

}
