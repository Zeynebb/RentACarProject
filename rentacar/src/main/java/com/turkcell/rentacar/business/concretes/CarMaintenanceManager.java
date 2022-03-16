package com.turkcell.rentacar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.dtos.getDtos.CarMaintenanceGetDto;
import com.turkcell.rentacar.business.dtos.listDtos.CarMaintenanceListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarMaintenanceRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.ErrorResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentacar.entities.concretes.CarMaintenance;
import com.turkcell.rentacar.exceptions.businessExceptions.CarIsAlreadyInMaintenanceException;
import com.turkcell.rentacar.exceptions.businessExceptions.CarIsAlreadyInRentException;
import com.turkcell.rentacar.exceptions.businessExceptions.EntityNotFoundException;
import com.turkcell.rentacar.exceptions.businessExceptions.ReturnDateIsAfterNowException;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

	private CarMaintenanceDao carMaintenanceDao;
	private ModelMapperService modelMapperService;
	private RentService rentService;

	@Autowired
	public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, ModelMapperService modelMapperService,
			@Lazy RentService rentService) {
		this.carMaintenanceDao = carMaintenanceDao;
		this.modelMapperService = modelMapperService;
		this.rentService = rentService;
	}

	@Override
	public DataResult<List<CarMaintenanceListDto>> getAll() {

		List<CarMaintenance> result = this.carMaintenanceDao.findAll();
		List<CarMaintenanceListDto> response = result.stream().map(
				carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CarMaintenanceListDto>>(response, "CarMaintenances listed successfully.");
	}

	@Override
	public DataResult<CarMaintenanceGetDto> getCarMaintenanceDetailsByCarMaintenanceId(int carMaintenanceId) {

		CarMaintenance result = this.carMaintenanceDao.getById(carMaintenanceId);
		CarMaintenanceGetDto response = this.modelMapperService.forDto().map(result, CarMaintenanceGetDto.class);

		return new SuccessDataResult<CarMaintenanceGetDto>(response, "CarMaintenance listed successfully.");
	}

	@Override
	public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) {

		checkIfReturnDateIsAfterNow(createCarMaintenanceRequest.getReturnDate());
		checkIfCarIsAlreadyInMaintenanceIsSuccess(createCarMaintenanceRequest.getCarId());
		checkIfCarIsAlreadyInRent(createCarMaintenanceRequest.getCarId());

		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest,
				CarMaintenance.class);

		this.carMaintenanceDao.save(carMaintenance);

		return new SuccessResult("CarMaintenance added successfully.");
	}

	@Override
	public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) {

		existByCarMaintenance(updateCarMaintenanceRequest.getCarMaintenanceId());
		checkIfReturnDateIsAfterNow(updateCarMaintenanceRequest.getReturnDate());

		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest,
				CarMaintenance.class);

		this.carMaintenanceDao.save(carMaintenance);

		return new SuccessResult("CarMaintenance updated successfully.");
	}

	@Override
	public Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) {

		existByCarMaintenance(deleteCarMaintenanceRequest.getCarMaintenanceId());

		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(deleteCarMaintenanceRequest,
				CarMaintenance.class);

		this.carMaintenanceDao.delete(carMaintenance);

		return new SuccessResult("CarMaintenance deleted successfully.");
	}

	@Override
	public DataResult<List<CarMaintenanceListDto>> getByCarId(int carId) {

		List<CarMaintenance> result = this.carMaintenanceDao.getByCar_CarId(carId);
		List<CarMaintenanceListDto> response = result.stream().map(
				carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CarMaintenanceListDto>>(response,
				"CarMaintenances for Car listed successfully.");
	}

	@Override
	public Result checkIfCarIsAlreadyInMaintenance(int carId) {
		for (CarMaintenance carMaintenance : this.carMaintenanceDao.getByCar_CarId(carId)) {
			if (carMaintenance.getReturnDate() != null) {
				return new SuccessResult("Car not in maintenance");
			}
		}
		return new ErrorResult("Car in maintenance!");
	}

	private void checkIfCarIsAlreadyInMaintenanceIsSuccess(int carId) {
		if (this.carMaintenanceDao.existsByCar_CarId(carId)) {
			if (!checkIfCarIsAlreadyInMaintenance(carId).isSuccess()) {
				throw new CarIsAlreadyInMaintenanceException("Car in maintenance!");
			}
		}
	}

	private void checkIfCarIsAlreadyInRent(int carId) {
		if (!checkIfCarExistInRentTable(carId)) {
			if (!this.rentService.checkIfCarAlreadyInRent(carId).isSuccess()) {
				throw new CarIsAlreadyInRentException("The Car cannot be sent for maintenance because it is on Rent.");
			}
		}
	}

	private boolean checkIfCarExistInRentTable(int carId) {
		return this.rentService.getByCarId(carId).getData().isEmpty();
	}

	private void existByCarMaintenance(int carMaintenanceId) {
		if (!this.carMaintenanceDao.existsById(carMaintenanceId)) {
			throw new EntityNotFoundException("CarMaintenance not found!");
		}
	}

	private void checkIfReturnDateIsAfterNow(LocalDate returnDate) {
		if (returnDate != null) {
			if (returnDate.isAfter(LocalDate.now())) {
				throw new ReturnDateIsAfterNowException("A future date cannot be entered!");
			}
		}
	}

}
