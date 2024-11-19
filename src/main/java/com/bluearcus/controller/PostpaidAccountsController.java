package com.bluearcus.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bluearcus.dto.ConsumedCallDataSmsDto;
import com.bluearcus.dto.DeductionDto;
import com.bluearcus.dto.PostpaidAccountsDto;
import com.bluearcus.dto.PostpaidCustomerBillDto;
import com.bluearcus.service.PostpaidAccountsService;
import com.bluearcus.service.PostpaidFlatFileService;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/postpaid/account")
@CrossOrigin("*")
public class PostpaidAccountsController {

	@Autowired
	private PostpaidAccountsService postpaidAccountsService;
	
	@Autowired
	private PostpaidFlatFileService postpaidFlatFileService;
	
	@PostMapping("/save")
	public ResponseEntity<PostpaidAccountsDto> savePostPaidAccount(@RequestBody PostpaidAccountsDto postpaidAccountsDto) {
		return postpaidAccountsService.savePostpaidAccount(postpaidAccountsDto);
	}

	@GetMapping("/get/account/{account_id}")
	public ResponseEntity<PostpaidAccountsDto> getPostPaidAccount(@PathVariable("account_id") Integer accountId) {
		return postpaidAccountsService.getPostpaidAccount(accountId);
	}
	
	@GetMapping("/get/customer/{customer_id}")
	public ResponseEntity<PostpaidAccountsDto> getPostPaidAccountByCustomerId(@PathVariable("customer_id") Integer customerId) {
		return postpaidAccountsService.getPostpaidAccountByCustomerId(customerId);
	}
	
	@GetMapping("/get/all/phone/numbers")
	public List<String> getAllPostPaidNumbers() {
		return postpaidAccountsService.getAllPostpaidNumbers();
	}
	
	@GetMapping("/generate/customer/bill/{msisdn}")
	public ResponseEntity<PostpaidCustomerBillDto> generateInvoiceForCustomer(@PathVariable String msisdn) {
		return postpaidAccountsService.generateBillForCustomer(msisdn);
	}
	
	@GetMapping("/get/file")
	public ResponseEntity<ConsumedCallDataSmsDto> getFiles(@RequestParam("file_name") String fileName, @RequestParam("start_date") String startDate, @RequestParam("end_date") String endDate) throws IOException {
		return postpaidFlatFileService.consumedCallDataSmsByCustomer(fileName, startDate, endDate);
	}
	
	@PostMapping("/deduct")
	public ResponseEntity<PostpaidAccountsDto> saveDeductionRecord(@RequestBody DeductionDto deductionDto) throws JsonProcessingException {
		return postpaidAccountsService.savePostpaidDeduction(deductionDto);
	}

	@GetMapping("/get/all/available/balance")
	public ResponseEntity<PostpaidAccountsDto> getAllAvailableBalance(@PathParam("imsi") String imsi) {
		return postpaidAccountsService.getAvailableBalance(imsi);
	}
}

