package com.bluearcus.controller;

import com.bluearcus.dto.DeductionDto;
import com.bluearcus.dto.PrepaidAccountRecordsDto;
import com.bluearcus.dto.PrepaidAccountsDto;
import com.bluearcus.dto.PrepaidAvailBalanceDto;
import com.bluearcus.service.PrepaidAccountsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.net.URISyntaxException;
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

	@PostMapping("/save")
	public ResponseEntity<PrepaidAccountsDto> savePrepaidAccount(@RequestBody PrepaidAccountsDto prepaidAccountsDto) {
		return prepaidAccountsService.savePrepaidAccount(prepaidAccountsDto);
	}

	@PutMapping("/edit/{account_id}")
	public ResponseEntity<PrepaidAccountsDto> editPrepaidAccount(@PathVariable("account_id") Integer accountId, @RequestBody PrepaidAccountsDto prepaidAccountsDto) {
		return prepaidAccountsService.editPrepaidAccount(accountId, prepaidAccountsDto);
	}

	@DeleteMapping("/delete/{account_id}")
	public ResponseEntity deletePrepaidAccount(@PathVariable("account_id") Integer accountId) {
		return prepaidAccountsService.deletePrepaidAccount(accountId);
	}

	@GetMapping("/get/{account_id}")
	public ResponseEntity<PrepaidAccountsDto> getPrepaidAccount(@PathVariable("account_id") Integer accountId) {
		return prepaidAccountsService.getPrepaidAccount(accountId);
	}

	@PostMapping("/deduct")
	public ResponseEntity<PrepaidAccountsDto> saveDeductionRecord(@RequestBody DeductionDto deductionDto) throws JsonProcessingException {
		return prepaidAccountsService.savePrepaidDeduction(deductionDto);
	}

	@GetMapping("/get/all/available/balance")
	public ResponseEntity<PrepaidAvailBalanceDto> getAllAvailableBalance(@RequestParam String imsi, @RequestParam String msisdn) throws URISyntaxException {
		return prepaidAccountsService.getAvailableBalance(imsi, msisdn);
	}
	
	@GetMapping("/get/all/prepaid/account")
	public List<PrepaidAccountsDto> getAllPrepaidAccounts() {
		return prepaidAccountsService.getAllPrepaidAccounts();
	}
	
	@GetMapping("/get/all/deduction/details/{msisdn}")
	public List<PrepaidAccountRecordsDto> getAllDeductionDetails(@PathVariable String msisdn) throws JsonMappingException, JsonProcessingException {
		return prepaidAccountsService.getAllDeductionRecords(msisdn);
	}
}
