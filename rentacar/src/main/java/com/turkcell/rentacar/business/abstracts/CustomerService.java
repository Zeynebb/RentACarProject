package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.listDtos.CustomerListDto;
import com.turkcell.rentacar.core.utilities.results.DataResult;

public interface CustomerService {

	public DataResult<List<CustomerListDto>> getAll();

}
