package com.bluearcus.service;

import com.bluearcus.dto.RatesPlanOfferDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RatesPlanOfferService {
    ResponseEntity saveRatesPlanDetail(RatesPlanOfferDto ratesPlanOfferDto);

    ResponseEntity getRatesPlanDetail(Integer ratesPlanOfferId);
    
    List<RatesPlanOfferDto> getAllRatesPlan();
    
    List<String> getAllRatesPlansBulk();
}
