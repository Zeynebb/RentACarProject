package com.turkcell.rentacar.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name = "payments")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_id")
	private int paymentId;

	@Column(name = "credit_card_no")
	private String creditCardNo;

	@Column(name = "card_holder")
	private String cardHolder;

	@Column(name = "expiration_date")
	private LocalDate expirationDate;

	@Column(name = "cvv")
	private String cvv;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "rent_id")
	private Rent rent;

	@OneToMany(mappedBy = "payment")
	private List<Invoice> invoices;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

}
