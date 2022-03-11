package com.turkcell.rentacar.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rents")
@Inheritance(strategy = InheritanceType.JOINED)
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
	private boolean rentStatus = true;

	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;
	
	@Column(name = "additional_service_price")
	private double additionalServicePrice;

	@ManyToOne
	@JoinColumn(name = "rental_city_id")
	private City rentalCity;

	@ManyToOne
	@JoinColumn(name = "return_city_id")
	private City returnCity;

	@OneToMany(mappedBy = "rent")
	private List<OrderedAdditionalProduct> orderedAdditionalProducts;

}
