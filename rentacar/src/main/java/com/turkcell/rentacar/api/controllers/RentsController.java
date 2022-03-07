package com.turkcell.rentacar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.dtos.getDtos.RentGetDto;
import com.turkcell.rentacar.business.dtos.listDtos.RentListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteRentRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateRentRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.exceptions.BusinessException;

@RestController
@RequestMapping("/api/rents")
public class RentsController {

	private RentService rentService;

	@Autowired
	public RentsController(RentService rentService) {
		this.rentService = rentService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateRentRequest createRentRequest) throws BusinessException {
		return this.rentService.add(createRentRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody UpdateRentRequest updateRentRequest) throws BusinessException {
		return this.rentService.update(updateRentRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody DeleteRentRequest deleteRentRequest) throws BusinessException {
		return this.rentService.delete(deleteRentRequest);
	}

	@GetMapping("/getAll")
	public DataResult<List<RentListDto>> getAll() {
		return this.rentService.getAll();
	}

	@GetMapping("/getRentDetailsByRentId")
	public DataResult<RentGetDto> getRentDetailsByRentId(int rentId) {
		return this.rentService.getRentDetailsByRentId(rentId);
	}

	@GetMapping("/checkIfCarAlreadyInRent")
	public Result checkIfCarAlreadyInRent(int carId) throws BusinessException {
		return this.rentService.checkIfCarAlreadyInRent(carId);
	}

}
