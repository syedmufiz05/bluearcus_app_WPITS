package com.bluearcus.service;

import org.springframework.http.ResponseEntity;

import com.bluearcus.dto.PackAllocationDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface PackAllocationService {
	ResponseEntity packAllocationForPrepaid(PackAllocationDto packAllocationDto) throws JsonProcessingException;
	
	ResponseEntity getAssignedPrepaidPack(Integer customerId);
	
	ResponseEntity packAllocationForPostpaid(PackAllocationDto packAllocationDto) throws JsonProcessingException;
	
	ResponseEntity getAssignedPostpaidPack(Integer customerId);
}
