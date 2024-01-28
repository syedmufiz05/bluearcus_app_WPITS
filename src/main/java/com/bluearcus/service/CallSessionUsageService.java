package com.bluearcus.service;

import com.bluearcus.dto.CallSessionUsageDto;
import com.bluearcus.dto.CallSessionUsageDtoNew;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CallSessionUsageService {
	ResponseEntity saveCallSessionUsage(CallSessionUsageDto callSessionUsageDto);

	List<CallSessionUsageDtoNew> getAllCallSessionUsage();

	ResponseEntity fetchSecondsDuringCall(CallSessionUsageDto callSessionUsageDto);
}
