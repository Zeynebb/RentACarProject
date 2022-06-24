package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.ColorService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.listDtos.ColorListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateColorRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteColorRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateColorRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.ColorDao;
import com.turkcell.rentacar.entities.concretes.Color;
import com.turkcell.rentacar.exceptions.businessExceptions.EntityAlreadyExistsException;
import com.turkcell.rentacar.exceptions.businessExceptions.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColorManager implements ColorService {

	private ColorDao colorDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService) {
		this.colorDao = colorDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateColorRequest createColorRequest) {

		checkIfColorNameExists(createColorRequest.getColorName());

		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);

		this.colorDao.save(color);

		return new SuccessResult(BusinessMessages.COLOR_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateColorRequest updateColorRequest) {

		checkIfColorExists(updateColorRequest.getColorId());
		checkIfColorNameExists(updateColorRequest.getColorName());

		Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);

		this.colorDao.save(color);

		return new SuccessResult(BusinessMessages.COLOR_UPDATED_SUCCESSFULLY);
	}

	@Override
	public Result delete(DeleteColorRequest deleteColorRequest) {

		checkIfColorExists(deleteColorRequest.getColorId());

		Color color = this.modelMapperService.forRequest().map(deleteColorRequest, Color.class);

		this.colorDao.deleteById(color.getColorId());

		return new SuccessResult(BusinessMessages.COLOR_DELETED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<ColorListDto>> getAll() {

		List<Color> result = this.colorDao.findAll();
		List<ColorListDto> response = result.stream()
				.map(color -> this.modelMapperService.forDto().map(color, ColorListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ColorListDto>>(response, BusinessMessages.COLORS_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<ColorListDto> getById(int id) {

		Color result = this.colorDao.getById(id);
		ColorListDto response = this.modelMapperService.forDto().map(result, ColorListDto.class);

		return new SuccessDataResult<ColorListDto>(response, BusinessMessages.COLOR_LISTED_SUCCESSFULLY);
	}

	private void checkIfColorNameExists(String colorName) {
		if (this.colorDao.existsColorByColorName(colorName)) {
			throw new EntityAlreadyExistsException(BusinessMessages.COLOR_ALREADY_EXISTS);
		}
	}

	private void checkIfColorExists(int colorId) {
		if (!this.colorDao.existsById(colorId)) {
			throw new EntityNotFoundException(BusinessMessages.COLOR_NOT_FOUND);
		}
	}

}
