package com.bluearcus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bluearcus.dto.CrmAccountsDto;
import com.bluearcus.exception.CustomMessage;
import com.bluearcus.model.CrmAccounts;
import com.bluearcus.repo.CrmAccountsRepo;

@Service
public class CrmAccountsServiceImpl implements CrmAccountsService {

	@Autowired
	private CrmAccountsRepo crmAccountsRepo;

	@Override
	public ResponseEntity saveAccount(CrmAccountsDto crmAccountsDto) {
		Optional<CrmAccounts> crmAccountDb = crmAccountsRepo.findByMsisdn(crmAccountsDto.getMsisdn());
		if (!crmAccountDb.isPresent()) {
			CrmAccounts crmAccount = new CrmAccounts();
			crmAccount.setCustomerId(crmAccountsDto.getCustomerId());
			crmAccount.setCustomerType(crmAccountsDto.getCustomerType());
			crmAccount.setImsi(crmAccountsDto.getImsi());
			crmAccount.setMsisdn(crmAccountsDto.getMsisdn());
			crmAccountsRepo.save(crmAccount);
			CrmAccountsDto crmAccountsDtoNew = new CrmAccountsDto(crmAccount.getId(), crmAccount.getCustomerId(),
					crmAccount.getCustomerType(), crmAccount.getImsi(), crmAccount.getMsisdn());
			return new ResponseEntity<>(crmAccountsDtoNew, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new CustomMessage(HttpStatus.CONFLICT.value(), "MSISDN already exist"));
	}

	@Override
	public ResponseEntity editAccount(CrmAccountsDto crmAccountsDto) {
		Optional<CrmAccounts> crmAccountsDb = crmAccountsRepo.findByMsisdn(crmAccountsDto.getMsisdn());
		if (crmAccountsDb.isPresent()) {
			CrmAccounts crmAccount = crmAccountsDb.get();
			crmAccount.setCustomerType(crmAccountsDto.getCustomerType() != null ? crmAccountsDto.getCustomerType() : crmAccount.getCustomerType());
			crmAccountsRepo.save(crmAccount);
			CrmAccountsDto crmAccountsDtoNew = new CrmAccountsDto(crmAccount.getId(), crmAccount.getCustomerId(),
					crmAccount.getCustomerType(), crmAccount.getImsi(), crmAccount.getMsisdn());
			return new ResponseEntity<>(crmAccountsDtoNew, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "MSISDN does n't exist"));
	}

	@Override
	public List<CrmAccountsDto> getAllAccounts() {
		List<CrmAccounts> crmAccountsDbList = crmAccountsRepo.findAll();
		List<CrmAccountsDto> crmAccountsDtoList = new ArrayList<>();
		for (CrmAccounts crmAccounts : crmAccountsDbList) {
			CrmAccountsDto crmAccountsDto = new CrmAccountsDto();
			crmAccountsDto.setCrmAccountId(crmAccounts.getId());
			crmAccountsDto.setCustomerId(crmAccounts.getCustomerId());
			crmAccountsDto.setCustomerType(crmAccounts.getCustomerType());
			crmAccountsDto.setImsi(crmAccounts.getImsi());
			crmAccountsDto.setMsisdn(crmAccounts.getMsisdn());
			crmAccountsDtoList.add(crmAccountsDto);
		}
		return crmAccountsDtoList;
	}

}
