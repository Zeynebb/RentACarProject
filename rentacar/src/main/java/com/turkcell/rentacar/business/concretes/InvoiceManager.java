package com.turkcell.rentacar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.abstracts.OrderedAdditionalProductService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.dtos.listDtos.InvioceListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateInvoiceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteInvoiceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateInvoiceRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.InvoiceDao;
import com.turkcell.rentacar.entities.concretes.Invoice;
import com.turkcell.rentacar.entities.concretes.OrderedAdditionalProduct;
import com.turkcell.rentacar.exceptions.businessExceptions.EntityNotFoundException;

@Service
public class InvoiceManager implements InvoiceService {

	private InvoiceDao invoiceDao;
	private ModelMapperService modelMapperService;
	private RentService rentService;
	private OrderedAdditionalProductService orderedAdditionalProductService;

	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService, @Lazy RentService rentService,
			OrderedAdditionalProductService orderedAdditionalProductService) {
		this.invoiceDao = invoiceDao;
		this.modelMapperService = modelMapperService;
		this.rentService = rentService;
		this.orderedAdditionalProductService = orderedAdditionalProductService;
	}

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) {

		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);

		invoice.setTotalPrice(calculateTotalPrice(invoice));

		this.invoiceDao.save(invoice);

		return new SuccessResult("Invoice added successfully.");

	}

	@Override
	public Result Update(UpdateInvoiceRequest updateInvoiceRequest) {

		checkIfInvoiceExists(updateInvoiceRequest.getInvoiceId());

		Invoice invoice = this.modelMapperService.forRequest().map(updateInvoiceRequest, Invoice.class);

		invoice.setTotalPrice(calculateTotalPrice(invoice));

		this.invoiceDao.save(invoice);

		return new SuccessResult("Invoice updated successfully.");
	}

	@Override
	public Result Delete(DeleteInvoiceRequest deleteInvoiceRequest) {

		Invoice invoice = this.modelMapperService.forRequest().map(deleteInvoiceRequest, Invoice.class);

		this.invoiceDao.delete(invoice);

		return new SuccessResult("Invoice deleted successfully.");
	}

	@Override
	public DataResult<List<InvioceListDto>> getAll() {

		List<Invoice> result = this.invoiceDao.findAll();
		List<InvioceListDto> response = result.stream()
				.map(invoice -> modelMapperService.forDto().map(invoice, InvioceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<InvioceListDto>>(response, "Invoices listed successfully.");
	}

	@Override
	public DataResult<InvioceListDto> getByRentId(int rentId) {

		checkIfRentExists(rentId);

		Invoice result = this.invoiceDao.getByRent_RentId(rentId);
		InvioceListDto response = this.modelMapperService.forDto().map(result, InvioceListDto.class);

		return new SuccessDataResult<InvioceListDto>(response, "Invoice listed successfully.");
	}

	@Override
	public DataResult<List<OrderedAdditionalProduct>> getOrderedAdditionalProductByRentId(int rentId) {

		List<OrderedAdditionalProduct> result = this.rentService.getOrderedAdditionalProductsByRentId(rentId).getData();

		return new SuccessDataResult<List<OrderedAdditionalProduct>>(result, "Invoice listed successfully.");
	}

	@Override
	public DataResult<List<InvioceListDto>> getByUserId(int userId) {

		checkIfUserExists(userId);

		List<Invoice> result = this.invoiceDao.getByUser_UserId(userId);
		List<InvioceListDto> response = result.stream()
				.map(invoice -> modelMapperService.forDto().map(invoice, InvioceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<InvioceListDto>>(response, "User's invoice listed successfully.");
	}

	@Override
	public DataResult<List<InvioceListDto>> getAllByBetweenStartDateAndEndDate(LocalDate startDate, LocalDate endDate) {

		List<Invoice> result = this.invoiceDao.getAllByBetweenStartDateAndEndDate(startDate, endDate);
		List<InvioceListDto> response = result.stream()
				.map(invoice -> modelMapperService.forDto().map(invoice, InvioceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<InvioceListDto>>(response,
				"Invoices between start date and end date listed successfully.");
	}

	private double calculateTotalPrice(Invoice invoice) {

		return calculateRentTotalPrice(invoice.getRent().getRentId())
				+ calculateOrderedAdditionalPrice(invoice.getRent().getRentId())
				+ calculateIfCityIsDifferentPrice(invoice.getRent().getRentId());
	}

	private double calculateRentTotalPrice(int rentId) {
		return this.rentService.calculateRentTotalPrice(rentId).getData();
	}

	private double calculateOrderedAdditionalPrice(int rentId) {
		if (checkIfInvoiceHasOrderedAdditionalProduct(rentId)) {
			return this.orderedAdditionalProductService.calculateOrderedAdditionalPrice(rentId).getData();
		}
		return 0;
	}

	private double calculateIfCityIsDifferentPrice(int rentId) {
		if (this.rentService.checkIfReturnCityIsDifferentForRentalCityIsSuccess(rentId).isSuccess()) {
			return 750;
		}
		return 0;
	}

	private void checkIfInvoiceExists(int invoiceId) {
		if (!this.invoiceDao.existsById(invoiceId)) {
			throw new EntityNotFoundException("Invoice not found!");
		}
	}

	private boolean checkIfInvoiceHasOrderedAdditionalProduct(int rentId) {
		if (this.orderedAdditionalProductService.checkIfRentExists(rentId).isSuccess()) {
			return true;
		}
		return false;
	}

	private void checkIfRentExists(int rentId) {
		if (!this.invoiceDao.existsByRent_RentId(rentId)) {
			throw new EntityNotFoundException("Rent not found!");
		}
	}

	private void checkIfUserExists(int userId) {
		if (!this.invoiceDao.existsByUser_UserId(userId)) {
			throw new EntityNotFoundException("User not found!");
		}
	}

}
