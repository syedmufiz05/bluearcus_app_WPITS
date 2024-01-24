package com.bluearcus.service;

import com.bluearcus.dto.DataSessionUsageDto;
import com.bluearcus.dto.DataSessionUsageDtoNew;
import com.bluearcus.exception.CustomMessage;
import com.bluearcus.model.DataSessionUsage;
import com.bluearcus.repo.DataSessionUsageRepo;
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
public class DataSessionUsageServiceImpl implements DataSessionUsageService {
	@Autowired
	private DataSessionUsageRepo dataSessionUsageRepo;

	@Override
	public ResponseEntity saveDataSessionUsage(DataSessionUsageDto dataSessionUsageDto) {
		Optional<DataSessionUsage> dataSessionUsageDb = dataSessionUsageRepo.findByImsi(dataSessionUsageDto.getImsi() != null ? dataSessionUsageDto.getImsi() : "0");
		if (!dataSessionUsageDb.isPresent()) {
			DataSessionUsage dataSessionUsage = new DataSessionUsage();
			dataSessionUsage.setPeerSessionId(dataSessionUsageDto.getPeerSessionId());
			dataSessionUsage.setMsisdn(dataSessionUsageDto.getMsisdn());
			dataSessionUsage.setImsi(dataSessionUsageDto.getImsi());
			dataSessionUsage.setFramedIp(dataSessionUsageDto.getFramedIp());
			dataSessionUsage.setLocationInfo(dataSessionUsageDto.getLocationInfo());
			dataSessionUsage.setSgsnAddress(dataSessionUsageDto.getSgsnAddress());
			dataSessionUsage.setCalledStationId(dataSessionUsageDto.getCalledStationId());
			dataSessionUsage.setSessionState(dataSessionUsageDto.getSessionState());
			dataSessionUsage.setTotalOctates(dataSessionUsageDto.getTotalOctates());
			dataSessionUsage.setBitrate(dataSessionUsageDto.getBitrate());
			dataSessionUsage.setTotalInputOctets(dataSessionUsageDto.getTotalInputOctets());
			dataSessionUsage.setTotalOutputOctets(dataSessionUsageDto.getTotalOutputOctets());
			dataSessionUsage.setSessionStatus(dataSessionUsageDto.getSessionStatus());
			dataSessionUsageRepo.save(dataSessionUsage);
			DataSessionUsageDtoNew dataSessionUsageDtoNew = new DataSessionUsageDtoNew(dataSessionUsage.getId(),
					dataSessionUsage.getPeerSessionId(), dataSessionUsage.getMsisdn(), dataSessionUsage.getImsi(),
					dataSessionUsage.getFramedIp(), dataSessionUsage.getLocationInfo(),
					dataSessionUsage.getSgsnAddress(), dataSessionUsage.getCalledStationId(),
					dataSessionUsage.getSessionState(), fetchReadableDateTime(dataSessionUsage.getSessionStartTs()),
					fetchReadableDateTime(dataSessionUsage.getSessionEndTs()), dataSessionUsage.getTotalOctates(),
					dataSessionUsage.getBitrate(), dataSessionUsage.getTotalInputOctets(),
					dataSessionUsage.getTotalOutputOctets(), dataSessionUsage.getSessionStatus());
			return new ResponseEntity<>(dataSessionUsageDtoNew, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new CustomMessage(HttpStatus.CONFLICT.value(), "IMSI Id already exist"));
	}

	@Override
	public List<DataSessionUsageDtoNew> getAllDataSessionUsage() {
		List<DataSessionUsageDto> dataSessionUsageDbList = dataSessionUsageRepo.fetchAllDataSessionUsage();
		List<DataSessionUsageDtoNew> dataSessionUsageDtoList = new ArrayList<>();
		for (DataSessionUsageDto dataSessionUsageDb : dataSessionUsageDbList) {
			DataSessionUsageDtoNew dataSessionUsageDto = new DataSessionUsageDtoNew();
			dataSessionUsageDto.setId(dataSessionUsageDb.getId());
			dataSessionUsageDto.setPeerSessionId(dataSessionUsageDb.getPeerSessionId());
			dataSessionUsageDto.setMsisdn(dataSessionUsageDb.getMsisdn());
			dataSessionUsageDto.setImsi(dataSessionUsageDb.getImsi());
			dataSessionUsageDto.setFramedIp(dataSessionUsageDb.getFramedIp());
			dataSessionUsageDto.setLocationInfo(dataSessionUsageDb.getLocationInfo());
			dataSessionUsageDto.setSgsnAddress(dataSessionUsageDb.getSgsnAddress());
			dataSessionUsageDto.setCalledStationId(dataSessionUsageDb.getCalledStationId());
			dataSessionUsageDto.setSessionState(dataSessionUsageDb.getSessionState());
			dataSessionUsageDto.setSessionStartTs(fetchReadableDateTime(dataSessionUsageDb.getSessionStartTs()));
			dataSessionUsageDto.setSessionEndTs(fetchReadableDateTime(dataSessionUsageDb.getSessionEndTs()));
			dataSessionUsageDto.setTotalOctates(dataSessionUsageDb.getTotalOctates());
			dataSessionUsageDto.setBitrate(dataSessionUsageDb.getBitrate());
			dataSessionUsageDto.setTotalInputOctets(dataSessionUsageDb.getTotalInputOctets());
			dataSessionUsageDto.setTotalOutputOctets(dataSessionUsageDb.getTotalOutputOctets());
			dataSessionUsageDto.setSessionStatus(dataSessionUsageDb.getSessionStatus());
			dataSessionUsageDtoList.add(dataSessionUsageDto);
		}
		return dataSessionUsageDtoList;
	}

	private String fetchReadableDateTime(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = simpleDateFormat.format(date);
		return formattedDate;
	}
}
