package com.bluearcus.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bluearcus.dto.CrmAccountsDto;
import com.bluearcus.dto.CustomerReportsDto;
import com.bluearcus.dto.CustomerReportsDtoWithCount;
import com.bluearcus.dto.PaymentStatusDto;
import com.bluearcus.service.CustomerReportsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customer/reports")
public class CustomerReportsController {
	@Autowired
	private CustomerReportsService customerReportsService;
	
	private Logger logger = LoggerFactory.getLogger(CustomerReportsController.class);

	@PostMapping("/save")
	public ResponseEntity<CustomerReportsDto> saveCustomerReport(@Valid @RequestBody CustomerReportsDto customerReportsDto) {
		logger.info("crm request {}", customerReportsDto);
		return customerReportsService.saveCustomerReport(customerReportsDto);
	}
	
	@PutMapping("/update")
	public ResponseEntity<CrmAccountsDto> editAccount(@RequestBody CustomerReportsDto customerReportsDto) {
		return customerReportsService.editCustomerReport(customerReportsDto);
	}

	@PutMapping("/update/payment/status")
	public ResponseEntity<CustomerReportsDto> updatePaymentStatus(@RequestBody PaymentStatusDto paymentStatusDto) {
		return customerReportsService.updatePaymentStatus(paymentStatusDto);
	}
	
	@DeleteMapping("/delete/{customer_id}")
	public ResponseEntity<CrmAccountsDto> deleteAccount(@PathVariable("customer_id") Integer customerId) {
		return customerReportsService.deleteCustomerReport(customerId);
	}

	@GetMapping("/get/all")
	public CustomerReportsDtoWithCount getAllCustomerReports() {
		return customerReportsService.getAllCustomerReports();
	}

	@GetMapping("/get/prepaid/customers")
	public CustomerReportsDtoWithCount getAllPrepaidCustomers() {
		return customerReportsService.getAllPrepaidCustomerReports();
	}

	@GetMapping("/get/postpaid/customers")
	public CustomerReportsDtoWithCount getAllPostpaidCustomers() {
		return customerReportsService.getAllPostpaidCustomerReports();
	}

	@GetMapping("/get/active/customers")
	public CustomerReportsDtoWithCount getAllActiveCustomers() {
		return customerReportsService.getAllActiveCustomerReports();
	}

	@GetMapping("/get/inactive/customers")
	public CustomerReportsDtoWithCount getAllInactiveCustomers() {
		return customerReportsService.getAllInactiveCustomerReports();
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<CustomerReportsDto>> searchCustomerReports(@RequestParam String keyword) {
		List<CustomerReportsDto> customerReportList = customerReportsService.searchCustomerRecords(keyword);
		return ResponseEntity.ok(customerReportList);
	}

}
