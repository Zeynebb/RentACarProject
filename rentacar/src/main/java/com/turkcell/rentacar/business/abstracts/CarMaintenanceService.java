package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.getDtos.CarMaintenanceGetDto;
import com.turkcell.rentacar.business.dtos.listDtos.CarMaintenanceListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarMaintenanceRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.exceptions.BusinessException;

public interface CarMaintenanceService {
	
	DataResult<List<CarMaintenanceListDto>> getAll();
	
	DataResult<List<CarMaintenanceListDto>> getByCarId(int carId);
	
	DataResult<CarMaintenanceGetDto> getCarMaintenanceDetailsByCarMaintenanceId(int carMaintenanceId);

	Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException;

	Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException;

	Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException;
	
	Result checkIfCarIsAlreadyInMaintenance(int carId) throws BusinessException;

}
