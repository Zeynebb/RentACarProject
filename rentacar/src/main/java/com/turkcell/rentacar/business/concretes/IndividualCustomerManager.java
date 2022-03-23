package com.turkcell.rentacar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.requests.createRequests.CreateIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateIndividualCustomerRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.IndividualCustomerDao;
import com.turkcell.rentacar.entities.concretes.IndividualCustomer;
import com.turkcell.rentacar.exceptions.businessExceptions.EntityAlreadyExistsException;
import com.turkcell.rentacar.exceptions.businessExceptions.EntityNotFoundException;
import com.turkcell.rentacar.exceptions.businessExceptions.NationalIdentityNotValidException;
import com.turkcell.rentacar.nationalIdentityService.NationalIdentityService;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {

	private IndividualCustomerDao individualCustomerDao;
	private ModelMapperService modelMapperService;
	private NationalIdentityService nationalIdentityService;

	@Autowired
	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao, ModelMapperService modelMapperService,
			NationalIdentityService nationalIdentityService) {
		this.individualCustomerDao = individualCustomerDao;
		this.modelMapperService = modelMapperService;
		this.nationalIdentityService = nationalIdentityService;
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {

		checkIfEmailExists(createIndividualCustomerRequest.getEmail());
		checkIfNationalIdentityIsReal(createIndividualCustomerRequest.getNationalIdentity());

		IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
				.map(createIndividualCustomerRequest, IndividualCustomer.class);

		this.individualCustomerDao.save(individualCustomer);

		return new SuccessResult(BusinessMessages.INDIVIDUAL_CUSTOMER_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {

		checkIfEmailExists(updateIndividualCustomerRequest.getEmail());
		checkIfIndividualCustomerExists(updateIndividualCustomerRequest.getUserId());
		checkIfNationalIdentityIsReal(updateIndividualCustomerRequest.getNationalIdentity());

		IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
				.map(updateIndividualCustomerRequest, IndividualCustomer.class);

		this.individualCustomerDao.save(individualCustomer);

		return new SuccessResult(BusinessMessages.INDIVIDUAL_CUSTOMER_UPDATED_SUCCESSFULLY);
	}

	@Override
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {

		checkIfIndividualCustomerExists(deleteIndividualCustomerRequest.getUserId());

		IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
				.map(deleteIndividualCustomerRequest, IndividualCustomer.class);

		this.individualCustomerDao.delete(individualCustomer);

		return new SuccessResult(BusinessMessages.INDIVIDUAL_CUSTOMER_DELETED_SUCCESSFULLY);
	}

	private void checkIfIndividualCustomerExists(int individualCustomerId) {
		if (!this.individualCustomerDao.existsById(individualCustomerId)) {
			throw new EntityNotFoundException(BusinessMessages.INDIVIDUAL_CUSTOMER_NOT_FOUND);
		}
	}

	private void checkIfEmailExists(String email) {
		if (this.individualCustomerDao.existsByEmail(email)) {
			throw new EntityAlreadyExistsException(BusinessMessages.EMAIL_ALREADY_EXISTS);
		}
	}

	private void checkIfNationalIdentityIsReal(String nationalIdentity) {
		if (!this.nationalIdentityService.checkNationalIdentity(nationalIdentity)) {
			throw new NationalIdentityNotValidException(BusinessMessages.NATIONAL_IDENTITY_NOT_VALID);
		}
	}

}
