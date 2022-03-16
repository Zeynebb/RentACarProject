package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.requests.createRequests.CreateCustomerRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCustomerRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCustomerRequest;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.exceptions.BusinessException;

public interface CustomerService {

	Result add(CreateCustomerRequest createCustomerRequest) throws BusinessException;

	Result update(UpdateCustomerRequest updateCustomerRequest) throws BusinessException;

	Result delete(DeleteCustomerRequest deleteCustomerRequest) throws BusinessException;

}
