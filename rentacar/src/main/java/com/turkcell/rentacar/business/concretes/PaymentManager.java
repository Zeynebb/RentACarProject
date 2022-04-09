package com.turkcell.rentacar.business.concretes;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.abstracts.OrderedAdditionalProductService;
import com.turkcell.rentacar.business.abstracts.PaymentService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.requests.createRequests.CreateInvoiceRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreateMakePaymentRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreateOrderedAdditionalProductRequest;
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

	private RentService rentService;
	private InvoiceService invoiceService;
	private OrderedAdditionalProductService orderedAdditionalProductService;

	@Autowired
	public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService,
			@Qualifier("halkbank") BaseBankPaymentServiceAdapter baseBankPaymentServiceAdapter, RentService rentService,
			InvoiceService invoiceService, OrderedAdditionalProductService orderedAdditionalProductService) {
		this.paymentDao = paymentDao;
		this.modelMapperService = modelMapperService;
		this.baseBankPaymentServiceAdapter = baseBankPaymentServiceAdapter;
		this.rentService = rentService;
		this.invoiceService = invoiceService;
		this.orderedAdditionalProductService = orderedAdditionalProductService;

	}

	@Override
	public Result add(CreateMakePaymentRequest createMakePaymentRequest) {

		Payment payment = this.modelMapperService.forRequest().map(createMakePaymentRequest.getCreatePaymentRequest(),
				Payment.class);

		checkIfPaymentIsSuccess(payment);
		runPaymentSuccessor(createMakePaymentRequest);

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

	@Transactional
	public void runPaymentSuccessor(CreateMakePaymentRequest createMakePaymentRequest) {

		// rentAdd
		String rentId = this.rentService.addIndividualCustomer(createMakePaymentRequest.getCreateRentRequest())
				.getData();

		// invoiceAdd
		CreateInvoiceRequest createInvoiceRequest = createMakePaymentRequest.getCreateInvoiceRequest();
		createInvoiceRequest.setRentId(rentId);
		this.invoiceService.add(createInvoiceRequest);

		// orderedAdditionalProductAdd
		for (CreateOrderedAdditionalProductRequest createOrderedAdditionalProductRequest : createMakePaymentRequest
				.getCreateOrderedAdditionalProductRequests()) {
			createOrderedAdditionalProductRequest.setRentId(rentId);
			this.orderedAdditionalProductService.add(createOrderedAdditionalProductRequest);
		}

		// paymentAdd
		CreatePaymentRequest createPaymentRequest = createMakePaymentRequest.getCreatePaymentRequest();
		createPaymentRequest.setRentId(rentId);
		this.paymentDao.save(this.modelMapperService.forRequest()
				.map(this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class), Payment.class));

	}

}
