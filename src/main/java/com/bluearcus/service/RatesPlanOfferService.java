package com.bluearcus.service;

import com.bluearcus.dto.RatesPlanOfferDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RatesPlanOfferService {
	
	ResponseEntity saveRatesPlanDetail(RatesPlanOfferDto ratesPlanOfferDto);

	ResponseEntity editRatesPlanDetail(Integer ratesPlanOfferId, RatesPlanOfferDto ratesPlanOfferDto);

	ResponseEntity deleteRatesPlanDetail(Integer ratesPlanOfferId);

	ResponseEntity getRatesPlanDetail(Integer ratesPlanOfferId);

	List<RatesPlanOfferDto> getAllRatesPlan();

	List<String> getAllRatesPlansBulk();
	
}
