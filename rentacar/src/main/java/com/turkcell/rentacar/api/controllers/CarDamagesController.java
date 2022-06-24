package com.turkcell.rentacar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.CarDamageService;
import com.turkcell.rentacar.business.dtos.listDtos.CarDamageListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarDamageRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarDamageRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarDamageRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/carDamages")
public class CarDamagesController {

	private CarDamageService carDamageService;

	@Autowired
	public CarDamagesController(CarDamageService carDamageService) {
		this.carDamageService = carDamageService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarDamageRequest createCarDamageRequest) {
		return this.carDamageService.add(createCarDamageRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCarDamageRequest updateCarDamageRequest) {
		return this.carDamageService.update(updateCarDamageRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCarDamageRequest deleteCarDamageRequest) {
		return this.carDamageService.delete(deleteCarDamageRequest);
	}

	@GetMapping("/getAll")
	public DataResult<List<CarDamageListDto>> getAll() {
		return this.carDamageService.getAll();
	}

}
