package com.turkcell.rentacar.business.requests.updateRequests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {

	@NotNull
	@Min(1)
	private int carId;

	@NotNull
	@Min(0)
	private double dailyPrice;

	@NotNull
	@Size(min = 4)
	private String modelYear;

	@NotNull
	@Size(min = 2, max = 50)
	private String description;
}
