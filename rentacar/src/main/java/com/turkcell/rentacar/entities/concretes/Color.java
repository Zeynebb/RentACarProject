package com.turkcell.rentacar.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "colors")
public class Color {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "color_id", insertable = true, updatable = true)
	private int colorId;

	@Column(name = "color_name")
	private String colorName;

	@JsonIgnore
	@OneToMany(mappedBy = "color")
	private List<Car> cars;
}
