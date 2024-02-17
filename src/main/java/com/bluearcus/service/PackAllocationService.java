package com.bluearcus.service;

import org.springframework.http.ResponseEntity;

import com.bluearcus.dto.PackAllocationDto;

public interface PackAllocationService {
	ResponseEntity packAllocationForPrepaid(PackAllocationDto packAllocationDto);
	
	ResponseEntity packAllocationForPostpaid(PackAllocationDto packAllocationDto);
}
