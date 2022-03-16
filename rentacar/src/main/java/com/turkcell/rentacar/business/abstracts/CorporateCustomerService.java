package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.requests.createRequests.CreateCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCorporateCustomerRequest;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.exceptions.BusinessException;

public interface CorporateCustomerService {

	Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException;

	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException;

	Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException;
}
