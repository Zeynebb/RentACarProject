package com.turkcell.rentacar.entities.concretes;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.turkcell.rentacar.entities.abstracts.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoices")
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoice_id")
	private int invoiceId;

	@Column(name = "invoice_create_date")
	private LocalDate invoiceCreateDate = LocalDate.now();

	@Column(name = "total_price")
	private double totalPrice;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "rent_id")
	private Rent rent;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "payment_id")
	private Payment payment;

}
