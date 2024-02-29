package com.bluearcus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bluearcus.dto.CrmAccountsDto;
import com.bluearcus.dto.PaymentStatusDto;
import com.bluearcus.exception.CustomMessage;
import com.bluearcus.model.CrmAccounts;
import com.bluearcus.model.PostpaidAccounts;
import com.bluearcus.model.PrepaidAccounts;
import com.bluearcus.repo.CrmAccountsRepo;
import com.bluearcus.repo.PostpaidAccountsRepo;
import com.bluearcus.repo.PrepaidAccountsRepository;

import jakarta.transaction.Transactional;

@Service
public class CrmAccountsServiceImpl implements CrmAccountsService {

	@Autowired
	private CrmAccountsRepo crmAccountsRepo;
	
	@Autowired
	private PrepaidAccountsRepository prepaidAccountsRepository;
	
	@Autowired
	private PostpaidAccountsRepo postpaidAccountsRepo;

	@Override
	public ResponseEntity saveAccount(CrmAccountsDto crmAccountsDto) {
		Optional<CrmAccounts> crmAccountDb = crmAccountsRepo.findByImsi(crmAccountsDto.getImsi());
		if (!crmAccountDb.isPresent()) {
			CrmAccounts crmAccount = new CrmAccounts();
			crmAccount.setCustomerId(crmAccountsDto.getCustomerId());
			crmAccount.setCustomerType(crmAccountsDto.getCustomerType());
			crmAccount.setImsi(crmAccountsDto.getImsi());
			crmAccount.setMsisdn(crmAccountsDto.getMsisdn());
			crmAccountsRepo.save(crmAccount);
			if (crmAccount.getCustomerType().equalsIgnoreCase("prepaid")) {
				PrepaidAccounts prepaidAccounts = new PrepaidAccounts();
				prepaidAccounts.setCustomerId(crmAccountsDto.getCustomerId());
				prepaidAccounts.setMsisdn(crmAccountsDto.getMsisdn());
				prepaidAccounts.setImsi(crmAccountsDto.getImsi());
				prepaidAccounts.setCalledStationId("");
				prepaidAccounts.setMonitoringKey("");
				prepaidAccounts.setAction("");
				prepaidAccounts.setDataParameterType("");
				prepaidAccounts.setCsVoiceCallSeconds(0l);
				prepaidAccounts.setFourGDataOctets(0);
				prepaidAccounts.setFiveGDataOctets(0);
				prepaidAccounts.setVolteCallSeconds(0l);
				prepaidAccounts.setTotalDataOctetsAvailable(0l);
				prepaidAccounts.setTotalInputDataOctetsAvailable(0l);
				prepaidAccounts.setTotalOutputDataOctetsAvailable(0l);
				prepaidAccounts.setTotalDataOctetsConsumed(0l);
				prepaidAccounts.setTotalCallSecondsAvailable(0l);
				prepaidAccounts.setTotalCallSecondsConsumed(0l);
				prepaidAccounts.setTotalSmsAvailable(0l);
				prepaidAccounts.setTotalSmsConsumed(0l);
				prepaidAccountsRepository.save(prepaidAccounts);
			}
			else {
				PostpaidAccounts postpaidAccounts=new PostpaidAccounts();
				postpaidAccounts.setCustomerId(crmAccountsDto.getCustomerId());
				postpaidAccounts.setMsisdn(crmAccountsDto.getMsisdn());
				postpaidAccounts.setImsi(crmAccountsDto.getImsi());
				postpaidAccounts.setDataParameterType("");
				postpaidAccounts.setCsVoiceCallSeconds(0l);
				postpaidAccounts.setFourGDataOctets(0);
				postpaidAccounts.setFiveGDataOctets(0);
				postpaidAccounts.setVolteCallSeconds(0l);
				postpaidAccounts.setTotalDataOctetsAvailable(0l);
				postpaidAccounts.setTotalInputDataOctetsAvailable(0l);
				postpaidAccounts.setTotalOutputDataOctetsAvailable(0l);
				postpaidAccounts.setTotalDataOctetsConsumed(0l);
				postpaidAccounts.setTotalCallSecondsAvailable(0l);
				postpaidAccounts.setTotalCallSecondsConsumed(0l);
				postpaidAccounts.setTotalSmsAvailable(0l);
				postpaidAccounts.setTotalSmsConsumed(0l);
				postpaidAccountsRepo.save(postpaidAccounts);
			}
			CrmAccountsDto crmAccountsDtoNew = new CrmAccountsDto(crmAccount.getId(), crmAccount.getCustomerId(), crmAccount.getCustomerType(), crmAccount.getImsi(), crmAccount.getMsisdn() ,crmAccount.getPaymentStatus());
			return new ResponseEntity<>(crmAccountsDtoNew, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomMessage(HttpStatus.CONFLICT.value(), "IMSI already exist"));
	}

	@Transactional
	@Override
	public ResponseEntity editAccount(Integer customerId, CrmAccountsDto crmAccountsDto) {
		Optional<CrmAccounts> crmAccountsDb = crmAccountsRepo.findByCustomerId(customerId);
		CrmAccounts crmAccount = null;
		if (crmAccountsDb.isPresent()) {
			crmAccount = crmAccountsDb.get();
			crmAccount.setCustomerType(crmAccountsDto.getCustomerType() != null ? crmAccountsDto.getCustomerType() : crmAccount.getCustomerType());
			crmAccountsRepo.save(crmAccount);
			
			if (crmAccountsDto.getCustomerType().equalsIgnoreCase("prepaid")) {
				
				// Delete customer record into the postpaid account
				Optional<PostpaidAccounts> postpaidAccount = postpaidAccountsRepo.findByCustomerId(customerId);
				postpaidAccountsRepo.delete(postpaidAccount.get());
				
				PrepaidAccounts prepaidAccounts = new PrepaidAccounts();
				prepaidAccounts.setCustomerId(customerId);
				prepaidAccounts.setMsisdn(crmAccount.getMsisdn());
				prepaidAccounts.setImsi(crmAccount.getImsi());
				prepaidAccounts.setCalledStationId("");
				prepaidAccounts.setMonitoringKey("");
				prepaidAccounts.setAction("");
				prepaidAccounts.setDataParameterType("");
				prepaidAccounts.setCsVoiceCallSeconds(0l);
				prepaidAccounts.setFourGDataOctets(0);
				prepaidAccounts.setFiveGDataOctets(0);
				prepaidAccounts.setVolteCallSeconds(0l);
				prepaidAccounts.setTotalDataOctetsAvailable(0l);
				prepaidAccounts.setTotalInputDataOctetsAvailable(0l);
				prepaidAccounts.setTotalOutputDataOctetsAvailable(0l);
				prepaidAccounts.setTotalDataOctetsConsumed(0l);
				prepaidAccounts.setTotalCallSecondsAvailable(0l);
				prepaidAccounts.setTotalCallSecondsConsumed(0l);
				prepaidAccounts.setTotalSmsAvailable(0l);
				prepaidAccounts.setTotalSmsConsumed(0l);
				prepaidAccountsRepository.save(prepaidAccounts);
			}
			else {
				
				//delete customer record into the prepaid account
				Optional<PrepaidAccounts> prepaidAccounts = prepaidAccountsRepository.findByCustomerId(customerId);
				prepaidAccountsRepository.delete(prepaidAccounts.get());
				
				PostpaidAccounts postpaidAccounts = new PostpaidAccounts();
				postpaidAccounts.setCustomerId(customerId);
				postpaidAccounts.setMsisdn(crmAccount.getMsisdn());
				postpaidAccounts.setImsi(crmAccount.getImsi());
				postpaidAccounts.setDataParameterType("");
				postpaidAccounts.setCsVoiceCallSeconds(0l);
				postpaidAccounts.setFourGDataOctets(0);
				postpaidAccounts.setFiveGDataOctets(0);
				postpaidAccounts.setVolteCallSeconds(0l);
				postpaidAccounts.setTotalDataOctetsAvailable(0l);
				postpaidAccounts.setTotalInputDataOctetsAvailable(0l);
				postpaidAccounts.setTotalOutputDataOctetsAvailable(0l);
				postpaidAccounts.setTotalDataOctetsConsumed(0l);
				postpaidAccounts.setTotalCallSecondsAvailable(0l);
				postpaidAccounts.setTotalCallSecondsConsumed(0l);
				postpaidAccounts.setTotalSmsAvailable(0l);
				postpaidAccounts.setTotalSmsConsumed(0l);
				postpaidAccountsRepo.save(postpaidAccounts);
			}
			
			CrmAccountsDto crmAccountsDtoNew = new CrmAccountsDto(crmAccount.getId(), crmAccount.getCustomerId(), crmAccount.getCustomerType(), crmAccount.getImsi(), crmAccount.getMsisdn() , crmAccount.getPaymentStatus());
			return new ResponseEntity<>(crmAccountsDtoNew, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Customer Id does n't exist"));
	}
	
	@Override
	public ResponseEntity updatePaymentStatus(PaymentStatusDto paymentStatusDto) {
		Optional<CrmAccounts> crmAccountDb = crmAccountsRepo.findByCustomerId(paymentStatusDto.getCustomerId());
		if (crmAccountDb.isPresent()) {
			CrmAccounts crmAccount = crmAccountDb.get();
			crmAccount.setPaymentStatus(paymentStatusDto.getPaymentStatus());
			crmAccountsRepo.save(crmAccount);
			CrmAccountsDto crmAccountsDto = new CrmAccountsDto(crmAccount.getId(), crmAccount.getCustomerId(),
					crmAccount.getCustomerType(), crmAccount.getImsi(), crmAccount.getMsisdn(),
					crmAccount.getPaymentStatus());
			return new ResponseEntity(crmAccountsDto, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Customer Id does n't exist"));
	}

	
	@Override
	public ResponseEntity deleteAccount(Integer customerId) {
		Optional<CrmAccounts> crmAccountDb = crmAccountsRepo.findByCustomerId(customerId);
		if (crmAccountDb.isPresent()) {
			CrmAccounts crmAccount = crmAccountDb.get();
			if (crmAccount.getCustomerType().equalsIgnoreCase("prepaid")) {
				Optional<PrepaidAccounts> prepaidAccounts = prepaidAccountsRepository.findByCustomerId(customerId);
				prepaidAccountsRepository.delete(prepaidAccounts.get());
			} else {
				Optional<PostpaidAccounts> postpaidAccount = postpaidAccountsRepo.findByCustomerId(customerId);
				postpaidAccountsRepo.delete(postpaidAccount.get());
			}
			crmAccountsRepo.deleteByCustomerId(customerId);
			return ResponseEntity.status(HttpStatus.OK).body(new CustomMessage(HttpStatus.OK.value(), "Deleted successfully"));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid customer id"));
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
			crmAccountsDto.setPaymentStatus(crmAccounts.getPaymentStatus());
			crmAccountsDtoList.add(crmAccountsDto);
		}
		return crmAccountsDtoList;
	}

}
