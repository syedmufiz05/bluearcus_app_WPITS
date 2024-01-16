package com.bluearcus.controller;

import com.bluearcus.dto.DataSessionUsageDto;
import com.bluearcus.dto.DataSessionUsageDtoNew;
import com.bluearcus.service.DataSessionUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/data/session/usage")
@CrossOrigin("http://172.5.10.2:8090/") 
public class DataSessionUsageController {
	@Autowired
	private DataSessionUsageService dataSessionUsageService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<DataSessionUsageDtoNew> saveDataSessionUsage(
			@RequestBody DataSessionUsageDto dataSessionUsageDto) {
		return dataSessionUsageService.saveDataSessionUsage(dataSessionUsageDto);
	}

	@RequestMapping(value = "/get/all", method = RequestMethod.GET)
	public List<DataSessionUsageDtoNew> getAllDataSessionUsage() {
		return dataSessionUsageService.getAllDataSessionUsage();
	}
}
