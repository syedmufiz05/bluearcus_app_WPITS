package com.bluearcus.controller;

import com.bluearcus.dto.RatesDto;
import com.bluearcus.service.RatesService;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rates/detail")
@CrossOrigin({"http://172.5.10.2:8090/","http://localhost:5173/","http://127.0.0.1:5173/","http://localhost:5500/"}) 
public class RatesController {

    @Autowired
    private RatesService ratesService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RatesDto addRates(@RequestBody RatesDto ratesDto, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String authToken = httpServletRequest.getHeader("Authorization").replace("Bearer", "");
        return ratesService.addRates(ratesDto, authToken);
    }

    @RequestMapping(value = "/get/{rates_id}", method = RequestMethod.GET)
    public ResponseEntity<RatesDto> getRates(@PathVariable("rates_id") Integer ratesId) {
        return ratesService.getRates(ratesId);
    }

    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public List<RatesDto> getAllRates() {
        return ratesService.getAllRates();
    }

    @RequestMapping(value = "/edit/{rates_id}", method = RequestMethod.PUT)
    public ResponseEntity<RatesDto> editRates(@PathVariable("rates_id") Integer ratesId, @RequestBody RatesDto ratesDto) throws JsonProcessingException {
        return ratesService.editRates(ratesId, ratesDto);
    }

    @RequestMapping(value = "/delete/{rates_id}", method = RequestMethod.DELETE)
    public String deleteRates(@PathVariable("rates_id") Integer ratesId) {
        return ratesService.deleteRates(ratesId);
    }
}
