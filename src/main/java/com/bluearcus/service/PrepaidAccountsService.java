package com.bluearcus.service;

import com.bluearcus.dto.DeductionDto;
import com.bluearcus.dto.PrepaidAccountRecordsDto;
import com.bluearcus.dto.PrepaidAccountsDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface PrepaidAccountsService {
	ResponseEntity savePrepaidAccount(PrepaidAccountsDto prepaidAccountsDto);

	ResponseEntity savePrepaidDeduction(DeductionDto deductionDto) throws JsonProcessingException;

	ResponseEntity editPrepaidAccount(Integer accountId, PrepaidAccountsDto prepaidAccountsDto);

	ResponseEntity deletePrepaidAccount(Integer accountId);

	ResponseEntity getPrepaidAccount(Integer accountId);

	ResponseEntity getAvailableBalance(String imsi, String msisdn);

	List<PrepaidAccountsDto> getAllPrepaidAccounts();

	List<PrepaidAccountRecordsDto> getAllDeductionRecords(String msisdn) throws JsonMappingException, JsonProcessingException;
}
