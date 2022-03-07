package com.turkcell.rentacar.api.controllers;

import com.turkcell.rentacar.business.abstracts.ColorService;
import com.turkcell.rentacar.business.dtos.listDtos.ColorListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateColorRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteColorRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateColorRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.exceptions.BusinessException;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colors")
public class ColorsController {

	private ColorService colorService;

	public ColorsController(ColorService colorService) {
		this.colorService = colorService;
	}

	@GetMapping("/getAll")
	public DataResult<List<ColorListDto>> getAll() {
		return this.colorService.getAll();
	}

	@PostMapping("/add")
	public void save(@RequestBody CreateColorRequest createColorRequest)  throws BusinessException {
		this.colorService.add(createColorRequest);
	}

	@GetMapping("/getById")
	public DataResult<ColorListDto> getById(@RequestParam int id) {
		return this.colorService.getById(id);
	}

	@PostMapping("/update")
	public Result update(@RequestBody UpdateColorRequest updateColorRequest) throws BusinessException{
		return this.colorService.update(updateColorRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody DeleteColorRequest deleteColorRequest) {
		return this.colorService.delete(deleteColorRequest);
	}
}
