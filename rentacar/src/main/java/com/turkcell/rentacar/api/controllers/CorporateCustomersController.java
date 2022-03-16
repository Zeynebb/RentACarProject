package com.turkcell.rentacar.api.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentacar.business.requests.createRequests.CreateCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCorporateCustomerRequest;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.exceptions.BusinessException;

@RestController
@RequestMapping("/api/corporateCustomers")
public class CorporateCustomersController {

	CorporateCustomerService corporateCustomerService;

	@Autowired
	public CorporateCustomersController(CorporateCustomerService corporateCustomerService) {
		this.corporateCustomerService = corporateCustomerService;
	}

	@PostMapping("/add")
	Result add(@RequestBody @Valid CreateCorporateCustomerRequest createCorporateCustomerRequest)
			throws BusinessException {
		return this.corporateCustomerService.add(createCorporateCustomerRequest);
	}

	@PutMapping("/update")
	Result update(@RequestBody @Valid UpdateCorporateCustomerRequest updateCorporateCustomerRequest)
			throws BusinessException {
		return this.corporateCustomerService.update(updateCorporateCustomerRequest);
	}

	@DeleteMapping("/delete")
	Result delete(@RequestBody @Valid DeleteCorporateCustomerRequest deleteCorporateCustomerRequest)
			throws BusinessException {
		return this.corporateCustomerService.delete(deleteCorporateCustomerRequest);
	}

}
