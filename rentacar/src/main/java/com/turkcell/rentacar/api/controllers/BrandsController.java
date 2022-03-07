package com.turkcell.rentacar.api.controllers;

import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.business.dtos.listDtos.BrandListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteBrandRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateBrandRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.exceptions.BusinessException;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandsController {

	private BrandService brandService;

	public BrandsController(BrandService brandService) {
		this.brandService = brandService;
	}

	@GetMapping("/getAll")
	public DataResult<List<BrandListDto>> getAll() {
		return this.brandService.getAll();
	}

	@PostMapping("/add")
	public Result save(@RequestBody CreateBrandRequest createBrandRequest) throws BusinessException {
		return this.brandService.add(createBrandRequest);
	}

	@GetMapping("/getById")
	public DataResult<BrandListDto> getById(@RequestParam int id) {
		return this.brandService.getById(id);
	}

	@PostMapping("/update")
	public Result update(@RequestBody UpdateBrandRequest updateBrandRequest) throws BusinessException {
		return this.brandService.update(updateBrandRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody DeleteBrandRequest deleteBrandRequest) {
		return this.brandService.delete(deleteBrandRequest);
	}
}
