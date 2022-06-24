package com.turkcell.rentacar.business.requests.updateRequests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBrandToCarRequest {

	@NotNull
	@Min(1)
	private int carId;
	
	@NotNull
	@Min(1)
	private int brandId;

}
