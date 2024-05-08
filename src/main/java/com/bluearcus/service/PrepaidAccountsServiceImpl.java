package com.bluearcus.service;

import com.bluearcus.dto.DeductionDto;
import com.bluearcus.dto.PrepaidAccountRecordsDto;
import com.bluearcus.dto.PrepaidAccountsDto;
import com.bluearcus.dto.PrepaidAvailBalanceDto;
import com.bluearcus.exception.CustomMessage;
import com.bluearcus.model.AuditLogsDeductionPrepaid;
import com.bluearcus.model.PrepaidAccounts;
import com.bluearcus.repo.AuditLogsDeductionPrepaidRepo;
import com.bluearcus.repo.PrepaidAccountsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PrepaidAccountsServiceImpl implements PrepaidAccountsService {
	@Autowired
	private PrepaidAccountsRepository prepaidAccountsRepository;
	
	@Autowired
	private PrepaidFlatFileService prepaidFlatFileService;
	
	@Autowired
	private AuditLogsDeductionPrepaidRepo auditLogsPrepaidRepo;

	@Override
	public ResponseEntity savePrepaidAccount(PrepaidAccountsDto prepaidAccountsDto) {
		Optional<PrepaidAccounts> prepaidAccount = prepaidAccountsRepository.findByImsi(prepaidAccountsDto.getImsi());
		if (!prepaidAccount.isPresent()) {
			PrepaidAccounts prepaidAccountDb = new PrepaidAccounts();
			prepaidAccountDb.setCustomerId(prepaidAccountsDto.getCustomerId() != null ? prepaidAccountsDto.getCustomerId() : Integer.valueOf(""));
			prepaidAccountDb.setMsisdn(prepaidAccountsDto.getMsisdn() != null ? prepaidAccountsDto.getMsisdn() : "");
			prepaidAccountDb.setImsi(prepaidAccountsDto.getImsi() != null ? prepaidAccountsDto.getImsi() : "");
			prepaidAccountDb.setCalledStationId("");
			prepaidAccountDb.setMonitoringKey("");
			prepaidAccountDb.setAction("");
			prepaidAccountDb.setDataParameterType(prepaidAccountsDto.getDataParameterType() != null ? prepaidAccountsDto.getDataParameterType() : "");
			prepaidAccountDb.setCsVoiceCallSeconds(convertMinsToSeconds(prepaidAccountsDto.getCsVoiceCallSeconds()));
			prepaidAccountDb.setFourGDataOctets(prepaidAccountsDto.getFourGDataOctets() != null ? prepaidAccountsDto.getFourGDataOctets() : Integer.valueOf(""));
			prepaidAccountDb.setFiveGDataOctets(prepaidAccountsDto.getFiveGDataOctets() != null ? prepaidAccountsDto.getFiveGDataOctets() : Integer.valueOf(""));
			prepaidAccountDb.setVolteCallSeconds(convertMinsToSeconds(prepaidAccountsDto.getVolteCallSeconds()));
			
			if (prepaidAccountsDto.getDataParameterType().equalsIgnoreCase("GB")) {
				prepaidAccountDb.setTotalDataOctetsAvailable(convertGigabytesToBytes(prepaidAccountsDto.getTotalDataOctetsAvailable()));
			}

			if (prepaidAccountsDto.getDataParameterType().equalsIgnoreCase("MB")) {
				prepaidAccountDb.setTotalDataOctetsAvailable(convertMegabytesToBytes(prepaidAccountsDto.getTotalDataOctetsAvailable()));
			}

			if (prepaidAccountsDto.getDataParameterType().equalsIgnoreCase("KB")) {
				prepaidAccountDb.setTotalDataOctetsAvailable(convertKilobytesToBytes(prepaidAccountsDto.getTotalDataOctetsAvailable()));
			}
			
			if (prepaidAccountsDto.getDataParameterType().equals("")) {
				prepaidAccountDb.setTotalDataOctetsAvailable(prepaidAccountsDto.getTotalDataOctetsAvailable());
			}
			
			prepaidAccountDb.setTotalInputDataOctetsAvailable(prepaidAccountsDto.getTotalInputDataOctetsAvailable());
			prepaidAccountDb.setTotalOutputDataOctetsAvailable(prepaidAccountsDto.getTotalOutputDataOctetsAvailable());
			prepaidAccountDb.setTotalDataOctetsConsumed(prepaidAccountsDto.getTotalDataOctetsConsumed());
			prepaidAccountDb.setTotalCallSecondsAvailable(convertMinsToSeconds(prepaidAccountsDto.getTotalCallSecondsAvailable()));
			prepaidAccountDb.setTotalCallSecondsConsumed(prepaidAccountsDto.getTotalCallSecondsConsumed());
			prepaidAccountDb.setTotalSmsAvailable(prepaidAccountsDto.getTotalSmsAvailable());
			prepaidAccountDb.setTotalSmsConsumed(prepaidAccountsDto.getTotalSmsConsumed());
			prepaidAccountsRepository.save(prepaidAccountDb);
			PrepaidAccountsDto prepaidAccountsDtoNew = new PrepaidAccountsDto(prepaidAccountDb.getAccountId(),
					prepaidAccountDb.getCustomerId(), prepaidAccountDb.getMsisdn(), prepaidAccountDb.getImsi(),
					prepaidAccountDb.getCalledStationId(), prepaidAccountDb.getMonitoringKey(),
					prepaidAccountDb.getAction(), prepaidAccountDb.getDataParameterType(),
					prepaidAccountDb.getCsVoiceCallSeconds(), prepaidAccountDb.getFourGDataOctets(),
					prepaidAccountDb.getFiveGDataOctets(), prepaidAccountDb.getVolteCallSeconds(),
					prepaidAccountDb.getTotalDataOctetsAvailable(), prepaidAccountDb.getTotalInputDataOctetsAvailable(),
					prepaidAccountDb.getTotalOutputDataOctetsAvailable(), prepaidAccountDb.getTotalDataOctetsConsumed(),
					prepaidAccountDb.getTotalCallSecondsAvailable(), prepaidAccountDb.getTotalCallSecondsConsumed(),
					prepaidAccountDb.getTotalSmsAvailable(), prepaidAccountDb.getTotalSmsConsumed());

			return new ResponseEntity(prepaidAccountsDtoNew, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomMessage(HttpStatus.CONFLICT.value(), "IMSI already exist"));
	}

	@Override
	public ResponseEntity savePrepaidDeduction(DeductionDto deductionDto) throws JsonProcessingException {
		Optional<PrepaidAccounts> prepaidAccountsDb = prepaidAccountsRepository.findByImsi(deductionDto.getImsi());
		if (prepaidAccountsDb.isPresent()) {
			PrepaidAccounts prepaidAccounts = prepaidAccountsDb.get();
			
			prepaidAccounts.setCalledStationId(deductionDto.getCalledStationId() != null ? deductionDto.getCalledStationId() : "");
			
			if (deductionDto.getMonitoringKey().equalsIgnoreCase("Internet")) {
				prepaidAccounts.setMonitoringKey("Data");
			}
			
			if (deductionDto.getMonitoringKey().equalsIgnoreCase("Ims")) {
				prepaidAccounts.setMonitoringKey("Call");
			}
			
			if (deductionDto.getMonitoringKey().equalsIgnoreCase("Sms")) {
				prepaidAccounts.setMonitoringKey("Sms");
			}
			
			prepaidAccounts.setAction(deductionDto.getAction() != null ? deductionDto.getAction() : "");
			
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
				Long consumedBalance = deductionDto.getConsumedOctets().getInput();
				Long outputBalance = availableBalance - consumedBalance;                                                
				prepaidAccounts.setTotalDataOctetsAvailable(outputBalance);
				prepaidAccounts.setTotalInputDataOctetsAvailable(prepaidAccounts.getTotalInputDataOctetsAvailable() + deductionDto.getConsumedOctets().getInput());
				prepaidAccounts.setTotalOutputDataOctetsAvailable(outputBalance);
				prepaidAccounts.setTotalDataOctetsConsumed(prepaidAccounts.getTotalInputDataOctetsAvailable());
			}
			
			
			prepaidAccountsRepository.save(prepaidAccounts);

			PrepaidAccountsDto prepaidAccountsDto = new PrepaidAccountsDto(prepaidAccounts.getAccountId(),
					prepaidAccounts.getCustomerId(), prepaidAccounts.getMsisdn(), prepaidAccounts.getImsi(),
					prepaidAccounts.getCalledStationId(), prepaidAccounts.getMonitoringKey(),
					prepaidAccounts.getAction(), prepaidAccounts.getDataParameterType(),
					prepaidAccounts.getCsVoiceCallSeconds(), prepaidAccounts.getFourGDataOctets(),
					prepaidAccounts.getFiveGDataOctets(), prepaidAccounts.getVolteCallSeconds(),
					prepaidAccounts.getTotalDataOctetsAvailable(), prepaidAccounts.getTotalInputDataOctetsAvailable(),
					prepaidAccounts.getTotalOutputDataOctetsAvailable(), prepaidAccounts.getTotalDataOctetsConsumed(),
					prepaidAccounts.getTotalCallSecondsAvailable(), prepaidAccounts.getTotalCallSecondsConsumed(),
					prepaidAccounts.getTotalSmsAvailable(), prepaidAccounts.getTotalSmsConsumed());

			ObjectMapper objectMapper = new ObjectMapper();
			String customerData = objectMapper.writeValueAsString(prepaidAccountsDto);
			String date = new Date().toInstant().toString();
			
			// Storing data for Logs...
			AuditLogsDeductionPrepaid auditLogsPrepaid = new AuditLogsDeductionPrepaid();
			auditLogsPrepaid.setCreatedDate(new Date());
			auditLogsPrepaid.setReqPayload(customerData);
			auditLogsPrepaid.setMsisdn(prepaidAccountsDto.getMsisdn());
			
			auditLogsPrepaidRepo.save(auditLogsPrepaid);
			
			//Storing data for Flat file...
			prepaidFlatFileService.storeUserData(prepaidAccounts.getMonitoringKey(), date, prepaidAccountsDto.getMsisdn(), customerData);
			
			return new ResponseEntity<>(prepaidAccountsDto, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid IMSI"));
	}

	@Override
	public ResponseEntity editPrepaidAccount(Integer accountId, PrepaidAccountsDto prepaidAccountsDto) {
		Optional<PrepaidAccounts> prepaidAccount = prepaidAccountsRepository.findByAccountId(accountId);
		if (prepaidAccount.isPresent()) {
			PrepaidAccounts prepaidAccountDb = prepaidAccount.get();
			prepaidAccountDb.setCustomerId(prepaidAccountsDto.getCustomerId() != null ? prepaidAccountsDto.getCustomerId() : prepaidAccountDb.getCustomerId());
			prepaidAccountDb.setMsisdn(prepaidAccountsDto.getMsisdn() != null ? prepaidAccountsDto.getMsisdn() : prepaidAccountDb.getMsisdn());
			prepaidAccountDb.setImsi(prepaidAccountsDto.getImsi() != null ? prepaidAccountsDto.getImsi() : prepaidAccountDb.getImsi());
			prepaidAccountDb.setDataParameterType(prepaidAccountsDto.getDataParameterType() != null ? prepaidAccountsDto.getDataParameterType() : prepaidAccountDb.getDataParameterType());
			prepaidAccountDb.setCsVoiceCallSeconds(prepaidAccountsDto.getCsVoiceCallSeconds() != null ? prepaidAccountsDto.getCsVoiceCallSeconds() : prepaidAccountDb.getCsVoiceCallSeconds());
			prepaidAccountDb.setFourGDataOctets(prepaidAccountsDto.getFourGDataOctets() != null ? prepaidAccountsDto.getFourGDataOctets() : prepaidAccountDb.getFourGDataOctets());
			prepaidAccountDb.setFiveGDataOctets(prepaidAccountsDto.getFiveGDataOctets() != null ? prepaidAccountsDto.getFiveGDataOctets() : prepaidAccountDb.getFiveGDataOctets());
			prepaidAccountDb.setVolteCallSeconds(prepaidAccountsDto.getVolteCallSeconds() != null ? prepaidAccountsDto.getVolteCallSeconds() : prepaidAccountDb.getVolteCallSeconds());
			prepaidAccountDb.setTotalDataOctetsAvailable(prepaidAccountsDto.getTotalDataOctetsAvailable() != null ? prepaidAccountsDto.getTotalDataOctetsAvailable() : prepaidAccountDb.getTotalDataOctetsAvailable());
			prepaidAccountDb.setTotalInputDataOctetsAvailable(prepaidAccountsDto.getTotalInputDataOctetsAvailable() != null ? prepaidAccountsDto.getTotalInputDataOctetsAvailable() : prepaidAccountDb.getTotalInputDataOctetsAvailable());
			prepaidAccountDb.setTotalOutputDataOctetsAvailable(prepaidAccountsDto.getTotalOutputDataOctetsAvailable() != null ? prepaidAccountsDto.getTotalOutputDataOctetsAvailable() : prepaidAccountDb.getTotalOutputDataOctetsAvailable());
			prepaidAccountDb.setTotalDataOctetsConsumed(prepaidAccountsDto.getTotalDataOctetsConsumed() != null ? prepaidAccountsDto.getTotalDataOctetsConsumed() : prepaidAccountDb.getTotalDataOctetsConsumed());
			prepaidAccountDb.setTotalCallSecondsAvailable(prepaidAccountsDto.getTotalCallSecondsAvailable() != null ? prepaidAccountsDto.getTotalCallSecondsAvailable() : prepaidAccountDb.getTotalCallSecondsAvailable());
			prepaidAccountDb.setTotalCallSecondsConsumed(prepaidAccountsDto.getTotalCallSecondsConsumed() != null ? prepaidAccountsDto.getTotalCallSecondsConsumed() : prepaidAccountDb.getTotalCallSecondsConsumed());
			prepaidAccountDb.setTotalSmsAvailable(prepaidAccountsDto.getTotalSmsAvailable() != null ? prepaidAccountsDto.getTotalSmsAvailable() : prepaidAccountDb.getTotalSmsAvailable());
			prepaidAccountDb.setTotalSmsConsumed(prepaidAccountsDto.getTotalSmsConsumed() != null ? prepaidAccountsDto.getTotalSmsConsumed() : prepaidAccountDb.getTotalSmsConsumed());
			prepaidAccountsRepository.save(prepaidAccountDb);
			PrepaidAccountsDto prepaidAccountsDtoNew = new PrepaidAccountsDto(prepaidAccountDb.getAccountId(),
					prepaidAccountDb.getCustomerId(), prepaidAccountDb.getMsisdn(), prepaidAccountDb.getImsi(),
					prepaidAccountDb.getCalledStationId(), prepaidAccountDb.getMonitoringKey(),
					prepaidAccountDb.getAction(), prepaidAccountDb.getDataParameterType(),
					prepaidAccountDb.getCsVoiceCallSeconds(), prepaidAccountDb.getFourGDataOctets(),
					prepaidAccountDb.getFiveGDataOctets(), prepaidAccountDb.getVolteCallSeconds(),
					prepaidAccountDb.getTotalDataOctetsAvailable(), prepaidAccountDb.getTotalInputDataOctetsAvailable(),
					prepaidAccountDb.getTotalOutputDataOctetsAvailable(), prepaidAccountDb.getTotalDataOctetsConsumed(),
					prepaidAccountDb.getTotalCallSecondsAvailable(), prepaidAccountDb.getTotalCallSecondsConsumed(),
					prepaidAccountDb.getTotalSmsAvailable(), prepaidAccountDb.getTotalSmsConsumed());
			return new ResponseEntity(prepaidAccountsDtoNew, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid account Id"));
	}

	@Transactional
	@Override
	public ResponseEntity deletePrepaidAccount(Integer accountId) {
		Optional<PrepaidAccounts> prepaidAccounts = prepaidAccountsRepository.findByAccountId(accountId);
		if (prepaidAccounts.isPresent()) {
			prepaidAccountsRepository.deleteById(accountId);
			return ResponseEntity.status(HttpStatus.OK).body(new CustomMessage(HttpStatus.OK.value(), "Deleted Successfully"));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid Account Id"));
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
			prepaidAccountsDto.setDataParameterType(prepaidAccountsDb.getDataParameterType());
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
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid Account Id"));
	}

	@Override
	public ResponseEntity getAvailableBalance(String imsi, String msisdn) throws URISyntaxException {
		Optional<PrepaidAccounts> prepaidAccounts = prepaidAccountsRepository.findByImsiOrMsisdn(imsi, msisdn);
		if (prepaidAccounts.isPresent()) {
			PrepaidAccounts prepaidAccountsDb = prepaidAccounts.get();
			if (prepaidAccountsDb.getTotalDataOctetsAvailable() != 0 && prepaidAccountsDb.getTotalCallSecondsAvailable() != 0 && prepaidAccountsDb.getTotalSmsAvailable() != 0) {
				PrepaidAvailBalanceDto prepaidAvailBalanceDto = new PrepaidAvailBalanceDto();
				prepaidAvailBalanceDto.setTotalDataOctetsAvailable(prepaidAccountsDb.getTotalDataOctetsAvailable());
				prepaidAvailBalanceDto.setTotalInputDataOctetsAvailable(prepaidAccountsDb.getTotalInputDataOctetsAvailable());
				prepaidAvailBalanceDto.setTotalOutputDataOctetsAvailable(prepaidAccountsDb.getTotalOutputDataOctetsAvailable());
				prepaidAvailBalanceDto.setTotalCallSecondsAvailable(prepaidAccountsDb.getTotalCallSecondsAvailable());
				prepaidAvailBalanceDto.setTotalSmsAvailable(prepaidAccountsDb.getTotalSmsAvailable());
				return new ResponseEntity<>(prepaidAvailBalanceDto, HttpStatus.OK);
			}
			// Calling an API to disable outgoing calls,sms & data...
			String url = "http://localhost:9697/api/hss/detail/restriction/subscriber/services/" + prepaidAccountsDb.getImsi();
			
			String requestBody = "{\"outgoing-calls\":false,\"incoming-calls\":true,\"outgoing-sms\":false,\"incoming-sms\":true}";
			
			RestTemplate restTemplate = new RestTemplate();
			
			HttpHeaders headers = new HttpHeaders();
		    headers.add("Content-Type", "application/json");
		    headers.add("Accept", "*/*");
		    
		    HttpEntity<String> requestEntity = new HttpEntity<String>(requestBody, headers);
		    
			ResponseEntity<String> apiResponse = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

//			URI uri = new URI("recharge_notification.html");
//			HttpHeaders httpHeaders = new HttpHeaders();
//			httpHeaders.setLocation(uri);

			return ResponseEntity.status(HttpStatus.OK).body(new CustomMessage(HttpStatus.OK.value(), "Please recharge to continue services..."));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid IMSI Or MSISDN"));
	}
	
	@Override
	public List<PrepaidAccountsDto> getAllPrepaidAccounts() {
		List<PrepaidAccounts> prepaidAccountList = prepaidAccountsRepository.findAll();
		List<PrepaidAccountsDto> prepaidAccountsDtoList = new ArrayList<>();
		for (PrepaidAccounts prepaidAccountsDb : prepaidAccountList) {
			PrepaidAccountsDto prepaidAccountsDto = new PrepaidAccountsDto();
			prepaidAccountsDto.setAccountId(prepaidAccountsDb.getAccountId());
			prepaidAccountsDto.setCustomerId(prepaidAccountsDb.getCustomerId());
			prepaidAccountsDto.setMsisdn(prepaidAccountsDb.getMsisdn());
			prepaidAccountsDto.setImsi(prepaidAccountsDb.getImsi());
			prepaidAccountsDto.setDataParameterType(prepaidAccountsDb.getDataParameterType());
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
			prepaidAccountsDtoList.add(prepaidAccountsDto);
		}
		return prepaidAccountsDtoList;
	}
	
	@Override
	public List<PrepaidAccountRecordsDto> getAllDeductionRecords(String msisdn) throws JsonMappingException, JsonProcessingException {
		List<AuditLogsDeductionPrepaid> auditLogsDeductionPrepaids = auditLogsPrepaidRepo.findAllByMsisdnOrderByCreatedDateDesc(msisdn);
		List<PrepaidAccountRecordsDto> accountRecordList = new ArrayList<>();
		for (AuditLogsDeductionPrepaid auditLogsDeductionPrepaid : auditLogsDeductionPrepaids) {
			
			PrepaidAccountRecordsDto accountRecordsDto = new PrepaidAccountRecordsDto();
			accountRecordsDto.setDate(fetchReadableDateTime(auditLogsDeductionPrepaid.getCreatedDate()));
			accountRecordsDto.setMsisdn(auditLogsDeductionPrepaid.getMsisdn());
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			String data = auditLogsDeductionPrepaid.getReqPayload().replaceAll("\\\\", "");

			data = data.substring(1, data.length() - 1);

			PrepaidAccountsDto account = objectMapper.readValue(data,PrepaidAccountsDto.class);
			
			accountRecordsDto.setPrepaidAccountsDto(account);
			accountRecordList.add(accountRecordsDto);
		}
		return accountRecordList;
	}

	private static long convertGigabytesToBytes(Long gigaBytes) {
		// 1 GB = 1024^3 bytes
		BigDecimal gigaBytesBigDecimal = new BigDecimal(String.valueOf(gigaBytes));
		BigDecimal bytesBigDecimal = gigaBytesBigDecimal.multiply(BigDecimal.valueOf(Math.pow(1024, 3)));
		return bytesBigDecimal.longValue();
	}
	
	private static long convertMegabytesToBytes(Long megaBytes) {
		// 1 MB = 1024^2 bytes
		BigDecimal megaBytesBigDecimal = new BigDecimal(String.valueOf(megaBytes));
		BigDecimal bytesBigDecimal = megaBytesBigDecimal.multiply(BigDecimal.valueOf(Math.pow(1024, 2)));
		return bytesBigDecimal.longValue();
	}
	
	private static long convertKilobytesToBytes(Long kiloBytes) {
		// 1 KB = 1024^1 bytes
		BigDecimal kiloBytesBigDecimal = new BigDecimal(String.valueOf(kiloBytes));
		BigDecimal bytesBigDecimal = kiloBytesBigDecimal.multiply(BigDecimal.valueOf(Math.pow(1024, 1)));
		return bytesBigDecimal.longValue();
	}

	private static long convertMinsToSeconds(Long mins) {
		// 1 Min = 60^1 seconds
		BigDecimal minsBigDecimal = new BigDecimal(String.valueOf(mins));
		BigDecimal secondsBigDecimal = minsBigDecimal.multiply(BigDecimal.valueOf(Math.pow(60, 1)));
		return secondsBigDecimal.longValue();
	}
	
	public static String fetchReadableDateTime(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = simpleDateFormat.format(date);
		return formattedDate;
	}

}
