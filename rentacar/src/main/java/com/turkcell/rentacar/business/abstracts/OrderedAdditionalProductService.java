package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.listDtos.OrderedAdditionalProductListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateOrderedAdditionalProductRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteOrderedAdditionalProductRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateOrderedAdditionalProductRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface OrderedAdditionalProductService {

	Result add(CreateOrderedAdditionalProductRequest createOrderedAdditionalProductRequest);

	Result update(UpdateOrderedAdditionalProductRequest updateOrderedAdditionalProductRequest);

	Result delete(DeleteOrderedAdditionalProductRequest deleteOrderedAdditionalProductRequest);

	Result checkIfRentExists(String rentId);

	DataResult<List<OrderedAdditionalProductListDto>> getAll();

	DataResult<List<OrderedAdditionalProductListDto>> getByRentId(String rentId);

	DataResult<Double> calculateOrderedAdditionalPrice(String rentId);

}
