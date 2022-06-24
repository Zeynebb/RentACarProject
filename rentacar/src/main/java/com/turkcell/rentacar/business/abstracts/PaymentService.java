package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.requests.createRequests.CreateMakePaymentRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeletePaymentRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdatePaymentRequest;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface PaymentService {

	Result add(CreateMakePaymentRequest createMakePaymentRequest);

	Result update(UpdatePaymentRequest updatePaymentRequest);

	Result delete(DeletePaymentRequest deletePaymentRequest);
}
