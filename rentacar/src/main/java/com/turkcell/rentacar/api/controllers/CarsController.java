package com.turkcell.rentacar.api.controllers;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.dtos.listDtos.CarListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateBrandToCarRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateColorToCarRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.exceptions.BusinessException;

@RestController
@RequestMapping("/api/cars")
public class CarsController {

	private CarService carService;

	public CarsController(CarService carService) {
		super();
		this.carService = carService;
	}

	@GetMapping("/getAll")
	public DataResult<List<CarListDto>> getAll() {
		return this.carService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<CarListDto> getById(@RequestParam(value = "id") int id) {
		return this.carService.getById(id);
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateCarRequest createCarRequest) throws BusinessException {
		return this.carService.add(createCarRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody DeleteCarRequest deleteCarRequest)  throws BusinessException{
		return this.carService.delete(deleteCarRequest);
	}

	@PostMapping("/update")
	public Result update(@RequestBody UpdateCarRequest updateCarRequest) throws BusinessException {
		return this.carService.update(updateCarRequest);
	}

	@Transactional
	@PostMapping("updateColor")
	public Result updateColor(@RequestBody UpdateColorToCarRequest updateColorToCarRequest) throws BusinessException {
		return this.carService.updateColor(updateColorToCarRequest);
	}

	@Transactional
	@PostMapping("updateBrand")
	public Result updateBrand(@RequestBody UpdateBrandToCarRequest updateBrandToCarRequest) throws BusinessException {
		return this.carService.updateBrand(updateBrandToCarRequest);
	}

	@GetMapping("/findByDailyPriceLessThanEqual")
	public DataResult<List<CarListDto>> findByDailyPriceLessThanEqual(
			@RequestParam(value = "dailyPrice") double dailyPrice) {
		return this.carService.findByDailyPriceLessThanEqual(dailyPrice);
	}

	@GetMapping("/getAllPaged")
	public DataResult<List<CarListDto>> getAllPaged(@RequestParam(value = "pageNo") int pageNo,
			@RequestParam(value = "pageSize") int pageSize) {
		return this.carService.getAllPaged(pageNo, pageSize);
	}

	@GetMapping("/getAllSorted")
	public DataResult<List<CarListDto>> getAllSorted(@RequestParam(value = "sort") boolean sort) {
		return this.carService.getAllSorted(sort);

	}

}
