package com.turkcell.rentacar.business.requests.createRequests;


import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIndividualCustomerRequest {

	@NotNull
	@Size(min = 2, max = 20)
	private String firstName;

	@NotNull
	@Size(min = 2, max = 20)
	private String lastName;

	@NotNull
	@Size(min = 2, max = 11)
	private String nationalIdentity;

	@NotNull
	@Size(min = 2, max = 20)
	@Email(message = "This value is not email!")
	private String email;

	@NotNull
	@Size(min = 2, max = 30)
	private String password;

}
