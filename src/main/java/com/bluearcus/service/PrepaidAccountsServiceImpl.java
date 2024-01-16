package com.bluearcus.service;

import com.bluearcus.dto.DeductionDto;
import com.bluearcus.dto.PrepaidAccountsDto;
import com.bluearcus.dto.PrepaidAvailBalanceDto;
import com.bluearcus.exception.CustomMessage;
import com.bluearcus.model.PrepaidAccounts;
import com.bluearcus.repo.PrepaidAccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PrepaidAccountsServiceImpl implements PrepaidAccountsService {
	@Autowired
	private PrepaidAccountsRepository prepaidAccountsRepository;

	@Override
	public ResponseEntity savePrepaidAccount(PrepaidAccountsDto prepaidAccountsDto) {
		Optional<PrepaidAccounts> prepaidAccount = prepaidAccountsRepository
				.findByAccountId(prepaidAccountsDto.getAccountId() != null ? prepaidAccountsDto.getAccountId() : 0);
		if (!prepaidAccount.isPresent()) {
			PrepaidAccounts prepaidAccountDb = new PrepaidAccounts();
			prepaidAccountDb.setCustomerId(prepaidAccountsDto.getCustomerId() != null ? prepaidAccountsDto.getCustomerId() : Integer.valueOf(""));
			prepaidAccountDb.setMsisdn(prepaidAccountsDto.getMsisdn() != null ? prepaidAccountsDto.getMsisdn() : "");
			prepaidAccountDb.setImsi(prepaidAccountsDto.getImsi() != null ? prepaidAccountsDto.getImsi() : "");
			prepaidAccountDb.setCsVoiceCallSeconds(convertMinsToSeconds(prepaidAccountsDto.getCsVoiceCallSeconds()));
			prepaidAccountDb.setFourGDataOctets(prepaidAccountsDto.getFourGDataOctets() != null ? prepaidAccountsDto.getFourGDataOctets() : Integer.valueOf(""));
			prepaidAccountDb.setFiveGDataOctets(prepaidAccountsDto.getFiveGDataOctets() != null ? prepaidAccountsDto.getFiveGDataOctets() : Integer.valueOf(""));
			prepaidAccountDb.setVolteCallSeconds(convertMinsToSeconds(prepaidAccountsDto.getVolteCallSeconds()));
			prepaidAccountDb.setTotalDataOctetsAvailable(convertGigabytesToBytes(prepaidAccountsDto.getTotalDataOctetsAvailable()));
			prepaidAccountDb.setTotalInputDataOctetsAvailable(convertGigabytesToBytes(prepaidAccountsDto.getTotalInputDataOctetsAvailable()));
			prepaidAccountDb.setTotalOutputDataOctetsAvailable(convertGigabytesToBytes(prepaidAccountsDto.getTotalOutputDataOctetsAvailable()));
			prepaidAccountDb.setTotalDataOctetsConsumed(convertGigabytesToBytes(prepaidAccountsDto.getTotalDataOctetsConsumed()));
			prepaidAccountDb.setTotalCallSecondsAvailable(convertMinsToSeconds(prepaidAccountsDto.getTotalCallSecondsAvailable()));
			prepaidAccountDb.setTotalCallSecondsConsumed(convertMinsToSeconds(prepaidAccountsDto.getTotalCallSecondsConsumed()));
			prepaidAccountDb.setTotalSmsAvailable(prepaidAccountsDto.getTotalSmsAvailable());
			prepaidAccountDb.setTotalSmsConsumed(prepaidAccountsDto.getTotalSmsConsumed());
			prepaidAccountsRepository.save(prepaidAccountDb);
			PrepaidAccountsDto prepaidAccountsDtoNew = new PrepaidAccountsDto(prepaidAccountDb.getAccountId(),
					prepaidAccountDb.getCustomerId(), prepaidAccountDb.getMsisdn(), prepaidAccountDb.getImsi(),
					prepaidAccountDb.getCsVoiceCallSeconds(), prepaidAccountDb.getFourGDataOctets(),
					prepaidAccountDb.getFiveGDataOctets(), prepaidAccountDb.getVolteCallSeconds(),
					prepaidAccountDb.getTotalDataOctetsAvailable(), prepaidAccountDb.getTotalInputDataOctetsAvailable(),
					prepaidAccountDb.getTotalOutputDataOctetsAvailable(), prepaidAccountDb.getTotalDataOctetsConsumed(),
					prepaidAccountDb.getTotalCallSecondsAvailable(), prepaidAccountDb.getTotalCallSecondsConsumed(),
					prepaidAccountDb.getTotalSmsAvailable(), prepaidAccountDb.getTotalSmsConsumed());
			return new ResponseEntity(prepaidAccountsDtoNew, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new CustomMessage(HttpStatus.CONFLICT.value(), "Account Id already exist"));
	}

	@Override
	public ResponseEntity saveDeductionRecord(DeductionDto deductionDto) {
		Optional<PrepaidAccounts> prepaidAccountsDb = prepaidAccountsRepository.findByImsi(deductionDto.getImsi() != null ? deductionDto.getImsi() : String.valueOf(0));
		if (prepaidAccountsDb.isPresent()) {
			PrepaidAccounts prepaidAccounts = prepaidAccountsDb.get();
			
			if (deductionDto.getConsumedTimeSeconds() != 0) {
				Long availableCallSeconds = prepaidAccounts.getTotalCallSecondsAvailable();
				Long consumedCallSeconds = convertMinsToSeconds(deductionDto.getConsumedTimeSeconds());
				Long remainingCallSeconds = availableCallSeconds - consumedCallSeconds;
				
				prepaidAccounts.setTotalCallSecondsAvailable(remainingCallSeconds);
				prepaidAccounts.setTotalCallSecondsConsumed(prepaidAccounts.getTotalCallSecondsConsumed() + consumedCallSeconds);
			}
			
			if (deductionDto.getConsumedSms() != 0) {
				Long totalSms = prepaidAccounts.getTotalSmsAvailable();
				Long consumedSms = deductionDto.getConsumedSms();
				Long remainingSms = totalSms - consumedSms;
				prepaidAccounts.setTotalSmsAvailable(remainingSms);
				prepaidAccounts.setTotalSmsConsumed(prepaidAccounts.getTotalSmsConsumed() + consumedSms);
			}
			
			if (deductionDto.getConsumedOctets().getInput() != 0) {
				Long availableBalance = prepaidAccounts.getTotalDataOctetsAvailable();
				Long consumedBalance = convertGigabytesToBytes(deductionDto.getConsumedOctets().getInput());
				Long outputBalance = availableBalance - consumedBalance;
				System.out.println(outputBalance);
				prepaidAccounts.setTotalDataOctetsAvailable(outputBalance);
				prepaidAccounts.setTotalInputDataOctetsAvailable(prepaidAccounts.getTotalInputDataOctetsAvailable() + convertGigabytesToBytes(deductionDto.getConsumedOctets().getInput()));
				prepaidAccounts.setTotalOutputDataOctetsAvailable(outputBalance);
				prepaidAccounts.setTotalDataOctetsConsumed(prepaidAccounts.getTotalInputDataOctetsAvailable());
			}
			
			
			prepaidAccountsRepository.save(prepaidAccounts);

			PrepaidAccountsDto prepaidAccountsDto = new PrepaidAccountsDto(prepaidAccounts.getAccountId(),
					prepaidAccounts.getCustomerId(), prepaidAccounts.getMsisdn(), prepaidAccounts.getImsi(),
					prepaidAccounts.getCsVoiceCallSeconds(), prepaidAccounts.getFourGDataOctets(),
					prepaidAccounts.getFiveGDataOctets(), prepaidAccounts.getVolteCallSeconds(),
					prepaidAccounts.getTotalDataOctetsAvailable(), prepaidAccounts.getTotalInputDataOctetsAvailable(),
					prepaidAccounts.getTotalOutputDataOctetsAvailable(), prepaidAccounts.getTotalDataOctetsConsumed(),
					prepaidAccounts.getTotalCallSecondsAvailable(), prepaidAccounts.getTotalCallSecondsConsumed(),
					prepaidAccounts.getTotalSmsAvailable(), prepaidAccounts.getTotalSmsConsumed());

			return new ResponseEntity<>(prepaidAccountsDto, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomMessage(HttpStatus.CONFLICT.value(), "Invalid MSISDN Id"));
	}

	@Override
	public ResponseEntity editPrepaidAccount(Integer accountId, PrepaidAccountsDto prepaidAccountsDto) {
		Optional<PrepaidAccounts> prepaidAccount = prepaidAccountsRepository.findByAccountId(accountId);
		if (prepaidAccount.isPresent()) {
			PrepaidAccounts prepaidAccountDb = prepaidAccount.get();
			prepaidAccountDb
					.setCustomerId(prepaidAccountsDto.getCustomerId() != null ? prepaidAccountsDto.getCustomerId()
							: prepaidAccountDb.getCustomerId());
			prepaidAccountDb.setMsisdn(prepaidAccountsDto.getMsisdn() != null ? prepaidAccountsDto.getMsisdn()
					: prepaidAccountDb.getMsisdn());
			prepaidAccountDb.setImsi(
					prepaidAccountsDto.getImsi() != null ? prepaidAccountsDto.getImsi() : prepaidAccountDb.getImsi());
			prepaidAccountDb.setCsVoiceCallSeconds(
					prepaidAccountsDto.getCsVoiceCallSeconds() != null ? prepaidAccountsDto.getCsVoiceCallSeconds()
							: prepaidAccountDb.getCsVoiceCallSeconds());
			prepaidAccountDb.setFourGDataOctets(
					prepaidAccountsDto.getFourGDataOctets() != null ? prepaidAccountsDto.getFourGDataOctets()
							: prepaidAccountDb.getFourGDataOctets());
			prepaidAccountDb.setFiveGDataOctets(
					prepaidAccountsDto.getFiveGDataOctets() != null ? prepaidAccountsDto.getFiveGDataOctets()
							: prepaidAccountDb.getFiveGDataOctets());
			prepaidAccountDb.setVolteCallSeconds(
					prepaidAccountsDto.getVolteCallSeconds() != null ? prepaidAccountsDto.getVolteCallSeconds()
							: prepaidAccountDb.getVolteCallSeconds());
			prepaidAccountDb.setTotalDataOctetsAvailable(prepaidAccountsDto.getTotalDataOctetsAvailable() != null
					? prepaidAccountsDto.getTotalDataOctetsAvailable()
					: prepaidAccountDb.getTotalDataOctetsAvailable());
			prepaidAccountDb
					.setTotalInputDataOctetsAvailable(prepaidAccountsDto.getTotalInputDataOctetsAvailable() != null
							? prepaidAccountsDto.getTotalInputDataOctetsAvailable()
							: prepaidAccountDb.getTotalInputDataOctetsAvailable());
			prepaidAccountDb
					.setTotalOutputDataOctetsAvailable(prepaidAccountsDto.getTotalOutputDataOctetsAvailable() != null
							? prepaidAccountsDto.getTotalOutputDataOctetsAvailable()
							: prepaidAccountDb.getTotalOutputDataOctetsAvailable());
			prepaidAccountDb.setTotalDataOctetsConsumed(prepaidAccountsDto.getTotalDataOctetsConsumed() != null
					? prepaidAccountsDto.getTotalDataOctetsConsumed()
					: prepaidAccountDb.getTotalDataOctetsConsumed());
			prepaidAccountDb.setTotalCallSecondsAvailable(prepaidAccountsDto.getTotalCallSecondsAvailable() != null
					? prepaidAccountsDto.getTotalCallSecondsAvailable()
					: prepaidAccountDb.getTotalCallSecondsAvailable());
			prepaidAccountDb.setTotalCallSecondsConsumed(prepaidAccountsDto.getTotalCallSecondsConsumed() != null
					? prepaidAccountsDto.getTotalCallSecondsConsumed()
					: prepaidAccountDb.getTotalCallSecondsConsumed());
			prepaidAccountDb.setTotalSmsAvailable(
					prepaidAccountsDto.getTotalSmsAvailable() != null ? prepaidAccountsDto.getTotalSmsAvailable()
							: prepaidAccountDb.getTotalSmsAvailable());
			prepaidAccountDb.setTotalSmsConsumed(
					prepaidAccountsDto.getTotalSmsConsumed() != null ? prepaidAccountsDto.getTotalSmsConsumed()
							: prepaidAccountDb.getTotalSmsConsumed());
			prepaidAccountsRepository.save(prepaidAccountDb);
			PrepaidAccountsDto prepaidAccountsDtoNew = new PrepaidAccountsDto(prepaidAccountDb.getAccountId(),
					prepaidAccountDb.getCustomerId(), prepaidAccountDb.getMsisdn(), prepaidAccountDb.getImsi(),
					prepaidAccountDb.getCsVoiceCallSeconds(), prepaidAccountDb.getFourGDataOctets(),
					prepaidAccountDb.getFiveGDataOctets(), prepaidAccountDb.getVolteCallSeconds(),
					prepaidAccountDb.getTotalDataOctetsAvailable(), prepaidAccountDb.getTotalInputDataOctetsAvailable(),
					prepaidAccountDb.getTotalOutputDataOctetsAvailable(), prepaidAccountDb.getTotalDataOctetsConsumed(),
					prepaidAccountDb.getTotalCallSecondsAvailable(), prepaidAccountDb.getTotalCallSecondsConsumed(),
					prepaidAccountDb.getTotalSmsAvailable(), prepaidAccountDb.getTotalSmsConsumed());
			return new ResponseEntity(prepaidAccountsDtoNew, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid account Id"));
	}

	@Transactional
	@Override
	public ResponseEntity deletePrepaidAccount(Integer accountId) {
		Optional<PrepaidAccounts> prepaidAccounts = prepaidAccountsRepository.findByAccountId(accountId);
		if (prepaidAccounts.isPresent()) {
			prepaidAccountsRepository.deleteById(accountId);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new CustomMessage(HttpStatus.OK.value(), "Deleted Successfully"));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid Account Id"));
	}

	@Override
	public ResponseEntity getPrepaidAccount(Integer accountId) {
		Optional<PrepaidAccounts> prepaidAccounts = prepaidAccountsRepository.findByAccountId(accountId);
		if (prepaidAccounts.isPresent()) {
			PrepaidAccounts prepaidAccountsDb = prepaidAccounts.get();
			PrepaidAccountsDto prepaidAccountsDto = new PrepaidAccountsDto();
			prepaidAccountsDto.setAccountId(prepaidAccountsDb.getAccountId());
			prepaidAccountsDto.setCustomerId(prepaidAccountsDb.getCustomerId());
			prepaidAccountsDto.setMsisdn(prepaidAccountsDb.getMsisdn());
			prepaidAccountsDto.setImsi(prepaidAccountsDb.getImsi());
			prepaidAccountsDto.setCsVoiceCallSeconds(prepaidAccountsDb.getCsVoiceCallSeconds());
			prepaidAccountsDto.setFourGDataOctets(prepaidAccountsDb.getFourGDataOctets());
			prepaidAccountsDto.setFiveGDataOctets(prepaidAccountsDb.getFiveGDataOctets());
			prepaidAccountsDto.setVolteCallSeconds(prepaidAccountsDb.getVolteCallSeconds());
			prepaidAccountsDto.setTotalDataOctetsAvailable(prepaidAccountsDb.getTotalDataOctetsAvailable());
			prepaidAccountsDto.setTotalInputDataOctetsAvailable(prepaidAccountsDb.getTotalInputDataOctetsAvailable());
			prepaidAccountsDto.setTotalOutputDataOctetsAvailable(prepaidAccountsDb.getTotalOutputDataOctetsAvailable());
			prepaidAccountsDto.setTotalDataOctetsConsumed(prepaidAccountsDb.getTotalDataOctetsConsumed());
			prepaidAccountsDto.setTotalCallSecondsAvailable(prepaidAccountsDb.getTotalCallSecondsAvailable());
			prepaidAccountsDto.setTotalCallSecondsConsumed(prepaidAccountsDb.getTotalCallSecondsConsumed());
			prepaidAccountsDto.setTotalSmsAvailable(prepaidAccountsDb.getTotalSmsAvailable());
			prepaidAccountsDto.setTotalSmsConsumed(prepaidAccountsDb.getTotalSmsConsumed());
			return new ResponseEntity<>(prepaidAccountsDto, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid Account Id"));
	}

	@Override
	public ResponseEntity getAvailableBalance(String imsi) {
		Optional<PrepaidAccounts> prepaidAccounts = prepaidAccountsRepository.findByImsi(imsi);
		if (prepaidAccounts.isPresent()) {
			PrepaidAccounts prepaidAccountsDb = prepaidAccounts.get();
			PrepaidAvailBalanceDto prepaidAvailBalanceDto = new PrepaidAvailBalanceDto();
			prepaidAvailBalanceDto.setTotalDataOctetsAvailable(prepaidAccountsDb.getTotalDataOctetsAvailable());
			prepaidAvailBalanceDto.setTotalInputDataOctetsAvailable(prepaidAccountsDb.getTotalInputDataOctetsAvailable());
			prepaidAvailBalanceDto.setTotalOutputDataOctetsAvailable(prepaidAccountsDb.getTotalOutputDataOctetsAvailable());
			prepaidAvailBalanceDto.setTotalCallSecondsAvailable(prepaidAccountsDb.getTotalCallSecondsAvailable());
			prepaidAvailBalanceDto.setTotalSmsAvailable(prepaidAccountsDb.getTotalSmsAvailable());
			return new ResponseEntity<>(prepaidAvailBalanceDto, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid Account Id"));
	}

	public static long convertGigabytesToBytes(Long gigabytes) {
		// 1 GB = 1024^3 bytes
		BigDecimal gigabytesBigDecimal = new BigDecimal(String.valueOf(gigabytes));
		BigDecimal bytesBigDecimal = gigabytesBigDecimal.multiply(BigDecimal.valueOf(Math.pow(1024, 3)));
		return bytesBigDecimal.longValue();
	}

	public static long convertMinsToSeconds(Long mins) {
		// 1 Min = 60^1 seconds
		BigDecimal gigabytesBigDecimal = new BigDecimal(String.valueOf(mins));
		BigDecimal bytesBigDecimal = gigabytesBigDecimal.multiply(BigDecimal.valueOf(Math.pow(60, 1)));
		return bytesBigDecimal.longValue();
	}
}
