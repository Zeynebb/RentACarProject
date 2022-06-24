package com.turkcell.rentacar.business.requests.createRequests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderedAdditionalProductRequest {

	@NotNull
	@Min(1)
	private int orderedAdditionalProductAmount;

	@NotNull
	@Min(1)
	private String rentId;

	@NotNull
	@Min(1)
	private int additionalProductId;

}
