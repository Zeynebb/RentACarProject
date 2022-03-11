package com.turkcell.rentacar.entities.concretes;

import javax.persistence.CascadeType;
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
@Table(name = "ordered_additional_products")
public class OrderedAdditionalProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ordered_additional_product_id")
	private int orderedAdditionalProductId;

	@Column(name = "ordered_additional_product_amount")
	private int orderedAdditionalProductAmount;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "rent_id")
	private Rent rent;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "additional_product_id")
	private AdditionalProduct additionalProduct;

}
