package com.turkcell.rentacar.nationalIdentityService;

import org.springframework.stereotype.Service;

@Service
public class FakeNationalIdentityManager implements NationalIdentityService {

	@Override
	public boolean checkNationalIdentity(String nationalIdentity) {
		return true;
	}

}
