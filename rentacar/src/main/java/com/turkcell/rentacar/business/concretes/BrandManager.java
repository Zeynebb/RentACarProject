package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.business.dtos.BrandListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteBrandRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateBrandRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.ErrorResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.BrandDao;
import com.turkcell.rentacar.entities.concretes.Brand;
import com.turkcell.rentacar.exceptions.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandManager implements BrandService {

	private BrandDao brandDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public BrandManager(BrandDao brandDao, ModelMapperService modelMapperService) {
		this.brandDao = brandDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<BrandListDto>> getAll() {
		List<Brand> result = this.brandDao.findAll();
		List<BrandListDto> response = result.stream()
				.map(brand -> this.modelMapperService.forDto().map(brand, BrandListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<BrandListDto>>(response, "Markalar listelendi.");
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) throws BusinessException {
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		if (checkIfBrandNameExists(brand.getBrandName())) {
			this.brandDao.save(brand);
			return new SuccessResult("Marka eklendi.");
		}
		return new ErrorResult("Marka eklenemedi!");
	}

	@Override
	public DataResult<BrandListDto> getById(int id) {
		Brand result = this.brandDao.getBrandByBrandId(id);
		BrandListDto response = this.modelMapperService.forDto().map(result, BrandListDto.class);
		return new SuccessDataResult<BrandListDto>(response, "Marka goruntulendi.");
	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException{
		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		if (checkIfBrandNameExists(brand.getBrandName())) {
			this.brandDao.save(brand);
			return new SuccessResult("Marka eklendi.");
		}
		return new ErrorResult("Marka eklenemedi!");
	}

	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) {
		Brand brand = this.modelMapperService.forRequest().map(deleteBrandRequest, Brand.class);
		this.brandDao.deleteById(brand.getBrandId());
		return new SuccessResult("Marka silindi.");
	}

	public boolean checkIfBrandNameExists(String brandName) throws BusinessException {
		if (!this.brandDao.existsBrandByBrandName(brandName)) {
			return true;
		} else {
			throw new BusinessException("Bu marka zaten kayitli!");
		}

	}
}
