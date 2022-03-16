package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.OrderedAdditionalProductService;
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
import com.turkcell.rentacar.exceptions.BusinessException;

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
	public Result add(CreateOrderedAdditionalProductRequest createOrderedAdditionalProductRequest)
			throws BusinessException {

		OrderedAdditionalProduct orderedAdditionalProduct = this.modelMapperService.forRequest()
				.map(createOrderedAdditionalProductRequest, OrderedAdditionalProduct.class);

		orderedAdditionalProduct.setOrderedAdditionalProductId(0);
		this.orderedAdditionalProductDao.save(orderedAdditionalProduct);

		return new SuccessResult("OrderedAdditionalProduct added successfully.");
	}

	@Override
	public Result update(UpdateOrderedAdditionalProductRequest updateOrderedAdditionalProductRequest)
			throws BusinessException {

		checkIfOrderedAdditionalProductExists(updateOrderedAdditionalProductRequest.getOrderedAdditionalProductId());

		OrderedAdditionalProduct orderedAdditionalProduct = this.modelMapperService.forRequest()
				.map(updateOrderedAdditionalProductRequest, OrderedAdditionalProduct.class);

		this.orderedAdditionalProductDao.save(orderedAdditionalProduct);

		return new SuccessResult("OrderedAdditionalProduct updated successfully.");
	}

	@Override
	public Result delete(DeleteOrderedAdditionalProductRequest deleteOrderedAdditionalProductRequest)
			throws BusinessException {

		checkIfOrderedAdditionalProductExists(deleteOrderedAdditionalProductRequest.getOrderedAdditionalProductId());

		OrderedAdditionalProduct orderedAdditionalProduct = this.modelMapperService.forRequest()
				.map(deleteOrderedAdditionalProductRequest, OrderedAdditionalProduct.class);

		this.orderedAdditionalProductDao.delete(orderedAdditionalProduct);

		return new SuccessResult("OrderedAdditionalProduct deleted successfully.");
	}

	@Override
	public DataResult<List<OrderedAdditionalProductListDto>> getAll() {

		List<OrderedAdditionalProduct> result = this.orderedAdditionalProductDao.findAll();
		List<OrderedAdditionalProductListDto> response = result.stream()
				.map(orderedAdditionalProduct -> this.modelMapperService.forDto().map(orderedAdditionalProduct,
						OrderedAdditionalProductListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<OrderedAdditionalProductListDto>>(response, "OrderedAdditionalProducts listed successfully.");
	}

	@Override
	public DataResult<List<OrderedAdditionalProductListDto>> getByRentId(int rentId) {

		List<OrderedAdditionalProduct> result = this.orderedAdditionalProductDao.getByRent_RentId(rentId);
		List<OrderedAdditionalProductListDto> response = result.stream()
				.map(orderedAdditionalProduct -> this.modelMapperService.forDto().map(orderedAdditionalProduct,
						OrderedAdditionalProductListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<OrderedAdditionalProductListDto>>(response,
				"OrderedAdditionalProducts for Rent listed successfully.");
	}

	@Override
	public DataResult<Double> calculateOrderedAdditionalPrice(int rentId) throws BusinessException {
		
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
				"OrderedAdditionalProducts price calculated successfully.");
	}

	@Override
	public Result checkIfRentExists(int rentId) {

		if (this.orderedAdditionalProductDao.existsByRent_RentId(rentId)) {
			return new SuccessResult("Rent is exists in OrderedAdditionalProduct.");
		}

		return new ErrorResult("Rent is not exists in OrderedAdditionalProduct.");
	}
	
	private void checkIfRentExistsIsSuccess(int rentId) throws BusinessException {
		if(!checkIfRentExists(rentId).isSuccess()) {
			throw new BusinessException("Rent is not exists in OrderedAdditionalProduct.");
		}
	}

	private void checkIfOrderedAdditionalProductExists(int orderedAdditionalProductId) throws BusinessException {
		if (!this.orderedAdditionalProductDao.existsById(orderedAdditionalProductId)) {
			throw new BusinessException("OrderedAdditionalProduct is not found!");
		}
	}

}
