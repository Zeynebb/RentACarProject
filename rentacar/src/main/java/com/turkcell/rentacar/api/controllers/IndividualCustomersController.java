package com.turkcell.rentacar.api.controllers;

import com.turkcell.rentacar.business.requests.createRequests.CreateIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateIndividualCustomerRequest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.exceptions.BusinessException;

@RestController
@RequestMapping("/api/individualCustomers")
public class IndividualCustomersController {

	private IndividualCustomerService individualCustomerService;

	@Autowired
	public IndividualCustomersController(IndividualCustomerService individualCustomerService) {
		this.individualCustomerService = individualCustomerService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateIndividualCustomerRequest createIndividualCustomerRequest)
			throws BusinessException {
		return this.individualCustomerService.add(createIndividualCustomerRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateIndividualCustomerRequest updateIndividualCustomerRequest)
			throws BusinessException {
		return this.individualCustomerService.update(updateIndividualCustomerRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteIndividualCustomerRequest deleteIndividualCustomerRequest)
			throws BusinessException {
		return this.individualCustomerService.delete(deleteIndividualCustomerRequest);
	}
}
