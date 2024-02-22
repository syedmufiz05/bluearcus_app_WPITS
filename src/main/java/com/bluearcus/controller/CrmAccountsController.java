package com.bluearcus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bluearcus.dto.CrmAccountsDto;
import com.bluearcus.service.CrmAccountsService;

@RestController
@RequestMapping("/api/crm/account")
public class CrmAccountsController {
	@Autowired
	private CrmAccountsService crmAccountsService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<CrmAccountsDto> saveAccount(@RequestBody CrmAccountsDto crmAccountsDto) {
		return crmAccountsService.saveAccount(crmAccountsDto);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public ResponseEntity<CrmAccountsDto> editAccount(@RequestBody CrmAccountsDto crmAccountsDto) {
		return crmAccountsService.editAccount(crmAccountsDto);
	}

	@RequestMapping(value = "/get/all", method = RequestMethod.GET)
	public List<CrmAccountsDto> getAllAccount() {
		return crmAccountsService.getAllAccounts();
	}
}
