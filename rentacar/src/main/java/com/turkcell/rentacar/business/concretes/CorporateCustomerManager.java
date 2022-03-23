package com.turkcell.rentacar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.requests.createRequests.CreateCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCorporateCustomerRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CorporateCustomerDao;
import com.turkcell.rentacar.entities.concretes.CorporateCustomer;
import com.turkcell.rentacar.exceptions.businessExceptions.EntityAlreadyExistsException;
import com.turkcell.rentacar.exceptions.businessExceptions.EntityNotFoundException;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

	private CorporateCustomerDao corporateCustomerDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService) {
		this.corporateCustomerDao = corporateCustomerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) {

		checkIfEmailExists(createCorporateCustomerRequest.getEmail());

		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest,
				CorporateCustomer.class);

		this.corporateCustomerDao.save(corporateCustomer);

		return new SuccessResult(BusinessMessages.CORPORATE_CUSTOMER_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {

		checkIfEmailExists(updateCorporateCustomerRequest.getEmail());
		checkIfCorporateCustomerExists(updateCorporateCustomerRequest.getUserId());

		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest,
				CorporateCustomer.class);

		this.corporateCustomerDao.save(corporateCustomer);

		return new SuccessResult(BusinessMessages.CORPORATE_CUSTOMER_UPDATED_SUCCESSFULLY);
	}

	@Override
	public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) {

		checkIfCorporateCustomerExists(deleteCorporateCustomerRequest.getUserId());

		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(deleteCorporateCustomerRequest,
				CorporateCustomer.class);

		this.corporateCustomerDao.delete(corporateCustomer);

		return new SuccessResult(BusinessMessages.CORPORATE_CUSTOMER_DELETED_SUCCESSFULLY);
	}

	private void checkIfCorporateCustomerExists(int corporateCustomerId) {
		if (!this.corporateCustomerDao.existsById(corporateCustomerId)) {
			throw new EntityNotFoundException(BusinessMessages.CORPORATE_CUSTOMER_NOT_FOUND);
		}
	}

	private void checkIfEmailExists(String email) {
		if (this.corporateCustomerDao.existsByEmail(email)) {
			throw new EntityAlreadyExistsException(BusinessMessages.EMAIL_ALREADY_EXISTS);
		}
	}

}
