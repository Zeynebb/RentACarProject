package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CreditCardService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.getDtos.CreditCardGetDto;
import com.turkcell.rentacar.business.dtos.listDtos.CreditCardListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCreditCardRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCreditCardRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCreditCardRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CreditCardDao;
import com.turkcell.rentacar.entities.concretes.CreditCard;

@Service
public class CreditCardManager implements CreditCardService {

	private CreditCardDao creditCardDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CreditCardManager(CreditCardDao creditCardDao, ModelMapperService modelMapperService) {
		super();
		this.creditCardDao = creditCardDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateCreditCardRequest createCreditCardRequest) {

		CreditCard creditCard = this.modelMapperService.forRequest().map(createCreditCardRequest, CreditCard.class);

		this.creditCardDao.save(creditCard);

		return new SuccessResult(BusinessMessages.CREDIT_CARD_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateCreditCardRequest updateCreditCardRequest) {

		CreditCard creditCard = this.modelMapperService.forRequest().map(updateCreditCardRequest, CreditCard.class);

		this.creditCardDao.save(creditCard);

		return new SuccessResult(BusinessMessages.CREDIT_CARD_UPDATED_SUCCESSFULLY);
	}

	@Override
	public Result delete(DeleteCreditCardRequest deleteCreditCardRequest) {

		CreditCard creditCard = this.modelMapperService.forRequest().map(deleteCreditCardRequest, CreditCard.class);

		this.creditCardDao.delete(creditCard);

		return new SuccessResult(BusinessMessages.CREDIT_CARD_DELETED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CreditCardListDto>> getAll() {

		List<CreditCard> result = this.creditCardDao.findAll();
		List<CreditCardListDto> response = result.stream()
				.map(creditCard -> this.modelMapperService.forDto().map(creditCard, CreditCardListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CreditCardListDto>>(response,
				BusinessMessages.CREDIT_CARDS_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<CreditCardGetDto> getById(int creditCardId) {

		CreditCard result = this.creditCardDao.getById(creditCardId);
		CreditCardGetDto response = this.modelMapperService.forDto().map(result, CreditCardGetDto.class);

		return new SuccessDataResult<CreditCardGetDto>(response, BusinessMessages.CREDIT_CARD_LISTED_SUCCESSFULLY);
	}

}
