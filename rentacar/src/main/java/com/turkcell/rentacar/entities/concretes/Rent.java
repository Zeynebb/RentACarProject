package com.turkcell.rentacar.entities.concretes;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rents")
public class Rent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rent_id")
	private int rentId;

	@Column(name = "rent_date")
	private LocalDate rentDate;

	@Column(name = "rent_return_date")
	private LocalDate rentReturnDate;

	@Column(name = "rent_status")
	private boolean rentStatus;

	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;

}
