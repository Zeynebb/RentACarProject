package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.listDtos.CarListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateBrandToCarRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarKilometerInfoRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateColorToCarRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CarDao;
import com.turkcell.rentacar.entities.concretes.Car;
import com.turkcell.rentacar.exceptions.businessExceptions.EntityNotFoundException;

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

		return new SuccessDataResult<List<CarListDto>>(response, BusinessMessages.CARS_LISTED_SUCCESSFULLY);
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {

		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);

		this.carDao.save(car);

		return new SuccessResult(BusinessMessages.CAR_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) {

		checkIfCarExists(updateCarRequest.getCarId());

		Car car = this.carDao.getById(updateCarRequest.getCarId());
		Car updatedCar = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);

		updatedCar.setBrand(car.getBrand());
		updatedCar.setColor(car.getColor());

		this.carDao.save(updatedCar);

		return new SuccessResult(BusinessMessages.CAR_UPDATED_SUCCESSFULLY);
	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) {

		checkIfCarExists(deleteCarRequest.getCarId());

		Car car = this.modelMapperService.forRequest().map(deleteCarRequest, Car.class);

		this.carDao.deleteById(car.getCarId());

		return new SuccessResult(BusinessMessages.CAR_DELETED_SUCCESSFULLY);
	}

	@Override
	public Result updateColor(UpdateColorToCarRequest updateColorToCarRequest) {

		checkIfCarExists(updateColorToCarRequest.getCarId());

		this.carDao.updateColorToCarByCarId(updateColorToCarRequest.getCarId(), updateColorToCarRequest.getColorId());

		return new SuccessResult(BusinessMessages.COLOR_CAR_UPDATED_SUCCESSFULLY);
	}

	@Override
	public Result updateBrand(UpdateBrandToCarRequest updateBrandToCarRequest) {

		checkIfCarExists(updateBrandToCarRequest.getCarId());

		this.carDao.updateBrandToCarByCarId(updateBrandToCarRequest.getCarId(), updateBrandToCarRequest.getBrandId());

		return new SuccessResult(BusinessMessages.BRAND_CAR_UPDATED_SUCCESSFULLY);
	}

	@Override
	public DataResult<CarListDto> getById(int id) {

		Car result = this.carDao.getById(id);
		CarListDto response = this.modelMapperService.forDto().map(result, CarListDto.class);

		return new SuccessDataResult<CarListDto>(response, BusinessMessages.CAR_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CarListDto>> findByDailyPriceLessThanEqual(double dailyPrice) {

		List<Car> result = this.carDao.findByDailyPriceLessThanEqual(dailyPrice);
		List<CarListDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<CarListDto>>(response,
				BusinessMessages.CAR_LISTED_SUCCESSFULLY_BY_DAILY_PRICE);
	}

	@Override
	public DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		List<Car> result = this.carDao.findAll(pageable).getContent();
		List<CarListDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<CarListDto>>(response,
				BusinessMessages.CAR_LISTED_AND_PAGINATED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CarListDto>> getAllSorted(boolean sort) {

		Sort sorted = Sort.by(checkSortDirectionType(sort), "dailyPrice");

		List<Car> result = this.carDao.findAll(sorted);
		List<CarListDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<CarListDto>>(response, BusinessMessages.CAR_SORTED_SUCCESSFULLY);
	}

	@Override
	public Result updateKilometerInfo(UpdateCarKilometerInfoRequest updateCarKilometerInfoRequest) {

		this.carDao.updateKilometerToCarByCarId(updateCarKilometerInfoRequest.getCarId(),
				updateCarKilometerInfoRequest.getKilometerInfo());

		return new SuccessResult(BusinessMessages.KILOMETER_INFO_CAR_UPDATED_SUCCESSFULLY);
	}

	private void checkIfCarExists(int carId) {
		if (!this.carDao.existsByCarId(carId)) {
			throw new EntityNotFoundException(BusinessMessages.CAR_NOT_FOUND);
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
