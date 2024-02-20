package com.bluearcus.service;

import com.bluearcus.dto.HssProvDtoNew;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HssProvService {
    ResponseEntity saveHssProvNew(HssProvDtoNew hssProvDtoNew, String authToken) throws JsonProcessingException;

    ResponseEntity getHssProv(String imsi, String msisdn);
    
    List<HssProvDtoNew> getAllHssProvRecord();

    ResponseEntity updateHssProv(String imsi, String msisdn, HssProvDtoNew hssProvDto) throws JsonProcessingException;

    ResponseEntity deleteHssProv(String imsi, String msisdn);
}
