package com.bluearcus.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bluearcus.dto.DeductionDto;
import com.bluearcus.dto.PostpaidAccountsDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface PostpaidAccountsService {
	ResponseEntity savePostpaidAccount(PostpaidAccountsDto postpaidAccountsDto);
	
	ResponseEntity savePostpaidDeduction(DeductionDto deductionDto) throws JsonProcessingException;

	ResponseEntity getPostpaidAccount(Integer accountId);
	
	ResponseEntity getPostpaidAccountByCustomerId(Integer customerId);
	
	ResponseEntity getAvailableBalance(String imsi);
	
	List<String> getAllPostpaidNumbers();
	
	ResponseEntity generateBillForCustomer(String msisdn);
}
