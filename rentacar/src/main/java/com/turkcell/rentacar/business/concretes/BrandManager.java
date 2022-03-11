package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.business.dtos.listDtos.BrandListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteBrandRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateBrandRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
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
		checkIfBrandNameExists(createBrandRequest.getBrandName());
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		this.brandDao.save(brand);
		return new SuccessResult("Marka eklendi.");
	}

	@Override
	public DataResult<BrandListDto> getById(int id) {
		Brand result = this.brandDao.getBrandByBrandId(id);
		BrandListDto response = this.modelMapperService.forDto().map(result, BrandListDto.class);
		return new SuccessDataResult<BrandListDto>(response, "Marka goruntulendi.");
	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException {
		checkIfBrandExists(updateBrandRequest.getBrandId());
		checkIfBrandNameExists(updateBrandRequest.getBrandName());
		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		this.brandDao.save(brand);
		return new SuccessResult("Marka eklendi.");
	}
	
	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) throws BusinessException{
		checkIfBrandExists(deleteBrandRequest.getBrandId());
		Brand brand = this.modelMapperService.forRequest().map(deleteBrandRequest, Brand.class);
		this.brandDao.deleteById(brand.getBrandId());
		return new SuccessResult("Marka silindi.");
	}
	
	private void checkIfBrandExists(int brandId)throws BusinessException {
		if(!this.brandDao.existsById(brandId)) {
			throw new BusinessException("Marka bulunamadi!");
		}
	}

	private void checkIfBrandNameExists(String brandName) throws BusinessException {
		if (this.brandDao.existsBrandByBrandName(brandName)) {
			throw new BusinessException("Bu marka zaten kayitli!");
		}
	}
}
