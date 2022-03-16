package com.turkcell.rentacar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentacar.business.requests.createRequests.CreateCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCorporateCustomerRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CorporateCustomerDao;
import com.turkcell.rentacar.entities.concretes.CorporateCustomer;
import com.turkcell.rentacar.exceptions.BusinessException;

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
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException {

		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest,
				CorporateCustomer.class);

		this.corporateCustomerDao.save(corporateCustomer);

		return new SuccessResult("Corporate customer added successfully.");
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {

		checkIfCorporateCustomerExists(updateCorporateCustomerRequest.getUserId());

		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest,
				CorporateCustomer.class);

		this.corporateCustomerDao.save(corporateCustomer);

		return new SuccessResult("Corporate customer updated successfully.");
	}

	@Override
	public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException {

		checkIfCorporateCustomerExists(deleteCorporateCustomerRequest.getUserId());

		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(deleteCorporateCustomerRequest,
				CorporateCustomer.class);

		this.corporateCustomerDao.delete(corporateCustomer);

		return new SuccessResult("Corporate customer deleted successfully.");
	}

	private void checkIfCorporateCustomerExists(int corporateCustomerId) throws BusinessException {
		if (!this.corporateCustomerDao.existsById(corporateCustomerId)) {
			throw new BusinessException("Corporate Customer not found!");
		}
	}

}
