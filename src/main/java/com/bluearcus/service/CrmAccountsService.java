package com.bluearcus.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bluearcus.dto.CrmAccountsDto;

public interface CrmAccountsService {
	ResponseEntity saveAccount(CrmAccountsDto crmAccountsDto);

	ResponseEntity editAccount(Integer customerId,CrmAccountsDto crmAccountsDto);
	
	ResponseEntity updatePaymentStatus(Integer customerId,Boolean paymentStatus);
	
	ResponseEntity deleteAccount(Integer customerId);

	List<CrmAccountsDto> getAllAccounts();
}
