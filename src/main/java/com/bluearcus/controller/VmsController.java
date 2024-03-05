package com.bluearcus.controller;

import com.bluearcus.dto.VmsDto;
import com.bluearcus.service.VmsService;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vms/detail")
@CrossOrigin("*") 
public class VmsController {
    @Autowired
    private VmsService vmsService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<VmsDto> saveVmsDetails(@RequestBody VmsDto vmsDto, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String authToken = httpServletRequest.getHeader("Authorization").replace("Bearer", "");
        return vmsService.saveVmsDetails(vmsDto, authToken);
    }

    @RequestMapping(value = "/get/{msisdn}", method = RequestMethod.GET)
    public ResponseEntity<VmsDto> getVmsDetails(@PathVariable("msisdn") String msisdn) {
        return vmsService.getVmsDetails(msisdn);
    }

    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public List<VmsDto> getAllVmsDetails() {
        return vmsService.getAllVmsDetails();
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<VmsDto> updateVmsDetails(@RequestParam String msisdn, @RequestBody VmsDto vmsDto) throws JsonProcessingException {
        return vmsService.updateVmsDetails(vmsDto, msisdn);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String deleteVmsDetails(@RequestParam String msisdn) {
        return vmsService.deleteVmsDetails(msisdn);
    }

}
