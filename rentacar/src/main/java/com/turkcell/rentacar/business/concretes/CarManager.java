package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.dtos.listDtos.CarListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateBrandToCarRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateColorToCarRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CarDao;
import com.turkcell.rentacar.entities.concretes.Car;
import com.turkcell.rentacar.exceptions.BusinessException;

@Service
public class CarManager implements CarService {

	private CarDao carDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CarManager(CarDao carDao, ModelMapperService modelMapperService) {
		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<CarListDto>> getAll() {
		
		List<Car> result = this.carDao.findAll();
		List<CarListDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<CarListDto>>(response, "Cars listed successfully.");
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) throws BusinessException {
		
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		
		this.carDao.save(car);
		
		return new SuccessResult("Car added successfully.");
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) throws BusinessException {
		
		checkIfCarExists(updateCarRequest.getCarId());
		
		Car car = this.carDao.getById(updateCarRequest.getCarId());
		Car updatedCar = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		
		updatedCar.setBrand(car.getBrand());
		updatedCar.setColor(car.getColor());
		
		this.carDao.save(updatedCar);
		
		return new SuccessResult("Car updated successfully.");
	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) throws BusinessException {
		
		checkIfCarExists(deleteCarRequest.getCarId());
		
		Car car = this.modelMapperService.forRequest().map(deleteCarRequest, Car.class);
		
		this.carDao.deleteById(car.getCarId());
		
		return new SuccessResult("Car deleted successfully.");
	}

	@Override
	public Result updateColor(UpdateColorToCarRequest updateColorToCarRequest) throws BusinessException {
		
		checkIfCarExists(updateColorToCarRequest.getCarId());
		
		this.carDao.updateColorToCarByCarId(updateColorToCarRequest.getCarId(), updateColorToCarRequest.getColorId());
		
		return new SuccessResult("The Color of the Car updated successfully.");
	}

	@Override
	public Result updateBrand(UpdateBrandToCarRequest updateBrandToCarRequest) throws BusinessException {
		
		checkIfCarExists(updateBrandToCarRequest.getCarId());
		
		this.carDao.updateBrandToCarByCarId(updateBrandToCarRequest.getCarId(), updateBrandToCarRequest.getBrandId());
		
		return new SuccessResult("The Brand of the Car updated successfully.");
	}

	@Override
	public DataResult<CarListDto> getById(int id) {
		
		Car result = this.carDao.getById(id);
		CarListDto response = this.modelMapperService.forDto().map(result, CarListDto.class);
		
		return new SuccessDataResult<CarListDto>(response, "Car listed successfully");
	}

	@Override
	public DataResult<List<CarListDto>> findByDailyPriceLessThanEqual(double dailyPrice) {
		
		List<Car> result = this.carDao.findByDailyPriceLessThanEqual(dailyPrice);
		List<CarListDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<CarListDto>>(response, "Cars successfully listed by dailyPrice.");
	}

	@Override
	public DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize) {
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		
		List<Car> result = this.carDao.findAll(pageable).getContent();
		List<CarListDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<CarListDto>>(response, "Cars are paginated and listed successfully.");
	}

	@Override
	public DataResult<List<CarListDto>> getAllSorted(boolean sort) {
		
		Sort sorted = Sort.by(checkSortDirectionType(sort), "dailyPrice");
		
		List<Car> result = this.carDao.findAll(sorted);
		List<CarListDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<CarListDto>>(response, "Cars are sorted successfully.");
	}

	private void checkIfCarExists(int carId) throws BusinessException {
		if (!this.carDao.existsByCarId(carId)) {
			throw new BusinessException("Car not found!");
		}
	}

	public Sort.Direction checkSortDirectionType(boolean sort) {
		if (sort) {
			return Sort.Direction.ASC;
		} else {
			return Sort.Direction.DESC;
		}
	}

}
