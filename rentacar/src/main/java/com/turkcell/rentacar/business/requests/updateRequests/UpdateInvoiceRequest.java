package com.turkcell.rentacar.business.requests.updateRequests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvoiceRequest {
	
	@NotNull
	@Min(1)
	private int invoiceId;

	@NotNull
	@Min(1)
	private int rentId;

	@NotNull
	@Min(1)
	private int userId;

}
