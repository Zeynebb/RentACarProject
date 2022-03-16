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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.OrderedAdditionalProductService;
import com.turkcell.rentacar.business.dtos.listDtos.OrderedAdditionalProductListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateOrderedAdditionalProductRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteOrderedAdditionalProductRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateOrderedAdditionalProductRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/orderedAdditionalProducts")
public class OrderedAdditionalProductsController {

	OrderedAdditionalProductService orderedAdditionalProductService;

	@Autowired
	public OrderedAdditionalProductsController(OrderedAdditionalProductService orderedAdditionalProductService) {
		this.orderedAdditionalProductService = orderedAdditionalProductService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateOrderedAdditionalProductRequest createOrderedAdditionalProductRequest) {
		return this.orderedAdditionalProductService.add(createOrderedAdditionalProductRequest);
	}

	@PutMapping("/update")
	public Result update(
			@RequestBody @Valid UpdateOrderedAdditionalProductRequest updateOrderedAdditionalProductRequest) {
		return this.orderedAdditionalProductService.update(updateOrderedAdditionalProductRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(
			@RequestBody @Valid DeleteOrderedAdditionalProductRequest deleteOrderedAdditionalProductRequest) {
		return this.orderedAdditionalProductService.delete(deleteOrderedAdditionalProductRequest);
	}

	@GetMapping("/getAll")
	public DataResult<List<OrderedAdditionalProductListDto>> getAll() {
		return this.orderedAdditionalProductService.getAll();
	}

	@GetMapping("/checkIfRentExists")
	public Result checkIfRentExists(@RequestParam int rentId) {
		return this.orderedAdditionalProductService.checkIfRentExists(rentId);
	}

	@GetMapping("/getByRentId")
	public DataResult<List<OrderedAdditionalProductListDto>> getByRentId(@RequestParam int rentId) {
		return this.orderedAdditionalProductService.getByRentId(rentId);
	}

	@GetMapping("/calculateOrderedAdditionalPrice")
	public DataResult<Double> calculateOrderedAdditionalPrice(@RequestParam int rentId) {
		return this.orderedAdditionalProductService.calculateOrderedAdditionalPrice(rentId);
	}

}
