package com.bluearcus.controller;

import com.bluearcus.dto.CallSessionUsageDto;
import com.bluearcus.dto.CallSessionUsageDtoNew;
import com.bluearcus.service.CallSessionUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/call/session/usage")
@CrossOrigin("http://172.5.10.2:8090/") 
public class CallSessionUsageController {
    @Autowired
    private CallSessionUsageService callSessionUsageService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<CallSessionUsageDtoNew> saveCallSession(@RequestBody CallSessionUsageDto callSessionUsageDto) {
        return callSessionUsageService.saveCallSessionUsage(callSessionUsageDto);
    }
    
	@RequestMapping(value = "/fetch/calling/time", method = RequestMethod.POST)
	public ResponseEntity<CallSessionUsageDtoNew> getCallDuration(@RequestBody CallSessionUsageDto callSessionUsageDto) {
		return callSessionUsageService.fetchSecondsDuringCall(callSessionUsageDto);
	}
    
    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public List<CallSessionUsageDtoNew> getAllCallSession() {
        return callSessionUsageService.getAllCallSessionUsage();
    }
}
