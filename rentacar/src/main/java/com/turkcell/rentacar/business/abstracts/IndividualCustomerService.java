package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.requests.createRequests.CreateIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateIndividualCustomerRequest;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.exceptions.BusinessException;

public interface IndividualCustomerService {

	Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException;

	Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException;

	Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException;

}
