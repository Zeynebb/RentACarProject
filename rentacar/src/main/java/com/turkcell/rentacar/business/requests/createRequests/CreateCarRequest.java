package com.turkcell.rentacar.business.requests.createRequests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {

	@NotNull
	@Min(0)
	private double dailyPrice;

	@NotNull
	@Size(min = 2)
	private String modelYear;

	@NotNull
	@Size(min = 2, max = 50)
	private String description;

	@NotNull
	@Min(1)
	private int brandId;

	@NotNull
	@Min(1)
	private int colorId;

}
