package com.turkcell.rentacar.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.turkcell.rentacar.entities.abstracts.User;

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
	private boolean rentStatus = true;

	@Column(name = "started_kilometer_info")
	private String startedKilometerInfo;

	@Column(name = "ended_kilometer_info")
	private String endedKilometerInfo;

	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;

	@ManyToOne
	@JoinColumn(name = "rental_city_id")
	private City rentalCity;

	@ManyToOne
	@JoinColumn(name = "return_city_id")
	private City returnCity;

	@OneToMany(mappedBy = "rent")
	private List<OrderedAdditionalProduct> orderedAdditionalProducts;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "rent")
	private List<Invoice> invoices;
	
	@OneToMany(mappedBy = "rent")
	private List<Payment> payments;

}
