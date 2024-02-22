package com.bluearcus.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bluearcus.dto.CrmAccountsDto;

public interface CrmAccountsService {
	ResponseEntity saveAccount(CrmAccountsDto crmAccountsDto);

	ResponseEntity editAccount(CrmAccountsDto crmAccountsDto);

	List<CrmAccountsDto> getAllAccounts();
}
