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
		return new SuccessResult("Ek urun bilgisi eklendi.");
	}

	@Override
	public Result update(UpdateOrderedAdditionalProductRequest updateOrderedAdditionalProductRequest)
			throws BusinessException {
		checkIfOrderedAdditionalProductExists(updateOrderedAdditionalProductRequest.getOrderedAdditionalProductId());
		OrderedAdditionalProduct orderedAdditionalProduct = this.modelMapperService.forRequest()
				.map(updateOrderedAdditionalProductRequest, OrderedAdditionalProduct.class);
		this.orderedAdditionalProductDao.save(orderedAdditionalProduct);
		return new SuccessResult("Ek urun bilgisi guncellendi.");
	}

	@Override
	public Result delete(DeleteOrderedAdditionalProductRequest deleteOrderedAdditionalProductRequest)
			throws BusinessException {
		checkIfOrderedAdditionalProductExists(deleteOrderedAdditionalProductRequest.getOrderedAdditionalProductId());
		OrderedAdditionalProduct orderedAdditionalProduct = this.modelMapperService.forRequest()
				.map(deleteOrderedAdditionalProductRequest, OrderedAdditionalProduct.class);
		this.orderedAdditionalProductDao.delete(orderedAdditionalProduct);
		return new SuccessResult("Ek urun bilgisi silindi.");
	}

	@Override
	public DataResult<List<OrderedAdditionalProductListDto>> getAll() {
		List<OrderedAdditionalProduct> result = this.orderedAdditionalProductDao.findAll();
		List<OrderedAdditionalProductListDto> response = result.stream()
				.map(orderedAdditionalProduct -> this.modelMapperService.forDto().map(orderedAdditionalProduct,
						OrderedAdditionalProductListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<OrderedAdditionalProductListDto>>(response, "Ek urun bilgileri listelendi.");
	}

	private void checkIfOrderedAdditionalProductExists(int orderedAdditionalProductId) throws BusinessException {
		if (!this.orderedAdditionalProductDao.existsById(orderedAdditionalProductId)) {
			throw new BusinessException("Ek urun bilgisi bulunamadi!");
		}
	}

}
