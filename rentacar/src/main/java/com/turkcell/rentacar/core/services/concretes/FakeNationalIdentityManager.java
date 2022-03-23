package com.turkcell.rentacar.core.services.concretes;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.turkcell.rentacar.core.services.abstracts.BaseNationalIdentityService;

@Component
@Qualifier("fakeNationalIdentityManager")
public class FakeNationalIdentityManager implements BaseNationalIdentityService {

	@Override
	public boolean checkNationalIdentity(String nationalIdentity) {
		return true;
	}

}
