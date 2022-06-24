package com.turkcell.rentacar.business.requests.createRequests;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {

	@NotNull
	@Size(min = 2)
	private String creditCardNo;

	@NotNull
	@Size(min = 2)
	private String cardHolder;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate expirationDate;

	@NotNull
	@Size(min = 3)
	private String cvv;
	
	@NotNull
	@Min(1)
	private int userId;
	
	@NotNull
	@Min(1)
	private String rentId;

}
