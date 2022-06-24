package com.turkcell.rentacar.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "brands")
public class Brand {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id", unique = true, nullable = false)
    private int brandId;
    
    @Column(name = "brand_name")
    private String brandName;
    
    @JsonIgnore
    @OneToMany(mappedBy = "brand")
    private List<Car> cars;
    
    
}
