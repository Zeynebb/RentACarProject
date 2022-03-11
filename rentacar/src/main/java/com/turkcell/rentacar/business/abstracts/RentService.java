package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.getDtos.RentGetDto;
import com.turkcell.rentacar.business.dtos.listDtos.RentListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteRentRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateRentRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.exceptions.BusinessException;

public interface RentService {

	Result add(CreateRentRequest createRentRequest) throws BusinessException;

	Result update(UpdateRentRequest updateRentRequest) throws BusinessException;

	Result delete(DeleteRentRequest deleteRentRequest) throws BusinessException;

	DataResult<List<RentListDto>> getAll();
	
	DataResult<List<RentListDto>> getByCarId(int carId);

	DataResult<RentGetDto> getRentDetailsByRentId(int rentId);
	
	Result checkIfCarAlreadyInRent(int carId) throws BusinessException;

}
