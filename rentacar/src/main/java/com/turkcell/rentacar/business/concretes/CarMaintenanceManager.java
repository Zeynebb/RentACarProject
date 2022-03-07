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
import com.turkcell.rentacar.exceptions.BusinessException;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

	private CarMaintenanceDao carMaintenanceDao;
	private ModelMapperService modelMapperService;
	private RentService rentService;

	@Lazy
	@Autowired
	public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, ModelMapperService modelMapperService,
			RentService rentService) {
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
		return new SuccessDataResult<List<CarMaintenanceListDto>>(response, "Bakim bilgileri listelendi.");
	}

	@Override
	public DataResult<CarMaintenanceGetDto> getCarMaintenanceDetailsByCarMaintenanceId(int carMaintenanceId) {
		CarMaintenance result = this.carMaintenanceDao.getById(carMaintenanceId);
		CarMaintenanceGetDto response = this.modelMapperService.forDto().map(result, CarMaintenanceGetDto.class);
		return new SuccessDataResult<CarMaintenanceGetDto>(response, "Detayli bakim bilgileri listelendi.");
	}

	@Override
	public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
		checkIfReturnDateIsAfterNow(createCarMaintenanceRequest.getReturnDate());
		checkIfCarIsAlreadyInMaintenanceIsSuccess(createCarMaintenanceRequest.getCarId());
		checkIfCarIsAlreadyInRent(createCarMaintenanceRequest.getCarId());
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest,
				CarMaintenance.class);
		this.carMaintenanceDao.save(carMaintenance);
		return new SuccessResult("Bakim bilgisi eklendi.");
	}

	@Override
	public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {
		existByCarMaintenance(updateCarMaintenanceRequest.getCarMaintenanceId());
		checkIfReturnDateIsAfterNow(updateCarMaintenanceRequest.getReturnDate());
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest,
				CarMaintenance.class);
		this.carMaintenanceDao.save(carMaintenance);
		return new SuccessResult("Bakim bilgisi guncellendi.");
	}

	@Override
	public Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException {
		existByCarMaintenance(deleteCarMaintenanceRequest.getCarMaintenanceId());
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(deleteCarMaintenanceRequest,
				CarMaintenance.class);
		this.carMaintenanceDao.delete(carMaintenance);
		return new SuccessResult("Bakim bilgisi silindi.");
	}

	@Override
	public DataResult<List<CarMaintenanceListDto>> getByCarId(int carId) {
		List<CarMaintenance> result = this.carMaintenanceDao.getByCar_CarId(carId);
		List<CarMaintenanceListDto> response = result.stream().map(
				carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CarMaintenanceListDto>>(response, "Araca ait bakim bilgileri listelendi.");
	}

	@Override
	public Result checkIfCarIsAlreadyInMaintenance(int carId) throws BusinessException {
		for (CarMaintenance carMaintenance : this.carMaintenanceDao.getByCar_CarId(carId)) {
			if (carMaintenance.getReturnDate() != null) {
				return new SuccessResult("Arac bakimda degil.");
			}
		}
		return new ErrorResult("Arac bakimda!");
	}

	private void checkIfCarIsAlreadyInMaintenanceIsSuccess(int carId) throws BusinessException {
		var result = this.carMaintenanceDao.getByCar_CarId(carId);
		if (!result.isEmpty()) {
			if (!checkIfCarIsAlreadyInMaintenance(carId).isSuccess()) {
				throw new BusinessException("Arac bakimda!");
			}
		}
	}

	private void checkIfCarIsAlreadyInRent(int carId) throws BusinessException {
		this.rentService.checkIfCarAlreadyInRent(carId);
	}

	private void existByCarMaintenance(int carMaintenanceId) throws BusinessException {
		if (!this.carMaintenanceDao.existsById(carMaintenanceId)) {
			throw new BusinessException("Bakim bilgisi bulunamadi.");
		}
	}

	private void checkIfReturnDateIsAfterNow(LocalDate returnDate) throws BusinessException {
		if (returnDate != null) {
			if (returnDate.isAfter(LocalDate.now())) {
				throw new BusinessException("Ileri bir tarih girilemez!");
			}
		}
	}

}
