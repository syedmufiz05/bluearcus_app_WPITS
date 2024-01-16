package com.bluearcus.service;

import com.bluearcus.dto.VmsDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VmsService {
    ResponseEntity saveVmsDetails(VmsDto vmsDto, String authToken) throws JsonProcessingException;

    ResponseEntity getVmsDetails(String msisdn);

    List<VmsDto> getAllVmsDetails();

    ResponseEntity updateVmsDetails(VmsDto vmsDto, String msisdn) throws JsonProcessingException;

    String deleteVmsDetails(String msisdn);

}
