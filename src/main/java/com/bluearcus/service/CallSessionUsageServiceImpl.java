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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CallSessionUsageServiceImpl implements CallSessionUsageService {

	@Autowired
	private CallSessionUsageRepo callSessionUsageRepo;
	
	@Autowired
	private FlatFileService flatFileService;

	@Override
	public ResponseEntity saveCallSessionUsage(CallSessionUsageDto callSessionUsageDto) {
		Optional<CallSessionUsage> callSessionUsage = callSessionUsageRepo.findByImsi(callSessionUsageDto.getImsi());
		if (!callSessionUsage.isPresent()) {
			CallSessionUsage callSessionUsageDb = new CallSessionUsage();
			callSessionUsageDb.setPeerSessionId(callSessionUsageDto.getPeerSessionId());
			callSessionUsageDb.setMsisdn(callSessionUsageDto.getMsisdn());
			callSessionUsageDb.setImsi(callSessionUsageDto.getImsi());
			callSessionUsageDb.setCalledMsisdn(callSessionUsageDto.getCalledMsisdn());
			callSessionUsageDb.setLocationInfo(callSessionUsageDto.getLocationInfo());
			callSessionUsageDb.setSessionState(callSessionUsageDto.getSessionState());
			callSessionUsageDb.setCallStartTs(callSessionUsageDto.getCallStartTs());
			callSessionUsageDb.setCallEndTs(callSessionUsageDto.getCallEndTs());
			callSessionUsageDb.setTotalSeconds(callSessionUsageDto.getTotalSeconds());
			callSessionUsageDb.setCallStatus(callSessionUsageDto.getCallStatus());
			callSessionUsageRepo.save(callSessionUsageDb);
			
			CallSessionUsageDtoNew callSessionUsageDtoNew = new CallSessionUsageDtoNew(callSessionUsageDb.getId(),
					callSessionUsageDb.getPeerSessionId(), callSessionUsageDb.getMsisdn(), callSessionUsageDb.getImsi(),
					callSessionUsageDb.getCalledMsisdn(), callSessionUsageDb.getLocationInfo(),
					callSessionUsageDb.getSessionState(), fetchReadableDateTime(callSessionUsageDb.getCallStartTs()),
					fetchReadableDateTime(callSessionUsageDb.getCallEndTs()), callSessionUsageDb.getTotalSeconds(),
					callSessionUsageDb.getCallStatus());
			
			// Storing data for flat file...
//			String dataForFlatFile = callSessionUsageDtoNew.toString();
//			System.out.println(dataForFlatFile);
//			flatFileService.storeData(dataForFlatFile);
			return new ResponseEntity<>(callSessionUsageDtoNew, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new CustomMessage(HttpStatus.CONFLICT.value(), "IMSI Id already exist"));
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
			callSessionUsageDtoNew.setLocationInfo(callSessionUsageDb.getLocationInfo());
			callSessionUsageDtoNew.setSessionState(callSessionUsageDb.getSessionState());
			callSessionUsageDtoNew.setCallStartTs(fetchReadableDateTime(callSessionUsageDb.getCallStartTs()));
			callSessionUsageDtoNew.setCallEndTs(fetchReadableDateTime(callSessionUsageDb.getCallEndTs()));
			callSessionUsageDtoNew.setTotalSeconds(callSessionUsageDb.getTotalSeconds());
			callSessionUsageDtoNew.setCallStatus(callSessionUsageDb.getCallStatus());
			callSessionUsageDtoList.add(callSessionUsageDtoNew);
		}
		return callSessionUsageDtoList;
	}

	private String fetchReadableDateTime(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = simpleDateFormat.format(date);
		return formattedDate;
	}
}
