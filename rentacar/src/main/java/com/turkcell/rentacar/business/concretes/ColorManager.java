package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.ColorService;
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
import com.turkcell.rentacar.exceptions.BusinessException;

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
	public DataResult<List<ColorListDto>> getAll() {

		List<Color> result = this.colorDao.findAll();
		List<ColorListDto> response = result.stream()
				.map(color -> this.modelMapperService.forDto().map(color, ColorListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ColorListDto>>(response, "Colors listed successfully.");
	}

	@Override
	public Result add(CreateColorRequest createColorRequest) throws BusinessException {

		checkIfColorNameExists(createColorRequest.getColorName());

		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);

		this.colorDao.save(color);

		return new SuccessResult("Color added successfully.");
	}

	@Override
	public DataResult<ColorListDto> getById(int id) {

		Color result = this.colorDao.getById(id);
		ColorListDto response = this.modelMapperService.forDto().map(result, ColorListDto.class);

		return new SuccessDataResult<ColorListDto>(response, "Color listed successfully.");
	}

	@Override
	public Result update(UpdateColorRequest updateColorRequest) throws BusinessException {

		checkIfColorExists(updateColorRequest.getColorId());
		checkIfColorNameExists(updateColorRequest.getColorName());

		Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);

		this.colorDao.save(color);

		return new SuccessResult("Color updated successfully.");
	}

	@Override
	public Result delete(DeleteColorRequest deleteColorRequest) throws BusinessException {

		checkIfColorExists(deleteColorRequest.getColorId());

		Color color = this.modelMapperService.forRequest().map(deleteColorRequest, Color.class);

		this.colorDao.deleteById(color.getColorId());

		return new SuccessResult("Color deleted successfully.");
	}

	private void checkIfColorNameExists(String colorName) throws BusinessException {
		if (this.colorDao.existsColorByColorName(colorName)) {
			throw new BusinessException("Color already exists!");
		}
	}

	private void checkIfColorExists(int colorId) throws BusinessException {
		if (!this.colorDao.existsById(colorId)) {
			throw new BusinessException("Color not found!");
		}
	}

}
