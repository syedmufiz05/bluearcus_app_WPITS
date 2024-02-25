package com.bluearcus.service;

import com.bluearcus.dto.PrepaidRoamingAccountsDto;
import com.bluearcus.exception.CustomMessage;
import com.bluearcus.model.PrepaidRoamingAccounts;
import com.bluearcus.repo.PrepaidRoamingAccountsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PrepaidRoamingAccountsServiceImpl implements PrepaidRoamingAccountsService {
    @Autowired
    private PrepaidRoamingAccountsRepo prepaidRoamingAccountsRepo;

    @Override
    public ResponseEntity savePrepaidRoamingAccount(PrepaidRoamingAccountsDto prepaidRoamingAccountsDto) {
        Optional<PrepaidRoamingAccounts> prepaidRoamingAccount = prepaidRoamingAccountsRepo.findByRoamingAccountId(prepaidRoamingAccountsDto.getRoamingAccountId() != null ? prepaidRoamingAccountsDto.getRoamingAccountId() : 0);
        if (!prepaidRoamingAccount.isPresent()) {
            PrepaidRoamingAccounts prepaidRoamingAccountDb = new PrepaidRoamingAccounts();
            prepaidRoamingAccountDb.setRoamingCustomerId(prepaidRoamingAccountsDto.getRoamingCustomerId() != null ? prepaidRoamingAccountsDto.getRoamingCustomerId() : Integer.valueOf(""));
            prepaidRoamingAccountDb.setMsisdn(prepaidRoamingAccountsDto.getMsisdn() != null ? prepaidRoamingAccountsDto.getMsisdn() : "");
            prepaidRoamingAccountDb.setRoamingCsVoiceCallSeconds(prepaidRoamingAccountsDto.getRoamingCsVoiceCallSeconds() != null ? prepaidRoamingAccountsDto.getRoamingCsVoiceCallSeconds() : Integer.valueOf(""));
            prepaidRoamingAccountDb.setRoaming4gDataOctets(prepaidRoamingAccountsDto.getRoaming4gDataOctets() != null ? prepaidRoamingAccountsDto.getRoaming4gDataOctets() : Integer.valueOf(""));
            prepaidRoamingAccountDb.setRoaming5gDataOctets(prepaidRoamingAccountsDto.getRoaming5gDataOctets() != null ? prepaidRoamingAccountsDto.getRoaming5gDataOctets() : Integer.valueOf(""));
            prepaidRoamingAccountDb.setRoamingVolteCallSeconds(prepaidRoamingAccountsDto.getRoamingVolteCallSeconds() != null ? prepaidRoamingAccountsDto.getRoamingVolteCallSeconds() : Integer.valueOf(""));
            prepaidRoamingAccountDb.setRoamingTotalDataOctetsAvailable(prepaidRoamingAccountsDto.getRoamingTotalDataOctetsAvailable() != null ? prepaidRoamingAccountsDto.getRoamingTotalDataOctetsAvailable() : Integer.valueOf(""));
            prepaidRoamingAccountDb.setRoamingTotalDataOctetsConsumed(prepaidRoamingAccountsDto.getRoamingTotalDataOctetsConsumed() != null ? prepaidRoamingAccountsDto.getRoamingTotalDataOctetsConsumed() : Integer.valueOf(""));
            prepaidRoamingAccountDb.setRoamingTotalCallSecondsAvailable(prepaidRoamingAccountsDto.getRoamingTotalCallSecondsAvailable() != null ? prepaidRoamingAccountsDto.getRoamingTotalCallSecondsAvailable() : Integer.valueOf(""));
            prepaidRoamingAccountDb.setRoamingTotalCallSecondsConsumed(prepaidRoamingAccountsDto.getRoamingTotalCallSecondsConsumed() != null ? prepaidRoamingAccountsDto.getRoamingTotalCallSecondsConsumed() : Integer.valueOf(""));
            prepaidRoamingAccountDb.setRoamingTotalSmsAvailable(prepaidRoamingAccountsDto.getRoamingTotalSmsAvailable() != null ? prepaidRoamingAccountsDto.getRoamingTotalSmsAvailable() : Integer.valueOf(""));
            prepaidRoamingAccountDb.setRoamingTotalSmsConsumed(prepaidRoamingAccountsDto.getRoamingTotalSmsConsumed() != null ? prepaidRoamingAccountsDto.getRoamingTotalSmsConsumed() : Integer.valueOf(""));
            prepaidRoamingAccountsRepo.save(prepaidRoamingAccountDb);
            PrepaidRoamingAccountsDto prepaidRoamingAccountsDtoNew = new PrepaidRoamingAccountsDto(prepaidRoamingAccountDb.getRoamingAccountId(), prepaidRoamingAccountDb.getRoamingCustomerId(), prepaidRoamingAccountDb.getMsisdn(), prepaidRoamingAccountDb.getRoamingCsVoiceCallSeconds(), prepaidRoamingAccountDb.getRoaming4gDataOctets(), prepaidRoamingAccountDb.getRoaming5gDataOctets(), prepaidRoamingAccountDb.getRoamingVolteCallSeconds(), prepaidRoamingAccountDb.getRoamingTotalDataOctetsAvailable(), prepaidRoamingAccountDb.getRoamingTotalDataOctetsConsumed(), prepaidRoamingAccountDb.getRoamingTotalCallSecondsAvailable(), prepaidRoamingAccountDb.getRoamingTotalCallSecondsConsumed(), prepaidRoamingAccountDb.getRoamingTotalSmsAvailable(), prepaidRoamingAccountDb.getRoamingTotalSmsConsumed());
            return new ResponseEntity<>(prepaidRoamingAccountsDtoNew, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomMessage(HttpStatus.CONFLICT.value(), "Roaming account id already exist"));
    }

    @Override
    public ResponseEntity editPrepaidRoamingAccount(Integer roamingAccountId, PrepaidRoamingAccountsDto prepaidRoamingAccountsDto) {
        Optional<PrepaidRoamingAccounts> prepaidRoamingAccount = prepaidRoamingAccountsRepo.findByRoamingAccountId(roamingAccountId);
        if (prepaidRoamingAccount.isPresent()) {
            PrepaidRoamingAccounts prepaidRoamingAccountDb = prepaidRoamingAccount.get();
            prepaidRoamingAccountDb.setRoamingCustomerId(prepaidRoamingAccountsDto.getRoamingCustomerId() != null ? prepaidRoamingAccountsDto.getRoamingCustomerId() : prepaidRoamingAccountDb.getRoamingAccountId());
            prepaidRoamingAccountDb.setMsisdn(prepaidRoamingAccountsDto.getMsisdn() != null ? prepaidRoamingAccountsDto.getMsisdn() : prepaidRoamingAccountDb.getMsisdn());
            prepaidRoamingAccountDb.setRoamingCsVoiceCallSeconds(prepaidRoamingAccountsDto.getRoamingCsVoiceCallSeconds() != null ? prepaidRoamingAccountsDto.getRoamingCsVoiceCallSeconds() : prepaidRoamingAccountDb.getRoamingCsVoiceCallSeconds());
            prepaidRoamingAccountDb.setRoaming4gDataOctets(prepaidRoamingAccountsDto.getRoaming4gDataOctets() != null ? prepaidRoamingAccountsDto.getRoaming4gDataOctets() : prepaidRoamingAccountDb.getRoaming4gDataOctets());
            prepaidRoamingAccountDb.setRoaming5gDataOctets(prepaidRoamingAccountsDto.getRoaming5gDataOctets() != null ? prepaidRoamingAccountsDto.getRoaming5gDataOctets() : prepaidRoamingAccountDb.getRoaming5gDataOctets());
            prepaidRoamingAccountDb.setRoamingVolteCallSeconds(prepaidRoamingAccountsDto.getRoamingVolteCallSeconds() != null ? prepaidRoamingAccountsDto.getRoamingVolteCallSeconds() : prepaidRoamingAccountDb.getRoamingVolteCallSeconds());
            prepaidRoamingAccountDb.setRoamingTotalDataOctetsAvailable(prepaidRoamingAccountsDto.getRoamingTotalDataOctetsAvailable() != null ? prepaidRoamingAccountsDto.getRoamingTotalDataOctetsAvailable() : prepaidRoamingAccountDb.getRoamingTotalDataOctetsAvailable());
            prepaidRoamingAccountDb.setRoamingTotalDataOctetsConsumed(prepaidRoamingAccountsDto.getRoamingTotalDataOctetsConsumed() != null ? prepaidRoamingAccountsDto.getRoamingTotalDataOctetsConsumed() : prepaidRoamingAccountDb.getRoamingTotalDataOctetsConsumed());
            prepaidRoamingAccountDb.setRoamingTotalCallSecondsAvailable(prepaidRoamingAccountsDto.getRoamingTotalCallSecondsAvailable() != null ? prepaidRoamingAccountsDto.getRoamingTotalCallSecondsAvailable() : prepaidRoamingAccountDb.getRoamingTotalCallSecondsAvailable());
            prepaidRoamingAccountDb.setRoamingTotalCallSecondsConsumed(prepaidRoamingAccountsDto.getRoamingTotalCallSecondsConsumed() != null ? prepaidRoamingAccountsDto.getRoamingTotalCallSecondsConsumed() : prepaidRoamingAccountDb.getRoamingTotalCallSecondsConsumed());
            prepaidRoamingAccountDb.setRoamingTotalSmsAvailable(prepaidRoamingAccountsDto.getRoamingTotalSmsAvailable() != null ? prepaidRoamingAccountsDto.getRoamingTotalSmsAvailable() : prepaidRoamingAccountDb.getRoamingTotalSmsAvailable());
            prepaidRoamingAccountDb.setRoamingTotalSmsConsumed(prepaidRoamingAccountsDto.getRoamingTotalSmsConsumed() != null ? prepaidRoamingAccountsDto.getRoamingTotalSmsConsumed() : prepaidRoamingAccountDb.getRoamingTotalSmsConsumed());
            prepaidRoamingAccountsRepo.save(prepaidRoamingAccountDb);
            PrepaidRoamingAccountsDto prepaidRoamingAccountsDtoNew = new PrepaidRoamingAccountsDto(prepaidRoamingAccountDb.getRoamingAccountId(), prepaidRoamingAccountDb.getRoamingCustomerId(), prepaidRoamingAccountDb.getMsisdn(), prepaidRoamingAccountDb.getRoamingCsVoiceCallSeconds(), prepaidRoamingAccountDb.getRoaming4gDataOctets(), prepaidRoamingAccountDb.getRoaming5gDataOctets(), prepaidRoamingAccountDb.getRoamingVolteCallSeconds(), prepaidRoamingAccountDb.getRoamingTotalDataOctetsAvailable(), prepaidRoamingAccountDb.getRoamingTotalDataOctetsConsumed(), prepaidRoamingAccountDb.getRoamingTotalCallSecondsAvailable(), prepaidRoamingAccountDb.getRoamingTotalCallSecondsConsumed(), prepaidRoamingAccountDb.getRoamingTotalSmsAvailable(), prepaidRoamingAccountDb.getRoamingTotalSmsConsumed());
            return new ResponseEntity<>(prepaidRoamingAccountsDtoNew, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid Roaming account id"));
    }

    @Transactional
    @Override
    public ResponseEntity deletePrepaidRoamingAccount(Integer roamingAccountId) {
        Optional<PrepaidRoamingAccounts> prepaidRoamingAccounts = prepaidRoamingAccountsRepo.findByRoamingAccountId(roamingAccountId);
        if (prepaidRoamingAccounts.isPresent()) {
            prepaidRoamingAccountsRepo.deleteById(roamingAccountId);
            return ResponseEntity.status(HttpStatus.OK).body(new CustomMessage(HttpStatus.OK.value(), "Deleted Successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid Roaming account id"));
    }

    @Override
    public ResponseEntity getPrepaidRoamingAccount(Integer roamingAccountId) {
        Optional<PrepaidRoamingAccounts> prepaidRoamingAccounts = prepaidRoamingAccountsRepo.findByRoamingAccountId(roamingAccountId);
        if (prepaidRoamingAccounts.isPresent()) {
            PrepaidRoamingAccounts prepaidRoamingAccountsDb = prepaidRoamingAccounts.get();
            PrepaidRoamingAccountsDto prepaidRoamingAccountsDto = new PrepaidRoamingAccountsDto();
            prepaidRoamingAccountsDto.setRoamingAccountId(prepaidRoamingAccountsDb.getRoamingAccountId());
            prepaidRoamingAccountsDto.setRoamingCustomerId(prepaidRoamingAccountsDb.getRoamingCustomerId());
            prepaidRoamingAccountsDto.setMsisdn(prepaidRoamingAccountsDb.getMsisdn());
            prepaidRoamingAccountsDto.setRoamingCsVoiceCallSeconds(prepaidRoamingAccountsDb.getRoamingCsVoiceCallSeconds());
            prepaidRoamingAccountsDto.setRoaming4gDataOctets(prepaidRoamingAccountsDb.getRoaming4gDataOctets());
            prepaidRoamingAccountsDto.setRoaming5gDataOctets(prepaidRoamingAccountsDb.getRoaming5gDataOctets());
            prepaidRoamingAccountsDto.setRoamingVolteCallSeconds(prepaidRoamingAccountsDb.getRoamingVolteCallSeconds());
            prepaidRoamingAccountsDto.setRoamingTotalDataOctetsAvailable(prepaidRoamingAccountsDb.getRoamingTotalDataOctetsAvailable());
            prepaidRoamingAccountsDto.setRoamingTotalDataOctetsConsumed(prepaidRoamingAccountsDb.getRoamingTotalDataOctetsConsumed());
            prepaidRoamingAccountsDto.setRoamingTotalCallSecondsAvailable(prepaidRoamingAccountsDb.getRoamingTotalCallSecondsAvailable());
            prepaidRoamingAccountsDto.setRoamingTotalCallSecondsConsumed(prepaidRoamingAccountsDb.getRoamingTotalCallSecondsConsumed());
            prepaidRoamingAccountsDto.setRoamingTotalSmsAvailable(prepaidRoamingAccountsDb.getRoamingTotalSmsAvailable());
            prepaidRoamingAccountsDto.setRoamingTotalSmsConsumed(prepaidRoamingAccountsDb.getRoamingTotalSmsConsumed());
            return new ResponseEntity<>(prepaidRoamingAccountsDto, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid Roaming account id"));
    }

	@Override
	public List<PrepaidRoamingAccountsDto> getAllPrepaidRoamingAccounts() {
		List<PrepaidRoamingAccounts> prepaidRoamingAccountDbList = prepaidRoamingAccountsRepo.findAll();
		List<PrepaidRoamingAccountsDto> prepaidRoamingAccountsDtoList = new ArrayList<>();
		for (PrepaidRoamingAccounts prepaidRoamingAccountsDb : prepaidRoamingAccountDbList) {
			PrepaidRoamingAccountsDto prepaidRoamingAccountsDto = new PrepaidRoamingAccountsDto();
			prepaidRoamingAccountsDto.setRoamingAccountId(prepaidRoamingAccountsDb.getRoamingAccountId());
			prepaidRoamingAccountsDto.setRoamingCustomerId(prepaidRoamingAccountsDb.getRoamingCustomerId());
			prepaidRoamingAccountsDto.setMsisdn(prepaidRoamingAccountsDb.getMsisdn());
			prepaidRoamingAccountsDto.setRoamingCsVoiceCallSeconds(prepaidRoamingAccountsDb.getRoamingCsVoiceCallSeconds());
			prepaidRoamingAccountsDto.setRoaming4gDataOctets(prepaidRoamingAccountsDb.getRoaming4gDataOctets());
			prepaidRoamingAccountsDto.setRoaming5gDataOctets(prepaidRoamingAccountsDb.getRoaming5gDataOctets());
			prepaidRoamingAccountsDto.setRoamingVolteCallSeconds(prepaidRoamingAccountsDb.getRoamingVolteCallSeconds());
			prepaidRoamingAccountsDto.setRoamingTotalDataOctetsAvailable(prepaidRoamingAccountsDb.getRoamingTotalDataOctetsAvailable());
			prepaidRoamingAccountsDto.setRoamingTotalDataOctetsConsumed(prepaidRoamingAccountsDb.getRoamingTotalDataOctetsConsumed());
			prepaidRoamingAccountsDto.setRoamingTotalCallSecondsAvailable(prepaidRoamingAccountsDb.getRoamingTotalCallSecondsAvailable());
			prepaidRoamingAccountsDto.setRoamingTotalCallSecondsConsumed(prepaidRoamingAccountsDb.getRoamingTotalCallSecondsConsumed());
			prepaidRoamingAccountsDto.setRoamingTotalSmsAvailable(prepaidRoamingAccountsDb.getRoamingTotalSmsAvailable());
			prepaidRoamingAccountsDto.setRoamingTotalSmsConsumed(prepaidRoamingAccountsDb.getRoamingTotalSmsConsumed());
			prepaidRoamingAccountsDtoList.add(prepaidRoamingAccountsDto);
		}
		return prepaidRoamingAccountsDtoList;
	}
}
