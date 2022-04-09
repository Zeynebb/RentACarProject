package com.turkcell.rentacar.business.requests.updateRequests;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCreditCardRequest {

	@NotNull
	@Min(1)
	private int creditCardId;

	@NotNull
	@Size(min = 2)
	private String creditCardNo;

	@NotNull
	@Size(min = 2)
	private String cardHolder;

	@NotNull
	private LocalDate expirationDate;

	@NotNull
	@Size(min = 3)
	private String cvv;
	
	@NotNull
	@Min(1)
	private int userId;

}
