package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.listDtos.CarDamageListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarDamageRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarDamageRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarDamageRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface CarDamageService {

	Result add(CreateCarDamageRequest createCarDamageRequest);

	Result update(UpdateCarDamageRequest updateCarDamageRequest);

	Result delete(DeleteCarDamageRequest deleteCarDamageRequest);

	DataResult<List<CarDamageListDto>> getAll();

}
