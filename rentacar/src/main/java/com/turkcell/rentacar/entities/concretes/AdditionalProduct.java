package com.turkcell.rentacar.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "additional_products")
public class AdditionalProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "additional_product_id")
	private int additionalProductId;

	@Column(name = "additional_product_name")
	private String additionalProductName;

	@Column(name = "additional_product_unit_price")
	private double additionalProductUnitPrice;

	@OneToMany(mappedBy = "additionalProduct")
	private List<OrderedAdditionalProduct> orderedAdditionalProducts;

}
