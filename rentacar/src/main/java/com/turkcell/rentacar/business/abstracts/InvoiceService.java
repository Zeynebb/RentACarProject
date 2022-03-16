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
import com.turkcell.rentacar.exceptions.BusinessException;

public interface InvoiceService {

	Result add(CreateInvoiceRequest createInvoiceRequest)throws BusinessException;

	Result Update(UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException;

	Result Delete(DeleteInvoiceRequest deleteInvoiceRequest) throws BusinessException;

	DataResult<InvioceListDto> getByRentId(int rentId)throws BusinessException;

	DataResult<List<OrderedAdditionalProduct>> getOrderedAdditionalProductByRentId(int rentId);

	DataResult<List<InvioceListDto>> getAll();

	DataResult<List<InvioceListDto>> getByUserId(int userId)throws BusinessException;

	DataResult<List<InvioceListDto>> getAllByBetweenStartDateAndEndDate(LocalDate startDate, LocalDate endDate);

}
