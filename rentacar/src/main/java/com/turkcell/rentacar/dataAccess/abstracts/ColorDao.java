package com.turkcell.rentacar.dataAccess.abstracts;

import com.turkcell.rentacar.business.dtos.listDtos.ColorListDto;
import com.turkcell.rentacar.entities.concretes.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorDao extends JpaRepository<Color, Integer> {
	boolean existsColorByColorName(String name);

	ColorListDto getColorByColorId(int id);

}
