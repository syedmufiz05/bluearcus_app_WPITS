package com.bluearcus.service;

import com.bluearcus.dto.AucDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AucService {
    ResponseEntity saveAucDetails(AucDto aucDto, String authToken) throws JsonProcessingException;

    ResponseEntity getAucDetails(String imsi);

    List<AucDto> getAllAucDetails();

    ResponseEntity updateAucDetails(String imsi, AucDto aucDto) throws JsonProcessingException;

    void deleteAucDetails(String imsi);
}
