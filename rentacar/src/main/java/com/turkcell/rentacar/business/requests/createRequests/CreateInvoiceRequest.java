package com.turkcell.rentacar.business.requests.createRequests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {

	@NotNull
	@Min(1)
	private int userId;

	@NotNull
	@Min(1)
	private int rentId;

}
