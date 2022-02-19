package com.turkcell.rentacar.dataAccess.abstracts;

import com.turkcell.rentacar.entities.concretes.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandDao extends JpaRepository<Brand,Integer> {
	
	Brand getByBrandName(String name);
	Brand getBrandByBrandId(int id);
	
    boolean existsBrandByBrandName(String name);
}
