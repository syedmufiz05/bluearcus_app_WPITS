package com.bluearcus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bluearcus.dto.PackAllocationDto;
import com.bluearcus.service.PackAllocationService;

@RestController
@RequestMapping("/api/pack/allocation")
@CrossOrigin("*")
public class PackAllocationController {

	@Autowired
	private PackAllocationService packAllocationService;

	@RequestMapping(value = "/prepaid", method = RequestMethod.POST)
	public ResponseEntity<PackAllocationDto> savePackAllocationPrepaid(@RequestBody PackAllocationDto packAllocationDto) {
		return packAllocationService.packAllocationForPrepaid(packAllocationDto);
	}
	
	@RequestMapping(value = "/assigned/prepaid/{customer_id}", method = RequestMethod.GET)
	public ResponseEntity<PackAllocationDto> getAssignedPrepaidPack(@PathVariable("customer_id") Integer customerId) {
		return packAllocationService.getAssignedPrepaidPack(customerId);
	}

	@RequestMapping(value = "/postpaid", method = RequestMethod.POST)
	public ResponseEntity<PackAllocationDto> savePackAllocationPostpaid(@RequestBody PackAllocationDto packAllocationDto) {
		return packAllocationService.packAllocationForPostpaid(packAllocationDto);
	}
	
	@RequestMapping(value = "/assigned/postpaid/{customer_id}", method = RequestMethod.GET)
	public ResponseEntity<PackAllocationDto> getAssignedPostpaidPack(@PathVariable("customer_id") Integer customerId) {
		return packAllocationService.getAssignedPostpaidPack(customerId);
	}
}
