package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.listDtos.OrderedAdditionalProductListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateOrderedAdditionalProductRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteOrderedAdditionalProductRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateOrderedAdditionalProductRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.exceptions.BusinessException;

public interface OrderedAdditionalProductService {

	Result add(CreateOrderedAdditionalProductRequest createOrderedAdditionalProductRequest) throws BusinessException;

	Result update(UpdateOrderedAdditionalProductRequest updateOrderedAdditionalProductRequest) throws BusinessException;

	Result delete(DeleteOrderedAdditionalProductRequest deleteOrderedAdditionalProductRequest) throws BusinessException;

	DataResult<List<OrderedAdditionalProductListDto>> getAll();

}
