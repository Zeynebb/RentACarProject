package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.requests.updateRequests.UpdateUserRequest;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface UserService {

	Result update(UpdateUserRequest updateUserRequest);

}
