package com.bluearcus.service;

import com.bluearcus.dto.DeductionDto;
import com.bluearcus.dto.PrepaidAccountsDto;
import org.springframework.http.ResponseEntity;

public interface PrepaidAccountsService {
    ResponseEntity savePrepaidAccount(PrepaidAccountsDto prepaidAccountsDto);

    ResponseEntity saveDeductionRecord(DeductionDto deductionDto);

    ResponseEntity editPrepaidAccount(Integer accountId, PrepaidAccountsDto prepaidAccountsDto);

    ResponseEntity deletePrepaidAccount(Integer accountId);

    ResponseEntity getPrepaidAccount(Integer accountId);

    ResponseEntity getAvailableBalance(String imsi);
}
