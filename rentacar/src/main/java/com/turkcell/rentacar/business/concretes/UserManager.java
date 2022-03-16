package com.turkcell.rentacar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.UserService;
import com.turkcell.rentacar.business.requests.createRequests.CreateUserRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteUserRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateUserRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.UserDao;
import com.turkcell.rentacar.entities.abstracts.User;
import com.turkcell.rentacar.exceptions.BusinessException;

@Service
public class UserManager implements UserService {

	private UserDao userDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public UserManager(UserDao userDao, ModelMapperService modelMapperService) {
		this.userDao = userDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateUserRequest createUserRequest) throws BusinessException {

		existsByEmail(createUserRequest.getEmail());

		User user = this.modelMapperService.forRequest().map(createUserRequest, User.class);

		this.userDao.save(user);

		return new SuccessResult("User added successfully.");
	}

	@Override
	public Result update(UpdateUserRequest updateUserRequest) throws BusinessException {

		checkIfUserExists(updateUserRequest.getUserId());

		User user = this.modelMapperService.forRequest().map(updateUserRequest, User.class);

		this.userDao.save(user);

		return new SuccessResult("User updated successfully.");
	}

	@Override
	public Result delete(DeleteUserRequest deleteUserRequest) throws BusinessException {

		checkIfUserExists(deleteUserRequest.getUserId());

		User user = this.modelMapperService.forRequest().map(deleteUserRequest, User.class);

		this.userDao.delete(user);

		return new SuccessResult("User deleted successfully.");
	}

	private void checkIfUserExists(int userId) throws BusinessException {
		if (!this.userDao.existsById(userId)) {
			throw new BusinessException("User not found!");
		}
	}

	private void existsByEmail(String email) throws BusinessException {
		if (this.userDao.existsByEmail(email)) {
			throw new BusinessException("Email is already exists!");
		}
	}

}
