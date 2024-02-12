package com.bluearcus.controller;

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

import com.bluearcus.dto.DeductionDto;
import com.bluearcus.dto.PostpaidAccountsDto;
import com.bluearcus.service.PostpaidAccountsService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/postpaid/account")
@CrossOrigin("http://172.5.10.2:8090/")
public class PostpaidAccountsController {

	@Autowired
	private PostpaidAccountsService postpaidAccountsService;
	
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
	
	@RequestMapping(value = "/get/all/numbers", method = RequestMethod.GET)
	public List<String> getAllPostPaidNumbers() {
		return postpaidAccountsService.getAllPostpaidNumbers();
	}
	
	@RequestMapping(value = "/deduct", method = RequestMethod.POST)
	public ResponseEntity<PostpaidAccountsDto> saveDeductionRecord(@RequestBody DeductionDto deductionDto) {
		return postpaidAccountsService.savePostpaidDeduction(deductionDto);
	}

	@RequestMapping(value = "/get/all/available/balance", method = RequestMethod.GET)
	public ResponseEntity<PostpaidAccountsDto> getAllAvailableBalance(@PathParam("imsi") String imsi) {
		return postpaidAccountsService.getAvailableBalance(imsi);
	}
}

