package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.listDtos.BrandListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteBrandRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateBrandRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.exceptions.BusinessException;

import java.util.List;

public interface BrandService {
	DataResult<List<BrandListDto>> getAll();

	DataResult<BrandListDto> getById(int id);

	Result add(CreateBrandRequest createBrandRequest) throws BusinessException;

	Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException;

	Result delete(DeleteBrandRequest deleteBrandRequest);

}
