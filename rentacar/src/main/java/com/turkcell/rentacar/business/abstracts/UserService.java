package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.requests.createRequests.CreateUserRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteUserRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateUserRequest;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.exceptions.BusinessException;

public interface UserService {

	Result add(CreateUserRequest createUserRequest) throws BusinessException;

	Result update(UpdateUserRequest updateUserRequest) throws BusinessException;

	Result delete(DeleteUserRequest deleteUserRequest) throws BusinessException;

}
