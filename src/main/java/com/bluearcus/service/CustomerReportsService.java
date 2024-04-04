package com.bluearcus.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bluearcus.dto.CustomerReportsDto;
import com.bluearcus.dto.CustomerReportsDtoWithCount;
import com.bluearcus.dto.PaymentStatusDto;

public interface CustomerReportsService {
	ResponseEntity saveCustomerReport(CustomerReportsDto customerReportsDto);

	ResponseEntity editCustomerReport(CustomerReportsDto customerReportsDto);
	
	ResponseEntity updatePaymentStatus(PaymentStatusDto paymentStatusDto);
	
	ResponseEntity deleteCustomerReport(Integer customerId);

	CustomerReportsDtoWithCount getAllCustomerReports();
	
	CustomerReportsDtoWithCount getAllPostpaidCustomerReports();
	
	CustomerReportsDtoWithCount getAllPrepaidCustomerReports();

	CustomerReportsDtoWithCount getAllActiveCustomerReports();

	CustomerReportsDtoWithCount getAllInactiveCustomerReports();
	
	List<CustomerReportsDto> searchCustomerRecords(String keyword);
}
