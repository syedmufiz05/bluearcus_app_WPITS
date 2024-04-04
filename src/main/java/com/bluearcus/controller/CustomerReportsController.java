package com.bluearcus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bluearcus.dto.CrmAccountsDto;
import com.bluearcus.dto.CustomerReportsDto;
import com.bluearcus.dto.CustomerReportsDtoWithCount;
import com.bluearcus.dto.PaymentStatusDto;
import com.bluearcus.service.CustomerReportsService;

@RestController
@RequestMapping("/api/customer/reports")
public class CustomerReportsController {
	@Autowired
	private CustomerReportsService customerReportsService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<CustomerReportsDto> saveCustomerReport(@RequestBody CustomerReportsDto customerReportsDto) {
		return customerReportsService.saveCustomerReport(customerReportsDto);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<CrmAccountsDto> editAccount(@RequestBody CustomerReportsDto customerReportsDto) {
		return customerReportsService.editCustomerReport(customerReportsDto);
	}

	@RequestMapping(value = "/update/payment/status", method = RequestMethod.PUT)
	public ResponseEntity<CustomerReportsDto> updatePaymentStatus(@RequestBody PaymentStatusDto paymentStatusDto) {
		return customerReportsService.updatePaymentStatus(paymentStatusDto);
	}
	
	@RequestMapping(value = "/delete/{customer_id}", method = RequestMethod.DELETE)
	public ResponseEntity<CrmAccountsDto> deleteAccount(@PathVariable("customer_id") Integer customerId) {
		return customerReportsService.deleteCustomerReport(customerId);
	}

	@RequestMapping(value = "/get/all", method = RequestMethod.GET)
	public CustomerReportsDtoWithCount getAllCustomerReports() {
		return customerReportsService.getAllCustomerReports();
	}

	@RequestMapping(value = "/get/prepaid/customers", method = RequestMethod.GET)
	public CustomerReportsDtoWithCount getAllPrepaidCustomers() {
		return customerReportsService.getAllPrepaidCustomerReports();
	}

	@RequestMapping(value = "/get/postpaid/customers", method = RequestMethod.GET)
	public CustomerReportsDtoWithCount getAllPostpaidCustomers() {
		return customerReportsService.getAllPostpaidCustomerReports();
	}

	@RequestMapping(value = "/get/active/customers", method = RequestMethod.GET)
	public CustomerReportsDtoWithCount getAllActiveCustomers() {
		return customerReportsService.getAllActiveCustomerReports();
	}

	@RequestMapping(value = "/get/inactive/customers", method = RequestMethod.GET)
	public CustomerReportsDtoWithCount getAllInactiveCustomers() {
		return customerReportsService.getAllInactiveCustomerReports();
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<List<CustomerReportsDto>> searchCustomerReports(@RequestParam("keyword") String keyword) {
		List<CustomerReportsDto> customerReportList = customerReportsService.searchCustomerRecords(keyword);
		return ResponseEntity.ok(customerReportList);
	}

}
