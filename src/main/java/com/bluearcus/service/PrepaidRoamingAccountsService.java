package com.bluearcus.service;

import com.bluearcus.dto.PrepaidRoamingAccountsDto;
import org.springframework.http.ResponseEntity;

public interface PrepaidRoamingAccountsService {
    ResponseEntity savePrepaidRoamingAccount(PrepaidRoamingAccountsDto prepaidRoamingAccountsDto);

    ResponseEntity editPrepaidRoamingAccount(Integer roamingAccountId, PrepaidRoamingAccountsDto prepaidRoamingAccountsDto);

    ResponseEntity deletePrepaidRoamingAccount(Integer roamingAccountId);

    ResponseEntity getPrepaidRoamingAccount(Integer roamingAccountId);
}
