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

import com.turkcell.rentacar.business.abstracts.AdditionalProductService;
import com.turkcell.rentacar.business.dtos.listDtos.AdditionalProductListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateAdditionalProductRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteAdditionalProductRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateAdditionalProductRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/additionalProducts")
public class AdditionalProductsController {

	private AdditionalProductService additionalProductService;

	@Autowired
	public AdditionalProductsController(AdditionalProductService additionalProductService) {
		this.additionalProductService = additionalProductService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateAdditionalProductRequest createAdditionalProductRequest) {
		return this.additionalProductService.add(createAdditionalProductRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateAdditionalProductRequest updateAdditionalProductRequest) {
		return this.additionalProductService.update(updateAdditionalProductRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteAdditionalProductRequest deleteAdditionalProductRequest) {
		return this.additionalProductService.delete(deleteAdditionalProductRequest);
	}

	@GetMapping("/getAll")
	public DataResult<List<AdditionalProductListDto>> getAll() {
		return this.additionalProductService.getAll();
	}
}
