package com.bluearcus.service;

import com.bluearcus.dto.CallSessionUsageDto;
import com.bluearcus.dto.CallSessionUsageDtoNew;
import com.bluearcus.exception.CustomMessage;
import com.bluearcus.model.CallSessionUsage;
import com.bluearcus.repo.CallSessionUsageRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CallSessionUsageServiceImpl implements CallSessionUsageService {

	@Autowired
	private CallSessionUsageRepo callSessionUsageRepo;
	
	@Override
	public ResponseEntity saveCallSessionUsage(CallSessionUsageDto callSessionUsageDto) {
		CallSessionUsage callSessionUsageDb = new CallSessionUsage();
		callSessionUsageDb.setPeerSessionId(callSessionUsageDto.getPeerSessionId());
		callSessionUsageDb.setMsisdn(callSessionUsageDto.getMsisdn());
		callSessionUsageDb.setImsi(callSessionUsageDto.getImsi());
		callSessionUsageDb.setCalledMsisdn(callSessionUsageDto.getCalledMsisdn());
		callSessionUsageDb.setCallStartTs(callSessionUsageDto.getCallStartTs());
		callSessionUsageDb.setCustomerType(callSessionUsageDto.getCustomerType());
		callSessionUsageDb.setLocationInfo(callSessionUsageDto.getLocationInfo());
		callSessionUsageDb.setSessionState(callSessionUsageDto.getSessionState());
		callSessionUsageDb.setTotalSeconds(callSessionUsageDto.getTotalSeconds());
		callSessionUsageDb.setCallStatus(callSessionUsageDto.getCallStatus());
		callSessionUsageRepo.save(callSessionUsageDb);

		CallSessionUsageDtoNew callSessionUsageDtoNew = new CallSessionUsageDtoNew(callSessionUsageDb.getId(),
				callSessionUsageDb.getPeerSessionId(), callSessionUsageDb.getMsisdn(), callSessionUsageDb.getImsi(),
				callSessionUsageDb.getCalledMsisdn(), callSessionUsageDb.getCustomerType(),
				callSessionUsageDb.getLocationInfo(), callSessionUsageDb.getSessionState(),
				callSessionUsageDb.getCallStartTs(), null, callSessionUsageDb.getTotalSeconds(),
				callSessionUsageDb.getCallStatus());

		return new ResponseEntity<>(callSessionUsageDtoNew, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity updateCallSessionUsage(Boolean callStatus, CallSessionUsageDto callSessionUsageDto) {
		Optional<CallSessionUsage> callSessionUsage = callSessionUsageRepo.findByCalledMsisdnAndCallStatus(callSessionUsageDto.getCalledMsisdn(), callStatus);
		if (callSessionUsage.isPresent()) {
			CallSessionUsage callSessionUsageDb = callSessionUsage.get();
			callSessionUsageDb.setCallEndTs(callSessionUsageDto.getCallEndTs());
			callSessionUsageDb.setTotalSeconds(callSessionUsageDto.getTotalSeconds());
			callSessionUsageDb.setCallStatus(callSessionUsageDto.getCallStatus() != null ? callSessionUsageDto.getCallStatus() : callSessionUsageDb.getCallStatus());
			callSessionUsageRepo.save(callSessionUsageDb);
			
			CallSessionUsageDtoNew callSessionUsageDtoNew = new CallSessionUsageDtoNew(callSessionUsageDb.getId(),
					callSessionUsageDb.getPeerSessionId(), callSessionUsageDb.getMsisdn(), callSessionUsageDb.getImsi(),
					callSessionUsageDb.getCalledMsisdn(), callSessionUsageDb.getCustomerType(),
					callSessionUsageDb.getLocationInfo(), callSessionUsageDb.getSessionState(),
					callSessionUsageDb.getCallStartTs(), null, callSessionUsageDb.getTotalSeconds(),
					callSessionUsageDb.getCallStatus());
			
			return new ResponseEntity<>(callSessionUsageDtoNew, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Msisdn does n't exist"));
	}
	
	@Override
	public ResponseEntity fetchSecondsDuringCall(CallSessionUsageDto callSessionUsageDto) {
		if (callSessionUsageDto.getCallStatus()) {
			CallSessionUsage callSessionUsageDb = new CallSessionUsage();
			callSessionUsageDb.setPeerSessionId(callSessionUsageDto.getPeerSessionId() != null ? callSessionUsageDto.getPeerSessionId() : "");
			callSessionUsageDb.setMsisdn(callSessionUsageDto.getMsisdn() != null ? callSessionUsageDto.getMsisdn() : "");
			callSessionUsageDb.setImsi(callSessionUsageDto.getImsi() != null ? callSessionUsageDto.getImsi() : "");
			callSessionUsageDb.setCalledMsisdn(callSessionUsageDto.getCalledMsisdn() != null ? callSessionUsageDto.getCalledMsisdn() : "");
			callSessionUsageDb.setLocationInfo(callSessionUsageDto.getLocationInfo() != null ? callSessionUsageDto.getLocationInfo() : "");
			callSessionUsageDb.setSessionState(true);
			callSessionUsageDb.setCallStatus(callSessionUsageDto.getCallStatus());
			callSessionUsageRepo.save(callSessionUsageDb);

			CallSessionUsageDtoNew callSessionUsageDtoNew = new CallSessionUsageDtoNew(callSessionUsageDb.getId(),
					callSessionUsageDb.getPeerSessionId(), callSessionUsageDb.getMsisdn(), callSessionUsageDb.getImsi(),
					callSessionUsageDb.getCalledMsisdn(), callSessionUsageDb.getCustomerType(),
					callSessionUsageDb.getLocationInfo(), callSessionUsageDb.getSessionState(),
					callSessionUsageDb.getCallStartTs(), null, callSessionUsageDb.getTotalSeconds(),
					callSessionUsageDb.getCallStatus());

			return new ResponseEntity<>(callSessionUsageDtoNew, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CustomMessage(HttpStatus.FORBIDDEN.value(), "Inactive call status"));
	}

	@Override
	public List<CallSessionUsageDtoNew> getAllCallSessionUsage() {
		List<CallSessionUsageDto> callSessionUsageDbList = callSessionUsageRepo.fetchAllCallSessionUsage();
		List<CallSessionUsageDtoNew> callSessionUsageDtoList = new ArrayList<>();
		for (CallSessionUsageDto callSessionUsageDb : callSessionUsageDbList) {
			CallSessionUsageDtoNew callSessionUsageDtoNew = new CallSessionUsageDtoNew();
			callSessionUsageDtoNew.setId(callSessionUsageDb.getId());
			callSessionUsageDtoNew.setPeerSessionId(callSessionUsageDb.getPeerSessionId());
			callSessionUsageDtoNew.setMsisdn(callSessionUsageDb.getMsisdn());
			callSessionUsageDtoNew.setImsi(callSessionUsageDb.getImsi());
			callSessionUsageDtoNew.setCalledMsisdn(callSessionUsageDb.getCalledMsisdn());
			callSessionUsageDtoNew.setCustomerType(callSessionUsageDb.getCustomerType());
			callSessionUsageDtoNew.setLocationInfo(callSessionUsageDb.getLocationInfo());
			callSessionUsageDtoNew.setSessionState(callSessionUsageDb.getSessionState());
			callSessionUsageDtoNew.setCallStartTs(callSessionUsageDb.getCallStartTs());
			callSessionUsageDtoNew.setCallEndTs(callSessionUsageDb.getCallEndTs());
			callSessionUsageDtoNew.setTotalSeconds(callSessionUsageDb.getTotalSeconds());
			callSessionUsageDtoNew.setCallStatus(callSessionUsageDb.getCallStatus());
			callSessionUsageDtoList.add(callSessionUsageDtoNew);
		}
		return callSessionUsageDtoList;
	}
	
	@Override
	public List<CallSessionUsageDtoNew> getLast5Calls() {
		List<CallSessionUsage> callSessionUsageDbList = callSessionUsageRepo.findTop5ByOrderByIdDesc();
		List<CallSessionUsageDtoNew> callSessionUsageDtoList = new ArrayList<>();
		for (CallSessionUsage callSessionUsageDb : callSessionUsageDbList) {
			CallSessionUsageDtoNew callSessionUsageDtoNew = new CallSessionUsageDtoNew();
			callSessionUsageDtoNew.setId(callSessionUsageDb.getId());
			callSessionUsageDtoNew.setPeerSessionId(callSessionUsageDb.getPeerSessionId());
			callSessionUsageDtoNew.setMsisdn(callSessionUsageDb.getMsisdn());
			callSessionUsageDtoNew.setImsi(callSessionUsageDb.getImsi());
			callSessionUsageDtoNew.setCalledMsisdn(callSessionUsageDb.getCalledMsisdn());
			callSessionUsageDtoNew.setLocationInfo(callSessionUsageDb.getLocationInfo());
			callSessionUsageDtoNew.setSessionState(callSessionUsageDb.getSessionState());
			callSessionUsageDtoNew.setCallStartTs(callSessionUsageDb.getCallStartTs());
			callSessionUsageDtoNew.setCallEndTs(callSessionUsageDb.getCallEndTs());
			callSessionUsageDtoNew.setTotalSeconds(callSessionUsageDb.getTotalSeconds());
			callSessionUsageDtoNew.setCallStatus(callSessionUsageDb.getCallStatus());
			callSessionUsageDtoList.add(callSessionUsageDtoNew);
		}
		return callSessionUsageDtoList;
	}

	public static String fetchReadableDateTime(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = simpleDateFormat.format(date);
		return formattedDate;
	}
	
	public static String createReadableDateTime(String dateTime) {
		int value = dateTime.indexOf('.');
		String formattedDate = "";
		if (value != -1) {
			formattedDate = dateTime.substring(0, value);
		} else {
			return formattedDate = dateTime;
		}
		return formattedDate;
	}
	
	public static LocalDateTime convertDateToLocalDateTime(Date date) {
		Instant instant = date.toInstant();
		LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
		return localDateTime;
	}

	public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

}
