package com.turkcell.rentacar.business.dtos.listDtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardListDto {

	private String creditCardNo;

	private String cardHolder;

	private LocalDate expirationDate;

	private String cvv;
	

}
