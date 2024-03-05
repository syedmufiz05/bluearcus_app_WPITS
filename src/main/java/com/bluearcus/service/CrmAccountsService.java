package com.bluearcus.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bluearcus.dto.CrmAccountsDto;
import com.bluearcus.dto.PaymentStatusDto;

public interface CrmAccountsService {
	ResponseEntity saveAccount(CrmAccountsDto crmAccountsDto);

	ResponseEntity editAccount(CrmAccountsDto crmAccountsDto);
	
	ResponseEntity updatePaymentStatus(PaymentStatusDto paymentStatusDto);
	
	ResponseEntity deleteAccount(Integer customerId);

	List<CrmAccountsDto> getAllAccounts();
}
