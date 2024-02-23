package com.bluearcus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bluearcus.dto.PackAllocationDto;
import com.bluearcus.service.PackAllocationService;

@RestController
@RequestMapping("/api/pack/allocation")
@CrossOrigin({"http://172.5.10.2:8090/","http://localhost:5173/","http://127.0.0.1:5173/"})
public class PackAllocationController {

	@Autowired
	private PackAllocationService packAllocationService;

	@RequestMapping(value = "/prepaid", method = RequestMethod.POST)
	public ResponseEntity<PackAllocationDto> savePackAllocationPrepaid(@RequestBody PackAllocationDto packAllocationDto) {
		return packAllocationService.packAllocationForPrepaid(packAllocationDto);
	}

	@RequestMapping(value = "/postpaid", method = RequestMethod.POST)
	public ResponseEntity<PackAllocationDto> savePackAllocationPostpaid(@RequestBody PackAllocationDto packAllocationDto) {
		return packAllocationService.packAllocationForPostpaid(packAllocationDto);
	}
}
