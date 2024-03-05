package com.bluearcus.controller;

import com.bluearcus.dto.HssProvDto;
import com.bluearcus.dto.HssProvDtoNew;
import com.bluearcus.service.HssProvServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hss/detail")
@CrossOrigin("*") 
public class HssProvController {
    @Autowired
    private HssProvServiceImpl hssProvService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<HssProvDtoNew> saveHssDetails(@RequestBody HssProvDtoNew hssProvDtoNew, HttpServletRequest httpServletRequest) throws JsonProcessingException {
    	String authCode = httpServletRequest.getHeader("Authorization").replace("Bearer", "");
        return hssProvService.saveHssProvNew(hssProvDtoNew, authCode);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<HssProvDto> getDetails(@RequestParam("imsi") String imsi, @RequestParam("msisdn") String msisdn) {
        return hssProvService.getHssProv(imsi, msisdn);
    }

    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public List<HssProvDtoNew> getAllDetails() {
        return hssProvService.getAllHssProvRecord();
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<HssProvDtoNew> updateDetails(@RequestParam String imsi, @RequestParam String msisdn, @RequestBody HssProvDtoNew hssProvDto) throws JsonProcessingException {
        return hssProvService.updateHssProv(imsi, msisdn, hssProvDto);
    }

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseEntity deleteHssProvData(@RequestParam String imsi, @RequestParam String msisdn) {
		return hssProvService.deleteHssProv(imsi, msisdn);
	}

}
