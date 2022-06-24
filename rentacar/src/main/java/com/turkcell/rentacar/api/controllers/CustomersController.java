package com.turkcell.rentacar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.CustomerService;
import com.turkcell.rentacar.business.dtos.listDtos.CustomerListDto;
import com.turkcell.rentacar.core.utilities.results.DataResult;

@RestController
@RequestMapping("/api/customers")
public class CustomersController {

	private CustomerService customerService;

	@Autowired
	public CustomersController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping("/getAll")
	public DataResult<List<CustomerListDto>> getAll() {
		return this.customerService.getAll();
	}

}
