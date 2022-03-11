package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.AdditionalProductService;
import com.turkcell.rentacar.business.dtos.listDtos.AdditionalProductListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateAdditionalProductRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteAdditionalProductRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateAdditionalProductRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.AdditionalProductDao;
import com.turkcell.rentacar.entities.concretes.AdditionalProduct;
import com.turkcell.rentacar.exceptions.BusinessException;

@Service
public class AdditionalProductManager implements AdditionalProductService {

	private AdditionalProductDao additionalProductDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public AdditionalProductManager(AdditionalProductDao additionalProductDao, ModelMapperService modelMapperService) {
		this.additionalProductDao = additionalProductDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateAdditionalProductRequest createAdditionalProductRequest) throws BusinessException {
		checkIfAdditionalProducNameExists(createAdditionalProductRequest.getAdditionalProductName());
		AdditionalProduct additionalProduct = this.modelMapperService.forRequest().map(createAdditionalProductRequest,
				AdditionalProduct.class);
		this.additionalProductDao.save(additionalProduct);
		return new SuccessResult("Ek urun eklendi.");
	}

	@Override
	public Result update(UpdateAdditionalProductRequest updateAdditionalProductRequest) throws BusinessException {
		checkIfAdditionalProductExists(updateAdditionalProductRequest.getAdditionalProductId());
		AdditionalProduct additionalProduct = this.modelMapperService.forRequest().map(updateAdditionalProductRequest,
				AdditionalProduct.class);
		this.additionalProductDao.save(additionalProduct);
		return new SuccessResult("Ek urun guncellendi.");
	}

	@Override
	public Result delete(DeleteAdditionalProductRequest deleteAdditionalProductRequest) throws BusinessException {
		checkIfAdditionalProductExists(deleteAdditionalProductRequest.getAdditionalProductId());
		AdditionalProduct additionalProduct = this.modelMapperService.forRequest().map(deleteAdditionalProductRequest,
				AdditionalProduct.class);
		this.additionalProductDao.delete(additionalProduct);
		return new SuccessResult("Ek urun silindi.");
	}

	@Override
	public DataResult<List<AdditionalProductListDto>> getAll() {
		List<AdditionalProduct> result = this.additionalProductDao.findAll();
		List<AdditionalProductListDto> response = result.stream().map(additionalProduct -> this.modelMapperService
				.forDto().map(additionalProduct, AdditionalProductListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<AdditionalProductListDto>>(response, "Ek urunler listelendi.");
	}

	private void checkIfAdditionalProductExists(int additionalProductId) throws BusinessException {
		if (!this.additionalProductDao.existsByAdditionalProductId(additionalProductId)) {
			throw new BusinessException("Ek urun bulunamadi!");
		}
	}

	private void checkIfAdditionalProducNameExists(String additionalProductName) throws BusinessException {
		if (this.additionalProductDao.existsByAdditionalProductName(additionalProductName)) {
			throw new BusinessException("Bu ek urun zaten kayitli!");
		}
	}

}
