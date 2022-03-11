package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.listDtos.CarListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateBrandToCarRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateColorToCarRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.exceptions.BusinessException;

public interface CarService {

	DataResult<List<CarListDto>> getAll();

	DataResult<CarListDto> getById(int id);

	DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize);

	DataResult<List<CarListDto>> getAllSorted(boolean sort);

	Result add(CreateCarRequest createCarRequest) throws BusinessException;

	Result update(UpdateCarRequest updateCarRequest) throws BusinessException;

	Result delete(DeleteCarRequest deleteCarRequest) throws BusinessException;

	Result updateColor(UpdateColorToCarRequest updateColorToCarRequest) throws BusinessException;

	Result updateBrand(UpdateBrandToCarRequest updateBrandToCarRequest) throws BusinessException;

	DataResult<List<CarListDto>> findByDailyPriceLessThanEqual(double dailyPrice);

}
