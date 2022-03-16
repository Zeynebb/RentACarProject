package com.turkcell.rentacar.api.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.CustomerService;
import com.turkcell.rentacar.business.requests.createRequests.CreateCustomerRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCustomerRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCustomerRequest;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.exceptions.BusinessException;

@RestController
@RequestMapping("/api/customers")
public class CustomersController {

	private CustomerService customerService;

	@Autowired
	public CustomersController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCustomerRequest createCustomerRequest) throws BusinessException {
		return this.customerService.add(createCustomerRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCustomerRequest updateCustomerRequest) throws BusinessException {
		return this.customerService.update(updateCustomerRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCustomerRequest deleteCustomerRequest) throws BusinessException {
		return this.customerService.delete(deleteCustomerRequest);
	}

}
