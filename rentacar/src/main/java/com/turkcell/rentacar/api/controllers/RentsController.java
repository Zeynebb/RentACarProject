package com.turkcell.rentacar.api.controllers;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.dtos.getDtos.RentGetDto;
import com.turkcell.rentacar.business.dtos.listDtos.RentListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteRentRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateEndedKilometerInfoRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateRentRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/rents")
public class RentsController {

	private RentService rentService;

	@Autowired
	public RentsController(RentService rentService) {
		this.rentService = rentService;
	}

	@PostMapping("/addCorporateCustomer")
	public Result addCorporateCustomer(@RequestBody @Valid CreateRentRequest createRentRequest) {
		return this.rentService.addCorporateCustomer(createRentRequest);

	}

	@PostMapping("/addIndividualCustomer")
	public Result addIndividualCustomer(@RequestBody @Valid CreateRentRequest createRentRequest) {
		return this.rentService.addIndividualCustomer(createRentRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateRentRequest updateRentRequest) {
		return this.rentService.update(updateRentRequest);
	}

	@Transactional
	@PostMapping("/updateEndedKilometer")
	public Result updateEndedKilometer(
			@RequestBody @Valid UpdateEndedKilometerInfoRequest updateEndedKilometerInfoRequest) {
		return this.rentService.updateEndedKilometer(updateEndedKilometerInfoRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteRentRequest deleteRentRequest) {
		return this.rentService.delete(deleteRentRequest);
	}

	@GetMapping("/getAll")
	public DataResult<List<RentListDto>> getAll() {
		return this.rentService.getAll();
	}

	@GetMapping("/getRentDetailsByRentId")
	public DataResult<RentGetDto> getRentDetailsByRentId(@RequestParam int rentId) {
		return this.rentService.getRentDetailsByRentId(rentId);
	}

	@GetMapping("/checkIfCarAlreadyInRent")
	public Result checkIfCarAlreadyInRent(@RequestParam int carId) {
		return this.rentService.checkIfCarAlreadyInRent(carId);
	}

}
