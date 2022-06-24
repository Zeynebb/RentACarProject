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
public class UpdateCarDamageRequest {

	@NotNull
	@Min(1)
	private int carDamageId;

	@NotNull
	@Size(min = 2)
	private String damageInfo;

	@NotNull
	@Min(1)
	private int carId;

}
