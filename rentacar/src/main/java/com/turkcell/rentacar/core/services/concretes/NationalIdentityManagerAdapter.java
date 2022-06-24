package com.turkcell.rentacar.core.services.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.turkcell.rentacar.core.services.abstracts.BaseNationalIdentityService;
import com.turkcell.rentacar.outServices.nationalIdentityService.NationalIdentityService;

@Component
@Qualifier("nationalIdentityManager")
public class NationalIdentityManagerAdapter implements BaseNationalIdentityService {

	private NationalIdentityService nationalIdentityService;

	@Autowired
	public NationalIdentityManagerAdapter(NationalIdentityService nationalIdentityService) {
		this.nationalIdentityService = nationalIdentityService;
	}

	@Override
	public boolean checkNationalIdentity(String nationalIdentity) {
		return this.nationalIdentityService.checkNationalIdentity(nationalIdentity);
	}

}
