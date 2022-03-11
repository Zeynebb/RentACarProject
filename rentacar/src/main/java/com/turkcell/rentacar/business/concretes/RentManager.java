package com.turkcell.rentacar.business.concretes;

import java.time.LocalDate;
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
import com.turkcell.rentacar.business.requests.updateRequests.UpdateRentRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.ErrorResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.RentDao;
import com.turkcell.rentacar.entities.concretes.Rent;
import com.turkcell.rentacar.exceptions.BusinessException;

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
	public Result add(CreateRentRequest createRentRequest) throws BusinessException {
		checkIfCarAlreadyInMaintenance(createRentRequest.getCarId());
		checkIfCarAlreadyInRentIsSuccess(createRentRequest.getCarId());
		Rent rent = this.modelMapperService.forRequest().map(createRentRequest, Rent.class);
		checkIfReturnCityIsDifferentForRentalCity(rent);
		checkIfRentReturnDateIsAfterNow(rent);
		this.rentDao.save(rent);
		return new SuccessResult("Kira bilgisi eklendi.");
	}

	@Override
	public Result update(UpdateRentRequest updateRentRequest) throws BusinessException {
		Rent rent = this.modelMapperService.forRequest().map(updateRentRequest, Rent.class);
		checkIfRentReturnDateIsAfterNow(rent);
		checkIfReturnCityIsDifferentForRentalCity(rent);
		this.rentDao.save(rent);
		return new SuccessResult("Kira bilgisi guncellendi.");
	}

	@Override
	public Result delete(DeleteRentRequest deleteRentRequest) throws BusinessException {
		Rent rent = this.modelMapperService.forRequest().map(deleteRentRequest, Rent.class);
		this.rentDao.delete(rent);
		return new SuccessResult("Kira bilgisi silindi.");
	}

	@Override
	public DataResult<List<RentListDto>> getAll() {
		List<Rent> result = this.rentDao.findAll();
		List<RentListDto> response = result.stream()
				.map(rent -> modelMapperService.forDto().map(rent, RentListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<RentListDto>>(response, "Kira bilgileri listelendi.");
	}

	@Override
	public DataResult<RentGetDto> getRentDetailsByRentId(int rentId) {
		Rent result = this.rentDao.getById(rentId);
		RentGetDto response = this.modelMapperService.forDto().map(result, RentGetDto.class);
		return new SuccessDataResult<RentGetDto>(response, "Kira bilgileri listelendi.");
	}

	@Override
	public DataResult<List<RentListDto>> getByCarId(int carId) {
		List<Rent> result = this.rentDao.getByCar_CarId(carId);
		List<RentListDto> response = result.stream()
				.map(rent -> modelMapperService.forDto().map(rent, RentListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<RentListDto>>(response, "Araca ait kira bilgileri listelendi.");
	}

	@Override
	public Result checkIfCarAlreadyInRent(int carId) throws BusinessException {
		for (Rent rent : this.rentDao.getByCar_CarId(carId)) {
			if (!rent.isRentStatus()) {
				return new SuccessResult("Arac kirada degil!");
			}
		}
		return new ErrorResult("Arac kirada!");
	}

	private void checkIfCarAlreadyInRentIsSuccess(int carId) throws BusinessException {
		if (this.rentDao.existsByCar_CarId(carId)) {
			if (!checkIfCarAlreadyInRent(carId).isSuccess()) {
				throw new BusinessException("Arac kirada!");
			}
		}
	}

	private void checkIfCarAlreadyInMaintenance(int carId) throws BusinessException {
		if (!checkIfCarExistInCarMaintenanceTable(carId)) {
			if (!this.carMaintenanceService.checkIfCarIsAlreadyInMaintenance(carId).isSuccess()) {
				throw new BusinessException("Araba bakimda oldugundan kiralanamaz.");
			}
		}
	}

	private boolean checkIfCarExistInCarMaintenanceTable(int id) {
		return this.carMaintenanceService.getByCarId(id).getData().isEmpty();
	}

	private void checkIfReturnCityIsDifferentForRentalCity(Rent rent) {
		double additionalPrice = 750;
		if (!rent.getRentalCity().equals(rent.getReturnCity())) {
			rent.setAdditionalServicePrice(additionalPrice);
		} else {
			rent.setAdditionalServicePrice(0);
		}
	}

	private void checkIfRentReturnDateIsAfterNow(Rent rent) {
		if (rent.getRentReturnDate() != null) {
			if (!rent.getRentReturnDate().isAfter(LocalDate.now())) {
				rent.setRentStatus(false);
			}
		}
	}

}
