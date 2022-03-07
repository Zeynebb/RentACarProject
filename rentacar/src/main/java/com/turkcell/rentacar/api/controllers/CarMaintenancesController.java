package com.turkcell.rentacar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.dtos.getDtos.CarMaintenanceGetDto;
import com.turkcell.rentacar.business.dtos.listDtos.CarMaintenanceListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarMaintenanceRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.exceptions.BusinessException;

@RestController
@RequestMapping("/api/carMaintenances")
public class CarMaintenancesController {

	private CarMaintenanceService carMaintenanceService;

	@Autowired
	public CarMaintenancesController(CarMaintenanceService carMaintenanceService) {
		this.carMaintenanceService = carMaintenanceService;
	}

	@GetMapping("/getAll")
	public DataResult<List<CarMaintenanceListDto>> getAll() {
		return this.carMaintenanceService.getAll();
	}

	@GetMapping("/getCarMaintenanceDetailsByCarMaintenanceId")
	public DataResult<CarMaintenanceGetDto> getCarMaintenanceDetailsByCarMaintenanceId(
			@RequestParam int carMaintenanceId) {
		return this.carMaintenanceService.getCarMaintenanceDetailsByCarMaintenanceId(carMaintenanceId);
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
		return this.carMaintenanceService.add(createCarMaintenanceRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody UpdateCarMaintenanceRequest updateCarMaintenanceRequest)
			throws BusinessException {
		return this.carMaintenanceService.update(updateCarMaintenanceRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody DeleteCarMaintenanceRequest deleteCarMaintenanceRequest)
			throws BusinessException {
		return this.carMaintenanceService.delete(deleteCarMaintenanceRequest);
	}

	@GetMapping("/checkIfCarIsAlreadyInMaintenance")
	public Result checkIfCarIsAlreadyInMaintenance(@RequestParam int carId) throws BusinessException {
		return this.carMaintenanceService.checkIfCarIsAlreadyInMaintenance(carId);
	}

}
