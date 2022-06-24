package com.turkcell.rentacar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.CreditCardService;
import com.turkcell.rentacar.business.dtos.getDtos.CreditCardGetDto;
import com.turkcell.rentacar.business.dtos.listDtos.CreditCardListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCreditCardRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCreditCardRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCreditCardRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/creditCards")
public class CreditCardsController {

	private CreditCardService creditCardService;

	@Autowired
	public CreditCardsController(CreditCardService creditCardService) {
		this.creditCardService = creditCardService;
	}

	@PostMapping("/add")
	public Result add(CreateCreditCardRequest createCreditCardRequest) {
		return this.creditCardService.add(createCreditCardRequest);
	}

	@PutMapping("/update")
	public Result update(UpdateCreditCardRequest updateCreditCardRequest) {
		return this.creditCardService.update(updateCreditCardRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(DeleteCreditCardRequest deleteCreditCardRequest) {
		return this.creditCardService.delete(deleteCreditCardRequest);
	}

	@GetMapping("/getAll")
	public DataResult<List<CreditCardListDto>> getAll() {
		return this.creditCardService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<CreditCardGetDto> getById(int creditCardId) {
		return this.creditCardService.getById(creditCardId);
	}

}
