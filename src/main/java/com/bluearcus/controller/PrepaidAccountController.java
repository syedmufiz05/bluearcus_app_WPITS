package com.bluearcus.controller;

import com.bluearcus.dto.DeductionDto;
import com.bluearcus.dto.PrepaidAccountRecordsDto;
import com.bluearcus.dto.PrepaidAccountsDto;
import com.bluearcus.dto.PrepaidAvailBalanceDto;
import com.bluearcus.service.PrepaidAccountsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prepaid/account")
@CrossOrigin("*")
public class PrepaidAccountController {
	@Autowired
	private PrepaidAccountsService prepaidAccountsService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<PrepaidAccountsDto> savePrepaidAccount(@RequestBody PrepaidAccountsDto prepaidAccountsDto) {
		return prepaidAccountsService.savePrepaidAccount(prepaidAccountsDto);
	}

	@RequestMapping(value = "/edit/{account_id}", method = RequestMethod.PUT)
	public ResponseEntity<PrepaidAccountsDto> editPrepaidAccount(@PathVariable("account_id") Integer accountId, @RequestBody PrepaidAccountsDto prepaidAccountsDto) {
		return prepaidAccountsService.editPrepaidAccount(accountId, prepaidAccountsDto);
	}

	@RequestMapping(value = "/delete/{account_id}", method = RequestMethod.DELETE)
	public ResponseEntity deletePrepaidAccount(@PathVariable("account_id") Integer accountId) {
		return prepaidAccountsService.deletePrepaidAccount(accountId);
	}

	@RequestMapping(value = "/get/{account_id}", method = RequestMethod.GET)
	public ResponseEntity<PrepaidAccountsDto> getPrepaidAccount(@PathVariable("account_id") Integer accountId) {
		return prepaidAccountsService.getPrepaidAccount(accountId);
	}

	@RequestMapping(value = "/deduct", method = RequestMethod.POST)
	public ResponseEntity<PrepaidAccountsDto> saveDeductionRecord(@RequestBody DeductionDto deductionDto) throws JsonProcessingException {
		return prepaidAccountsService.savePrepaidDeduction(deductionDto);
	}

	@RequestMapping(value = "/get/all/available/balance", method = RequestMethod.GET)
	public ResponseEntity<PrepaidAvailBalanceDto> getAllAvailableBalance(@RequestParam("imsi") String imsi, @RequestParam("msisdn") String msisdn) {
		return prepaidAccountsService.getAvailableBalance(imsi, msisdn);
	}
	
	@RequestMapping(value = "/get/all/prepaid/account", method = RequestMethod.GET)
	public List<PrepaidAccountsDto> getAllPrepaidAccounts() {
		return prepaidAccountsService.getAllPrepaidAccounts();
	}
	
	@RequestMapping(value = "/get/all/deduction/details/{msisdn}", method = RequestMethod.GET)
	public List<PrepaidAccountRecordsDto> getAllDeductionDetails(@PathVariable("msisdn") String msisdn) throws JsonMappingException, JsonProcessingException {
		return prepaidAccountsService.getAllDeductionRecords(msisdn);
	}
}
