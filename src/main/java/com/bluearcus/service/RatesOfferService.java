package com.bluearcus.service;

import com.bluearcus.dto.RatesOfferDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RatesOfferService {
    public ResponseEntity saveRatesOffer(RatesOfferDto ratesOfferDto);
    
    public ResponseEntity getRatesOffer(Integer ratesId);

    public List<String> getAllRatesOfferBulk();
    
    public List<RatesOfferDto> getAllRatesOffer();
    
    public List<String> getAllCurrencyISOCodes();
}
