package com.turkcell.rentacar.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentacar.business.dtos.listDtos.InvioceListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateInvoiceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteInvoiceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateInvoiceRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.OrderedAdditionalProduct;

public interface InvoiceService {

	Result add(CreateInvoiceRequest createInvoiceRequest);

	Result update(UpdateInvoiceRequest updateInvoiceRequest);

	Result delete(DeleteInvoiceRequest deleteInvoiceRequest);

	DataResult<InvioceListDto> getByRentId(String rentId);

	DataResult<List<OrderedAdditionalProduct>> getOrderedAdditionalProductByRentId(String rentId);

	DataResult<List<InvioceListDto>> getAll();

	DataResult<List<InvioceListDto>> getByUserId(int userId);

	DataResult<List<InvioceListDto>> getAllByBetweenStartDateAndEndDate(LocalDate startDate, LocalDate endDate);

}
