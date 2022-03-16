package com.turkcell.rentacar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CustomerService;
import com.turkcell.rentacar.business.requests.createRequests.CreateCustomerRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCustomerRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCustomerRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CustomerDao;
import com.turkcell.rentacar.entities.concretes.Customer;
import com.turkcell.rentacar.exceptions.BusinessException;

@Service
public class CustomerManager implements CustomerService {

	private CustomerDao customerDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CustomerManager(CustomerDao customerDao, ModelMapperService modelMapperService) {
		this.customerDao = customerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateCustomerRequest createCustomerRequest) throws BusinessException {

		Customer customer = this.modelMapperService.forRequest().map(createCustomerRequest, Customer.class);

		this.customerDao.save(customer);

		return new SuccessResult("Customer added successfully.");
	}

	@Override
	public Result update(UpdateCustomerRequest updateCustomerRequest) throws BusinessException {

		checkIfCustomerExists(updateCustomerRequest.getUserId());

		Customer customer = this.modelMapperService.forRequest().map(updateCustomerRequest, Customer.class);

		this.customerDao.save(customer);

		return new SuccessResult("Customer updated successfully.");
	}

	@Override
	public Result delete(DeleteCustomerRequest deleteCustomerRequest) throws BusinessException {

		checkIfCustomerExists(deleteCustomerRequest.getUserId());

		Customer customer = this.modelMapperService.forRequest().map(deleteCustomerRequest, Customer.class);

		this.customerDao.delete(customer);

		return new SuccessResult("Customer deleted successfully.");
	}

	private void checkIfCustomerExists(int customerId) throws BusinessException {
		if (!this.customerDao.existsById(customerId)) {
			throw new BusinessException("Customer not found!");
		}
	}

}
