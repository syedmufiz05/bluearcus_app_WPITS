package com.bluearcus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bluearcus.dto.CrmAccountsDto;
import com.bluearcus.dto.PaymentStatusDto;
import com.bluearcus.service.CrmAccountsService;

@RestController
@RequestMapping("/api/crm/account")
@CrossOrigin("*")
public class CrmAccountsController {
	@Autowired
	private CrmAccountsService crmAccountsService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<CrmAccountsDto> saveAccount(@RequestBody CrmAccountsDto crmAccountsDto) {
		return crmAccountsService.saveAccount(crmAccountsDto);
	}

	@RequestMapping(value = "/update/customer", method = RequestMethod.PUT)
	public ResponseEntity<CrmAccountsDto> editAccount(@RequestBody CrmAccountsDto crmAccountsDto) {
		return crmAccountsService.editAccount(crmAccountsDto);
	}
	
	@RequestMapping(value ="/update/customer/payment/status", method = RequestMethod.PUT)
	public ResponseEntity<CrmAccountsDto> updatePaymentStatus(@RequestBody PaymentStatusDto paymentStatusDto) {
		return crmAccountsService.updatePaymentStatus(paymentStatusDto);
	}
	
	@RequestMapping(value = "/delete/{customer_id}", method = RequestMethod.DELETE)
	public ResponseEntity<CrmAccountsDto> deleteAccount(@PathVariable("customer_id") Integer customerId) {
		return crmAccountsService.deleteAccount(customerId);
	}

	@RequestMapping(value = "/get/all", method = RequestMethod.GET)
	public List<CrmAccountsDto> getAllAccount() {
		return crmAccountsService.getAllAccounts();
	}
}
