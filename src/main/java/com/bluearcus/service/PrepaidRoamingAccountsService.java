package com.bluearcus.service;

import com.bluearcus.dto.PrepaidRoamingAccountsDto;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface PrepaidRoamingAccountsService {
    ResponseEntity savePrepaidRoamingAccount(PrepaidRoamingAccountsDto prepaidRoamingAccountsDto);

    ResponseEntity editPrepaidRoamingAccount(Integer roamingAccountId, PrepaidRoamingAccountsDto prepaidRoamingAccountsDto);

    ResponseEntity deletePrepaidRoamingAccount(Integer roamingAccountId);

    ResponseEntity getPrepaidRoamingAccount(Integer roamingAccountId);
    
    List<PrepaidRoamingAccountsDto> getAllPrepaidRoamingAccounts();
}
