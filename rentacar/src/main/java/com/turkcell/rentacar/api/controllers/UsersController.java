package com.turkcell.rentacar.api.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.UserService;
import com.turkcell.rentacar.business.requests.createRequests.CreateUserRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteUserRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateUserRequest;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.exceptions.BusinessException;

@RestController
@RequestMapping("/api/users")
public class UsersController {

	private UserService userService;

	@Autowired
	public UsersController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateUserRequest createUserRequest) throws BusinessException {
		return this.userService.add(createUserRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateUserRequest updateUserRequest) throws BusinessException {
		return this.userService.update(updateUserRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteUserRequest deleteUserRequest) throws BusinessException {
		return this.userService.delete(deleteUserRequest);
	}

}
