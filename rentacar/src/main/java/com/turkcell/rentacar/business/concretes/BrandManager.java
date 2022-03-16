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
import com.turkcell.rentacar.exceptions.businessExceptions.EntityAlreadyExistsException;
import com.turkcell.rentacar.exceptions.businessExceptions.EntityNotFoundException;

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

		return new SuccessDataResult<List<BrandListDto>>(response, "Brands listed successfully.");
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) {

		checkIfBrandNameExists(createBrandRequest.getBrandName());

		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);

		this.brandDao.save(brand);

		return new SuccessResult("Brand added successfully.");
	}

	@Override
	public DataResult<BrandListDto> getById(int id) {

		Brand result = this.brandDao.getBrandByBrandId(id);
		BrandListDto response = this.modelMapperService.forDto().map(result, BrandListDto.class);

		return new SuccessDataResult<BrandListDto>(response, "Brand listed successfully.");
	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) {

		checkIfBrandExists(updateBrandRequest.getBrandId());
		checkIfBrandNameExists(updateBrandRequest.getBrandName());

		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);

		this.brandDao.save(brand);

		return new SuccessResult("Brand updated successfully.");
	}

	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) {

		checkIfBrandExists(deleteBrandRequest.getBrandId());

		Brand brand = this.modelMapperService.forRequest().map(deleteBrandRequest, Brand.class);

		this.brandDao.deleteById(brand.getBrandId());

		return new SuccessResult("Brand deleted successfully.");
	}

	private void checkIfBrandExists(int brandId) {
		if (!this.brandDao.existsById(brandId)) {
			throw new EntityNotFoundException("Brand not found!");
		}
	}

	private void checkIfBrandNameExists(String brandName) {
		if (this.brandDao.existsBrandByBrandName(brandName)) {
			throw new EntityAlreadyExistsException("Brand already exists.");
		}
	}
}
