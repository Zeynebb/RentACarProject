package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.getDtos.CarMaintenanceGetDto;
import com.turkcell.rentacar.business.dtos.listDtos.CarMaintenanceListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarMaintenanceRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface CarMaintenanceService {

	DataResult<List<CarMaintenanceListDto>> getAll();

	DataResult<List<CarMaintenanceListDto>> getByCarId(int carId);

	DataResult<CarMaintenanceGetDto> getCarMaintenanceDetailsByCarMaintenanceId(int carMaintenanceId);

	Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest);

	Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest);

	Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest);

	Result checkIfCarIsAlreadyInMaintenance(int carId);

}
