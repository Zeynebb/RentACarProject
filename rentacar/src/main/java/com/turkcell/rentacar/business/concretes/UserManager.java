package com.turkcell.rentacar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.UserService;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateUserRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.UserDao;
import com.turkcell.rentacar.entities.abstracts.User;
import com.turkcell.rentacar.exceptions.businessExceptions.EntityAlreadyExistsException;
import com.turkcell.rentacar.exceptions.businessExceptions.EntityNotFoundException;

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
	public Result update(UpdateUserRequest updateUserRequest) {

		checkIfUserExists(updateUserRequest.getUserId());

		existsByEmail(updateUserRequest.getEmail());

		User user = this.modelMapperService.forRequest().map(updateUserRequest, User.class);

		this.userDao.save(user);

		return new SuccessResult("User updated successfully.");
	}

	private void checkIfUserExists(int userId) {
		if (!this.userDao.existsById(userId)) {
			throw new EntityNotFoundException("User not found!");
		}
	}

	private void existsByEmail(String email) {
		if (this.userDao.existsByEmail(email)) {
			throw new EntityAlreadyExistsException("Email is already exists!");
		}
	}

}
