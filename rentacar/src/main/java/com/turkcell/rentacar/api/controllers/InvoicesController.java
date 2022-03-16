package com.turkcell.rentacar.api.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.dtos.listDtos.InvioceListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateInvoiceRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.OrderedAdditionalProduct;
import com.turkcell.rentacar.exceptions.BusinessException;

@RestController
@RequestMapping("/api/invoices")
public class InvoicesController {

	private InvoiceService invoicesService;

	@Autowired
	public InvoicesController(InvoiceService invoicesService) {
		this.invoicesService = invoicesService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateInvoiceRequest createInvoiceRequest) throws BusinessException {
		return this.invoicesService.add(createInvoiceRequest);
	}

	@GetMapping("/getAll")
	public DataResult<List<InvioceListDto>> getAll() {
		return this.invoicesService.getAll();
	}

	@GetMapping("/getByRentId")
	public DataResult<InvioceListDto> getByRentId(@RequestParam int rentId) throws BusinessException {
		return this.invoicesService.getByRentId(rentId);
	}

	@GetMapping("/getOrderedAdditionalProductByRentId")
	public DataResult<List<OrderedAdditionalProduct>> getOrderedAdditionalProductByRentId(@RequestParam int rentId) {
		return this.invoicesService.getOrderedAdditionalProductByRentId(rentId);
	}

	@GetMapping("/getByUserId")
	public DataResult<List<InvioceListDto>> getByUserId(@RequestParam int userId) throws BusinessException {
		return this.invoicesService.getByUserId(userId);
	}

	@GetMapping("/getAllByBetweenStartDateAndEndDate")
	public DataResult<List<InvioceListDto>> getAllByBetweenStartDateAndEndDate(
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") @RequestParam("startDate") LocalDate startDate,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") @RequestParam("endDate") LocalDate endDate) {
		return this.invoicesService.getAllByBetweenStartDateAndEndDate(startDate, endDate);
	}

}
