package com.turkcell.rentacar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentacar.business.requests.createRequests.CreateIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateIndividualCustomerRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.IndividualCustomerDao;
import com.turkcell.rentacar.entities.concretes.IndividualCustomer;
import com.turkcell.rentacar.exceptions.BusinessException;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {

	private IndividualCustomerDao individualCustomerDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao,
			ModelMapperService modelMapperService) {
		this.individualCustomerDao = individualCustomerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException {

		IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
				.map(createIndividualCustomerRequest, IndividualCustomer.class);

		this.individualCustomerDao.save(individualCustomer);

		return new SuccessResult("Individual customer added successfully.");
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException {

		checkIfIndividualCustomerExists(updateIndividualCustomerRequest.getUserId());

		IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
				.map(updateIndividualCustomerRequest, IndividualCustomer.class);

		this.individualCustomerDao.save(individualCustomer);

		return new SuccessResult("Individual customer updated successfully.");
	}

	@Override
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException {

		checkIfIndividualCustomerExists(deleteIndividualCustomerRequest.getUserId());

		IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
				.map(deleteIndividualCustomerRequest, IndividualCustomer.class);

		this.individualCustomerDao.delete(individualCustomer);

		return new SuccessResult("Individual customer deleted successfully.");
	}

	private void checkIfIndividualCustomerExists(int individualCustomerId) throws BusinessException {
		if (!this.individualCustomerDao.existsById(individualCustomerId)) {
			throw new BusinessException("Individual Customer not found!");
		}
	}

}
