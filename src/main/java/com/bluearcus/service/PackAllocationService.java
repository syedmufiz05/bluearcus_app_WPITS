package com.bluearcus.service;

import org.springframework.http.ResponseEntity;

import com.bluearcus.dto.PackAllocationDto;

public interface PackAllocationService {
	ResponseEntity packAllocationForPrepaid(PackAllocationDto packAllocationDto);
	
	ResponseEntity getAssignedPrepaidPack(Integer customerId);
	
	ResponseEntity packAllocationForPostpaid(PackAllocationDto packAllocationDto);
	
	ResponseEntity getAssignedPostpaidPack(Integer customerId);
}
