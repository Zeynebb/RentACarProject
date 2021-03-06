package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.OrderedAdditionalProductService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.listDtos.OrderedAdditionalProductListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateOrderedAdditionalProductRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteOrderedAdditionalProductRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateOrderedAdditionalProductRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.ErrorResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.OrderedAdditionalProductDao;
import com.turkcell.rentacar.entities.concretes.OrderedAdditionalProduct;
import com.turkcell.rentacar.exceptions.businessExceptions.EntityNotFoundException;

@Service
public class OrderedAdditionalProductManager implements OrderedAdditionalProductService {

	private OrderedAdditionalProductDao orderedAdditionalProductDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public OrderedAdditionalProductManager(OrderedAdditionalProductDao orderedAdditionalProductDao,
			ModelMapperService modelMapperService) {
		this.orderedAdditionalProductDao = orderedAdditionalProductDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateOrderedAdditionalProductRequest createOrderedAdditionalProductRequest) {

		OrderedAdditionalProduct orderedAdditionalProduct = this.modelMapperService.forRequest()
				.map(createOrderedAdditionalProductRequest, OrderedAdditionalProduct.class);

		orderedAdditionalProduct.setOrderedAdditionalProductId(0);
		this.orderedAdditionalProductDao.save(orderedAdditionalProduct);

		return new SuccessResult(BusinessMessages.ORDERED_ADDITIONAL_PRODUCT_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateOrderedAdditionalProductRequest updateOrderedAdditionalProductRequest) {

		checkIfOrderedAdditionalProductExists(updateOrderedAdditionalProductRequest.getOrderedAdditionalProductId());

		OrderedAdditionalProduct orderedAdditionalProduct = this.modelMapperService.forRequest()
				.map(updateOrderedAdditionalProductRequest, OrderedAdditionalProduct.class);

		this.orderedAdditionalProductDao.save(orderedAdditionalProduct);

		return new SuccessResult(BusinessMessages.ORDERED_ADDITIONAL_PRODUCT_UPDATED_SUCCESSFULLY);
	}

	@Override
	public Result delete(DeleteOrderedAdditionalProductRequest deleteOrderedAdditionalProductRequest) {

		checkIfOrderedAdditionalProductExists(deleteOrderedAdditionalProductRequest.getOrderedAdditionalProductId());

		OrderedAdditionalProduct orderedAdditionalProduct = this.modelMapperService.forRequest()
				.map(deleteOrderedAdditionalProductRequest, OrderedAdditionalProduct.class);

		this.orderedAdditionalProductDao.delete(orderedAdditionalProduct);

		return new SuccessResult(BusinessMessages.ORDERED_ADDITIONAL_PRODUCT_DELETED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<OrderedAdditionalProductListDto>> getAll() {

		List<OrderedAdditionalProduct> result = this.orderedAdditionalProductDao.findAll();
		List<OrderedAdditionalProductListDto> response = result.stream()
				.map(orderedAdditionalProduct -> this.modelMapperService.forDto().map(orderedAdditionalProduct,
						OrderedAdditionalProductListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<OrderedAdditionalProductListDto>>(response,
				BusinessMessages.ORDERED_ADDITIONAL_PRODUCTS_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<OrderedAdditionalProductListDto>> getByRentId(String rentId) {

		List<OrderedAdditionalProduct> result = this.orderedAdditionalProductDao.getByRent_RentId(rentId);
		List<OrderedAdditionalProductListDto> response = result.stream()
				.map(orderedAdditionalProduct -> this.modelMapperService.forDto().map(orderedAdditionalProduct,
						OrderedAdditionalProductListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<OrderedAdditionalProductListDto>>(response,
				BusinessMessages.ORDERED_ADDITIONAL_PRODUCT_FOR_RENT_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<Double> calculateOrderedAdditionalPrice(String rentId) {

		checkIfRentExistsIsSuccess(rentId);

		double orderedAdditionalProductsPrice = 0;

		List<OrderedAdditionalProductListDto> result = this.getByRentId(rentId).getData();
		List<OrderedAdditionalProduct> orderedAdditionalProductList = result.stream()
				.map(orderedAdditionalProduct -> this.modelMapperService.forDto().map(orderedAdditionalProduct,
						OrderedAdditionalProduct.class))
				.collect(Collectors.toList());

		for (OrderedAdditionalProduct orderedAdditionalProduct : orderedAdditionalProductList) {
			orderedAdditionalProductsPrice += (orderedAdditionalProduct.getOrderedAdditionalProductAmount()
					* orderedAdditionalProduct.getAdditionalProduct().getAdditionalProductUnitPrice());
		}

		return new SuccessDataResult<Double>(orderedAdditionalProductsPrice,
				BusinessMessages.ORDERED_ADDITIONAL_PRODUCT_PRICE_CALCULATED_SUCCESSFULLY);
	}

	@Override
	public Result checkIfRentExists(String rentId) {

		if (this.orderedAdditionalProductDao.existsByRent_RentId(rentId)) {
			return new SuccessResult(BusinessMessages.RENT_ALREADY_EXISTS_IN_ORDERED_ADDITIONAL_PRODUCT);
		}

		return new ErrorResult(BusinessMessages.RENT_NOT_EXISTS_IN_ORDERED_ADDITIONAL_PRODUCT);
	}

	private void checkIfRentExistsIsSuccess(String rentId) {
		if (!checkIfRentExists(rentId).isSuccess()) {
			throw new EntityNotFoundException(BusinessMessages.RENT_NOT_EXISTS_IN_ORDERED_ADDITIONAL_PRODUCT);
		}
	}

	private void checkIfOrderedAdditionalProductExists(int orderedAdditionalProductId) {
		if (!this.orderedAdditionalProductDao.existsById(orderedAdditionalProductId)) {
			throw new EntityNotFoundException(BusinessMessages.ORDERED_ADDITIONAL_PRODUCT_NOT_FOUND);
		}
	}

}
