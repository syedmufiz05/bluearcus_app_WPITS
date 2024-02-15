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
@CrossOrigin("http://172.5.10.2:8090/")
public class PackAllocationController {

	@Autowired
	private PackAllocationService packAllocationService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<PackAllocationDto> savePackAllocationDetails(@RequestBody PackAllocationDto packAllocationDto) {
		return packAllocationService.savePackAllocationDetail(packAllocationDto);
	}
}