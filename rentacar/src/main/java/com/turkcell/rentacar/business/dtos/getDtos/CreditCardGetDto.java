package com.turkcell.rentacar.business.dtos.getDtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardGetDto {

	private String creditCardNo;

	private String cardHolder;

	private LocalDate expirationDate;

	private String cvv;

	private int userId;

	private String email;
}
