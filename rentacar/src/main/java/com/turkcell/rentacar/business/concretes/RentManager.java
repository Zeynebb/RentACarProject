package com.turkcell.rentacar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.getDtos.RentGetDto;
import com.turkcell.rentacar.business.dtos.listDtos.RentListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteRentRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateDeliveryDateToRentRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateEndedKilometerInfoRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateRentRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.ErrorDataResult;
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

		rent.setRentId(makeRentId());

		checkIfRentReturnDateIsAfterNow(rent);

		this.rentDao.save(rent);

		return new SuccessResult(BusinessMessages.RENT_ADDED_SUCCESSFULLY);
	}

	@Override
	public DataResult<String> addIndividualCustomer(CreateRentRequest createRentRequest) {

		checkIfCarAlreadyInMaintenance(createRentRequest.getCarId());
		checkIfCarAlreadyInRentIsSuccess(createRentRequest.getCarId());

		Rent rent = this.modelMapperService.forRequest().map(createRentRequest, Rent.class);

		String rentId = makeRentId();
		rent.setRentId(rentId);

		checkIfRentReturnDateIsAfterNow(rent);

		this.rentDao.save(rent);

		return new SuccessDataResult<String>(rentId, BusinessMessages.RENT_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateRentRequest updateRentRequest) {

		Rent rent = this.modelMapperService.forRequest().map(updateRentRequest, Rent.class);

		checkIfRentReturnDateIsAfterNow(rent);

		this.rentDao.save(rent);

		return new SuccessResult(BusinessMessages.RENT_UPDATED_SUCCESSFULLY);
	}

	@Override
	public Result delete(DeleteRentRequest deleteRentRequest) {

		Rent rent = this.modelMapperService.forRequest().map(deleteRentRequest, Rent.class);

		this.rentDao.delete(rent);

		return new SuccessResult(BusinessMessages.RENT_DELETED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<RentListDto>> getAll() {

		List<Rent> result = this.rentDao.findAll();
		List<RentListDto> response = result.stream()
				.map(rent -> modelMapperService.forDto().map(rent, RentListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<RentListDto>>(response, BusinessMessages.RENTS_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<RentGetDto> getRentDetailsByRentId(String rentId) {

		Rent result = this.rentDao.getById(rentId);
		RentGetDto response = this.modelMapperService.forDto().map(result, RentGetDto.class);

		return new SuccessDataResult<RentGetDto>(response, BusinessMessages.RENT_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<RentGetDto>> getByCarId(int carId) {

		List<Rent> result = this.rentDao.getByCar_CarId(carId);
		List<RentGetDto> response = result.stream().map(rent -> modelMapperService.forDto().map(rent, RentGetDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<RentGetDto>>(response, BusinessMessages.RENTS_FOR_CAR_LISTED_SUCCESSFULLY);
	}

	@Override
	public Result checkIfCarAlreadyInRent(int carId) {
		for (Rent rent : this.rentDao.getByCar_CarId(carId)) {
			if (!rent.isRentStatus()) {
				return new SuccessResult(BusinessMessages.CAR_NOT_IN_RENT);
			}
		}
		return new ErrorResult(BusinessMessages.CAR_ALREADY_IN_RENT);
	}

	@Override
	public DataResult<List<OrderedAdditionalProduct>> getOrderedAdditionalProductsByRentId(String rentId) {

		List<OrderedAdditionalProduct> result = this.rentDao.getOrderedAdditionalProductsByRentId(rentId);

		return new SuccessDataResult<List<OrderedAdditionalProduct>>(result,
				BusinessMessages.ORDERED_ADDITIONAL_PRODUCT_FOR_RENT_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<Double> calculateRentTotalPrice(String rentId) {

		Rent rent = this.rentDao.getById(rentId);

		long usageTime = ChronoUnit.DAYS.between(rent.getRentDate(), rent.getRentReturnDate());

		double rentTotalPrice = (rent.getCar().getDailyPrice() * usageTime);

		return new SuccessDataResult<>(rentTotalPrice, BusinessMessages.RENT_TOTAL_PRICE_CALCULATED_SUCCESSFULLY);
	}

	@Override
	public Result checkIfReturnCityIsDifferentForRentalCityIsSuccess(String rentId) {

		Rent rent = this.rentDao.getById(rentId);

		if (!rent.getRentalCity().equals(rent.getReturnCity())) {
			return new SuccessResult(BusinessMessages.CITIES_ARE_DIFFERENT);
		}

		return new ErrorResult(BusinessMessages.CITIES_ARE_SAME);
	}

	@Override
	public Result updateEndedKilometer(UpdateEndedKilometerInfoRequest updateEndedKilometerInfoRequest) {

		checkIfRentExists(updateEndedKilometerInfoRequest.getRentId());

		this.rentDao.updateEndedKilometerInfoToRentByRentId(updateEndedKilometerInfoRequest.getRentId(),
				updateEndedKilometerInfoRequest.getEndedKilometerInfo());

		return new SuccessResult(BusinessMessages.ENDED_KILOMETER_INFO_UPDATED_SUCCESSFULLY);
	}

	@Override
	public Result updateDeliveryDate(UpdateDeliveryDateToRentRequest updateDeliveryDateToRentRequest) {

		checkIfRentExists(updateDeliveryDateToRentRequest.getRentId());

		this.rentDao.updateDeliveryDateToRentByRentId(updateDeliveryDateToRentRequest.getRentId(),
				updateDeliveryDateToRentRequest.getDeliveryDate());

		return new SuccessResult(BusinessMessages.DELIVERY_DATE_UPDATED_SUCCESSFULLY);
	}

	@Override
	public DataResult<Double> calculateDelayedDayPriceForRent(String rentId) {

		Rent rent = this.rentDao.getById(rentId);

		if (rent.getDeliveryDate() != null) {
			if (!checkIfDeliveryDateAndRentReturnDateIsDifferent(rent.getDeliveryDate(), rent.getRentReturnDate())) {

				long usageTime = ChronoUnit.DAYS.between(rent.getDeliveryDate(), rent.getRentReturnDate()) * (-1);

				return new SuccessDataResult<Double>((rent.getCar().getDailyPrice() * usageTime));
			}
		}
		return new ErrorDataResult<Double>(BusinessMessages.RENT_DELAYED_DAY_PRICE_CALCULATED_FAILED);
	}

	private boolean checkIfDeliveryDateAndRentReturnDateIsDifferent(LocalDate deliveryDate, LocalDate rentReturnDate) {

		if (deliveryDate != null) {
			if (deliveryDate.equals(rentReturnDate)) {
				return true;
			}
		}
		return false;
	}

	private void checkIfCarAlreadyInRentIsSuccess(int carId) {

		if (this.rentDao.existsByCar_CarId(carId)) {
			if (!checkIfCarAlreadyInRent(carId).isSuccess()) {
				throw new CarIsAlreadyInRentException(BusinessMessages.CAR_ALREADY_IN_RENT);
			}
		}
	}

	private void checkIfCarAlreadyInMaintenance(int carId) {

		if (!checkIfCarExistInCarMaintenanceTable(carId)) {
			if (!this.carMaintenanceService.checkIfCarIsAlreadyInMaintenance(carId).isSuccess()) {
				throw new CarIsAlreadyInMaintenanceException(BusinessMessages.CAR_ALREADY_IN_MAINTENANCE);
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

	private void checkIfRentExists(String rentId) {

		if (!this.rentDao.existsById(rentId)) {
			throw new EntityNotFoundException(BusinessMessages.RENT_NOT_FOUND);
		}
	}

	private String makeRentId() {
		return UUID.randomUUID().toString();
	}

}
