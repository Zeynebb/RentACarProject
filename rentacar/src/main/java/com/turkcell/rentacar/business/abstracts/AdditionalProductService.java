package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.listDtos.AdditionalProductListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateAdditionalProductRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteAdditionalProductRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateAdditionalProductRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.exceptions.BusinessException;

public interface AdditionalProductService {

	Result add(CreateAdditionalProductRequest createAdditionalProductRequest) throws BusinessException;

	Result update(UpdateAdditionalProductRequest updateAdditionalProductRequest) throws BusinessException;

	Result delete(DeleteAdditionalProductRequest deleteAdditionalProductRequest) throws BusinessException;

	DataResult<List<AdditionalProductListDto>> getAll();

}
