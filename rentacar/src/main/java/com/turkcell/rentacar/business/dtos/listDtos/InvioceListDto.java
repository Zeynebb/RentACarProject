package com.turkcell.rentacar.business.dtos.listDtos;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvioceListDto {

	private LocalDate invoiceCreateDate;

	private double totalPrice;

	private LocalDate rentDate;

	private LocalDate rentReturnDate;

	private String rentalCity;

	private String returnCity;

	private String email;
}
