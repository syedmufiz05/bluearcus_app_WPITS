package com.bluearcus.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@CrossOrigin({"http://172.5.10.2:8090/","http://localhost:5173/","http://127.0.0.1:5173/","http://localhost:5500/","http://127.0.0.1:5500/"})
public class PostpaidAccountsController {

	@Autowired
	private PostpaidAccountsService postpaidAccountsService;
	
	@Autowired
	private PostpaidFlatFileService postpaidFlatFileService;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<PostpaidAccountsDto> savePostPaidAccount(@RequestBody PostpaidAccountsDto postpaidAccountsDto) {
		return postpaidAccountsService.savePostpaidAccount(postpaidAccountsDto);
	}

	@RequestMapping(value = "/get/account/{account_id}", method = RequestMethod.GET)
	public ResponseEntity<PostpaidAccountsDto> getPostPaidAccount(@PathVariable("account_id") Integer accountId) {
		return postpaidAccountsService.getPostpaidAccount(accountId);
	}
	
	@RequestMapping(value = "/get/customer/{customer_id}", method = RequestMethod.GET)
	public ResponseEntity<PostpaidAccountsDto> getPostPaidAccountByCustomerId(@PathVariable("customer_id") Integer customerId) {
		return postpaidAccountsService.getPostpaidAccountByCustomerId(customerId);
	}
	
	@RequestMapping(value = "/get/all/phone/numbers", method = RequestMethod.GET)
	public List<String> getAllPostPaidNumbers() {
		return postpaidAccountsService.getAllPostpaidNumbers();
	}
	
	@RequestMapping(value = "/generate/customer/bill/{msisdn}", method = RequestMethod.GET)
	public ResponseEntity<PostpaidCustomerBillDto> generateInvoiceForCustomer(@PathVariable("msisdn") String msisdn) {
		return postpaidAccountsService.generateBillForCustomer(msisdn);
	}
	
	@RequestMapping(value = "/get/file", method = RequestMethod.GET)
	public ResponseEntity<ConsumedCallDataSmsDto> getFiles(@RequestParam("file_name") String fileName,@RequestParam("start_date") String startDate, @RequestParam("end_date") String endDate) throws IOException {
		return postpaidFlatFileService.consumedCallDataSmsByCustomer(fileName, startDate, endDate);
	}
	
	@RequestMapping(value = "/deduct", method = RequestMethod.POST)
	public ResponseEntity<PostpaidAccountsDto> saveDeductionRecord(@RequestBody DeductionDto deductionDto) throws JsonProcessingException {
		return postpaidAccountsService.savePostpaidDeduction(deductionDto);
	}

	@RequestMapping(value = "/get/all/available/balance", method = RequestMethod.GET)
	public ResponseEntity<PostpaidAccountsDto> getAllAvailableBalance(@PathParam("imsi") String imsi) {
		return postpaidAccountsService.getAvailableBalance(imsi);
	}
}

