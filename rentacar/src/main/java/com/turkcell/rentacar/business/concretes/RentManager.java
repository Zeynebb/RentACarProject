package com.turkcell.rentacar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.dtos.getDtos.RentGetDto;
import com.turkcell.rentacar.business.dtos.listDtos.RentListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteRentRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateEndedKilometerInfoRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateRentRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.ErrorResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.RentDao;
import com.turkcell.rentacar.entities.concretes.OrderedAdditionalProduct;
import com.turkcell.rentacar.entities.concretes.Rent;
import com.turkcell.rentacar.exceptions.businessExceptions.CarIsAlreadyInMaintenanceException;
import com.turkcell.rentacar.exceptions.businessExceptions.CarIsAlreadyInRentException;

@Service
public class RentManager implements RentService {

	private RentDao rentDao;
	private ModelMapperService modelMapperService;
	private CarMaintenanceService carMaintenanceService;

	@Autowired
	public RentManager(RentDao rentDao, ModelMapperService modelMapperService,
			@Lazy CarMaintenanceService carMaintenanceService) {
		this.rentDao = rentDao;
		this.modelMapperService = modelMapperService;
		this.carMaintenanceService = carMaintenanceService;
	}

	@Override
	public Result addCorporateCustomer(CreateRentRequest createRentRequest) {

		checkIfCarAlreadyInMaintenance(createRentRequest.getCarId());
		checkIfCarAlreadyInRentIsSuccess(createRentRequest.getCarId());

		Rent rent = this.modelMapperService.forRequest().map(createRentRequest, Rent.class);

		checkIfRentReturnDateIsAfterNow(rent);

		this.rentDao.save(rent);

		return new SuccessResult("Rent added successfully.");
	}

	@Override
	public Result addIndividualCustomer(CreateRentRequest createRentRequest) {

		checkIfCarAlreadyInMaintenance(createRentRequest.getCarId());
		checkIfCarAlreadyInRentIsSuccess(createRentRequest.getCarId());

		Rent rent = this.modelMapperService.forRequest().map(createRentRequest, Rent.class);

		checkIfRentReturnDateIsAfterNow(rent);

		this.rentDao.save(rent);

		return new SuccessResult("Rent added successfully.");
	}

	@Override
	public Result update(UpdateRentRequest updateRentRequest) {

		Rent rent = this.modelMapperService.forRequest().map(updateRentRequest, Rent.class);

		checkIfRentReturnDateIsAfterNow(rent);

		this.rentDao.save(rent);

		return new SuccessResult("Rent updated successfully.");
	}

	@Override
	public Result delete(DeleteRentRequest deleteRentRequest) {

		Rent rent = this.modelMapperService.forRequest().map(deleteRentRequest, Rent.class);

		this.rentDao.delete(rent);

		return new SuccessResult("Rent deleted successfully.");
	}

	@Override
	public DataResult<List<RentListDto>> getAll() {

		List<Rent> result = this.rentDao.findAll();
		List<RentListDto> response = result.stream()
				.map(rent -> modelMapperService.forDto().map(rent, RentListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<RentListDto>>(response, "Rent listed successfully.");
	}

	@Override
	public DataResult<RentGetDto> getRentDetailsByRentId(int rentId) {

		Rent result = this.rentDao.getById(rentId);
		RentGetDto response = this.modelMapperService.forDto().map(result, RentGetDto.class);

		return new SuccessDataResult<RentGetDto>(response, "Rents listed successfully.");
	}

	@Override
	public DataResult<List<RentListDto>> getByCarId(int carId) {

		List<Rent> result = this.rentDao.getByCar_CarId(carId);
		List<RentListDto> response = result.stream()
				.map(rent -> modelMapperService.forDto().map(rent, RentListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<RentListDto>>(response, "Rent for Car listed successfuly.");
	}

	@Override
	public Result checkIfCarAlreadyInRent(int carId) {
		for (Rent rent : this.rentDao.getByCar_CarId(carId)) {
			if (!rent.isRentStatus()) {
				return new SuccessResult("Car not in Rent!");
			}
		}
		return new ErrorResult("Car in Rent!");
	}

	@Override
	public DataResult<List<OrderedAdditionalProduct>> getOrderedAdditionalProductsByRentId(int rentId) {

		List<OrderedAdditionalProduct> result = this.rentDao.getOrderedAdditionalProductsByRentId(rentId);

		return new SuccessDataResult<List<OrderedAdditionalProduct>>(result,
				"OrderedAdditionalProducts for Rent listed successfully.");
	}

	@Override
	public DataResult<Double> calculateRentTotalPrice(int rentId) {

		Rent rent = this.rentDao.getById(rentId);

		long usageTime = ChronoUnit.DAYS.between(rent.getRentDate(), rent.getRentReturnDate());

		double rentTotalPrice = (rent.getCar().getDailyPrice() * usageTime);

		return new SuccessDataResult<>(rentTotalPrice, "Rent total price calculated successfully.");
	}

	@Override
	public Result checkIfReturnCityIsDifferentForRentalCityIsSuccess(int rentId) {

		Rent rent = this.rentDao.getById(rentId);

		if (!rent.getRentalCity().equals(rent.getReturnCity())) {
			return new SuccessResult("Cities are different!");
		}

		return new ErrorResult("Cities are same!");
	}

	@Override
	public Result updateEndedKilometer(UpdateEndedKilometerInfoRequest updateEndedKilometerInfoRequest) {

		this.rentDao.updateEndedKilometerInfoToRentByRentId(updateEndedKilometerInfoRequest.getRentId(),
				updateEndedKilometerInfoRequest.getEndedKilometerInfo());

		return new SuccessResult("EndedKilometerInfo updated successfully.");
	}

	private void checkIfCarAlreadyInRentIsSuccess(int carId) {
		if (this.rentDao.existsByCar_CarId(carId)) {
			if (!checkIfCarAlreadyInRent(carId).isSuccess()) {
				throw new CarIsAlreadyInRentException("Car in Rent!");
			}
		}
	}

	private void checkIfCarAlreadyInMaintenance(int carId) {
		if (!checkIfCarExistInCarMaintenanceTable(carId)) {
			if (!this.carMaintenanceService.checkIfCarIsAlreadyInMaintenance(carId).isSuccess()) {
				throw new CarIsAlreadyInMaintenanceException("The Car cannot be rented as it is in maintenance.");
			}
		}
	}

	private boolean checkIfCarExistInCarMaintenanceTable(int id) {
		return this.carMaintenanceService.getByCarId(id).getData().isEmpty();
	}

	private void checkIfRentReturnDateIsAfterNow(Rent rent) {
		if (rent.getRentReturnDate() != null) {
			if (!rent.getRentReturnDate().isAfter(LocalDate.now())) {
				rent.setRentStatus(false);
			}
		}
	}

}
