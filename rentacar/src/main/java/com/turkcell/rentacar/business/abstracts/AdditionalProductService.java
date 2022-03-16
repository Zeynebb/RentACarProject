package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.listDtos.AdditionalProductListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateAdditionalProductRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteAdditionalProductRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateAdditionalProductRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface AdditionalProductService {

	Result add(CreateAdditionalProductRequest createAdditionalProductRequest);

	Result update(UpdateAdditionalProductRequest updateAdditionalProductRequest);

	Result delete(DeleteAdditionalProductRequest deleteAdditionalProductRequest);

	DataResult<List<AdditionalProductListDto>> getAll();

}
