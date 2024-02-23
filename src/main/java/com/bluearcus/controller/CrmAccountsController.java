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
import com.bluearcus.service.CrmAccountsService;

@RestController
@RequestMapping("/api/crm/account")
@CrossOrigin({"http://172.5.10.2:8090/","http://localhost:5173/","http://127.0.0.1:5173/"})
public class CrmAccountsController {
	@Autowired
	private CrmAccountsService crmAccountsService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<CrmAccountsDto> saveAccount(@RequestBody CrmAccountsDto crmAccountsDto) {
		return crmAccountsService.saveAccount(crmAccountsDto);
	}

	@RequestMapping(value = "/edit/{customer_id}", method = RequestMethod.PUT)
	public ResponseEntity<CrmAccountsDto> editAccount(@PathVariable("customer_id") Integer customerId, @RequestBody CrmAccountsDto crmAccountsDto) {
		return crmAccountsService.editAccount(customerId,crmAccountsDto);
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
