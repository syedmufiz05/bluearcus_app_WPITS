package com.bluearcus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bluearcus.dto.CrmAccountsDto;
import com.bluearcus.dto.PaymentStatusDto;
import com.bluearcus.service.CrmAccountsService;

@RestController
@RequestMapping("/api/crm/account")
@CrossOrigin("*")
public class CrmAccountsController {
	@Autowired
	private CrmAccountsService crmAccountsService;

	@PostMapping("/save")
	public ResponseEntity<CrmAccountsDto> saveAccount(@RequestBody CrmAccountsDto crmAccountsDto) {
		return crmAccountsService.saveAccount(crmAccountsDto);
	}

	@PutMapping("/update/customer")
	public ResponseEntity<CrmAccountsDto> editAccount(@RequestBody CrmAccountsDto crmAccountsDto) {
		return crmAccountsService.editAccount(crmAccountsDto);
	}
	
	@PutMapping("/update/customer/payment/status")
	public ResponseEntity<CrmAccountsDto> updatePaymentStatus(@RequestBody PaymentStatusDto paymentStatusDto) {
		return crmAccountsService.updatePaymentStatus(paymentStatusDto);
	}
	
	@DeleteMapping("/delete/{customer_id}")
	public ResponseEntity<CrmAccountsDto> deleteAccount(@PathVariable("customer_id") Integer customerId) {
		return crmAccountsService.deleteAccount(customerId);
	}

	@GetMapping("/get/all")
	public List<CrmAccountsDto> getAllAccount() {
		return crmAccountsService.getAllAccounts();
	}
}
