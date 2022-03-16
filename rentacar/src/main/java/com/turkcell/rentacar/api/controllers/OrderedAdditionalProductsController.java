package com.turkcell.rentacar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.OrderedAdditionalProductService;
import com.turkcell.rentacar.business.dtos.listDtos.OrderedAdditionalProductListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateOrderedAdditionalProductRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteOrderedAdditionalProductRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateOrderedAdditionalProductRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.exceptions.BusinessException;

@RestController
@RequestMapping("/api/orderedAdditionalProducts")
public class OrderedAdditionalProductsController {

	OrderedAdditionalProductService orderedAdditionalProductService;

	@Autowired
	public OrderedAdditionalProductsController(OrderedAdditionalProductService orderedAdditionalProductService) {
		this.orderedAdditionalProductService = orderedAdditionalProductService;
	}

	@PostMapping("/add")
	public Result add(CreateOrderedAdditionalProductRequest createOrderedAdditionalProductRequest)
			throws BusinessException {
		return this.orderedAdditionalProductService.add(createOrderedAdditionalProductRequest);
	}

	@PutMapping("/update")
	public Result update(UpdateOrderedAdditionalProductRequest updateOrderedAdditionalProductRequest)
			throws BusinessException {
		return this.orderedAdditionalProductService.update(updateOrderedAdditionalProductRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(DeleteOrderedAdditionalProductRequest deleteOrderedAdditionalProductRequest)
			throws BusinessException {
		return this.orderedAdditionalProductService.delete(deleteOrderedAdditionalProductRequest);
	}

	@GetMapping("/getAll")
	public DataResult<List<OrderedAdditionalProductListDto>> getAll() {
		return this.orderedAdditionalProductService.getAll();
	}

	@GetMapping("/checkIfRentExists")
	public Result checkIfRentExists(int rentId) {
		return this.orderedAdditionalProductService.checkIfRentExists(rentId);
	}

	@GetMapping("/getByRentId")
	public DataResult<List<OrderedAdditionalProductListDto>> getByRentId(int rentId) {
		return this.orderedAdditionalProductService.getByRentId(rentId);
	}

	@GetMapping("/calculateOrderedAdditionalPrice")
	public DataResult<Double> calculateOrderedAdditionalPrice(int rentId) throws BusinessException {
		return this.orderedAdditionalProductService.calculateOrderedAdditionalPrice(rentId);
	}

}
