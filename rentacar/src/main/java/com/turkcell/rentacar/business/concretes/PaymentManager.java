package com.turkcell.rentacar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.PaymentService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.requests.createRequests.CreatePaymentRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeletePaymentRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdatePaymentRequest;
import com.turkcell.rentacar.core.services.abstracts.BaseBankPaymentServiceAdapter;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentacar.entities.concretes.Payment;
import com.turkcell.rentacar.exceptions.businessExceptions.PaymentFailedException;

@Service
public class PaymentManager implements PaymentService {

	private PaymentDao paymentDao;
	private ModelMapperService modelMapperService;
	private BaseBankPaymentServiceAdapter baseBankPaymentServiceAdapter;

	@Autowired
	public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService,
			@Qualifier("halkbank") BaseBankPaymentServiceAdapter baseBankPaymentServiceAdapter) {
		this.paymentDao = paymentDao;
		this.modelMapperService = modelMapperService;
		this.baseBankPaymentServiceAdapter = baseBankPaymentServiceAdapter;
	}

	@Override
	public Result add(CreatePaymentRequest createPaymentRequest) {

		Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);

		checkIfPaymentIsSuccess(payment);

		this.paymentDao.save(payment);

		return new SuccessResult(BusinessMessages.PAYMENT_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdatePaymentRequest updatePaymentRequest) {

		Payment payment = this.modelMapperService.forRequest().map(updatePaymentRequest, Payment.class);

		this.paymentDao.save(payment);

		return new SuccessResult(BusinessMessages.PAYMENT_UPDATED_SUCCESSFULLY);
	}

	@Override
	public Result delete(DeletePaymentRequest deletePaymentRequest) {

		Payment payment = this.modelMapperService.forRequest().map(deletePaymentRequest, Payment.class);

		this.paymentDao.delete(payment);

		return new SuccessResult(BusinessMessages.PAYMENT_DELETED_SUCCESSFULLY);
	}

	private void checkIfPaymentIsSuccess(Payment payment) {
		if (!this.baseBankPaymentServiceAdapter.payment(payment)) {
			throw new PaymentFailedException(BusinessMessages.PAYMENT_FAILED);
		}
	}

}
