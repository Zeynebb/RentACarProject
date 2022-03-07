package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.listDtos.ColorListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateColorRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteColorRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateColorRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.exceptions.BusinessException;

import java.util.List;

public interface ColorService {
	DataResult<List<ColorListDto>> getAll();

	DataResult<ColorListDto> getById(int id);

	Result add(CreateColorRequest createColorRequest) throws BusinessException;

	Result update(UpdateColorRequest updateColorRequest) throws BusinessException;

	Result delete(DeleteColorRequest deleteColorRequest);
}
