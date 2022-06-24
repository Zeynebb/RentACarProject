package com.turkcell.rentacar.business.requests.deleteRequests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteRentRequest {

	@NotNull
	@Min(1)
	private String rentId;
	
}
