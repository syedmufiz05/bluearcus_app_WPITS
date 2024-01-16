package com.bluearcus.service;

import com.bluearcus.dto.RatesDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RatesService {
    RatesDto addRates(RatesDto ratesDto, String authToken) throws JsonProcessingException;

    ResponseEntity getRates(Integer ratesId);

    List<RatesDto> getAllRates();

    ResponseEntity editRates(Integer ratesId, RatesDto ratesDto) throws JsonProcessingException;

    String deleteRates(Integer ratesId);
}
