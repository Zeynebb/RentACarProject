package com.turkcell.rentacar.business.requests.createRequests;

import java.util.List;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMakePaymentRequest {
	
	@NotNull
	private CreatePaymentRequest createPaymentRequest;
	
	@NotNull
	private CreateRentRequest createRentRequest;
	
	@NotNull
	private CreateInvoiceRequest createInvoiceRequest;
	
	private List<CreateOrderedAdditionalProductRequest> createOrderedAdditionalProductRequests;

}
