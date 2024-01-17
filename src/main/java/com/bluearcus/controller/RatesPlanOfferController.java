package com.bluearcus.controller;

import com.bluearcus.dto.RatesPlanOfferDto;
import com.bluearcus.service.RatesPlanOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rates/plan/offer")
@CrossOrigin("http://172.5.10.2:8090/")
public class RatesPlanOfferController {
	@Autowired
	private RatesPlanOfferService ratesPlanOfferService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<RatesPlanOfferDto> saveRatesPlanOffer(@RequestBody RatesPlanOfferDto ratesPlanOfferDto) {
		return ratesPlanOfferService.saveRatesPlanDetail(ratesPlanOfferDto);
	}
	
	@RequestMapping(value = "/edit/{rates_plan_offer_id}", method = RequestMethod.POST)
	public ResponseEntity<RatesPlanOfferDto> editRatesPlanOffer(@PathVariable("rates_plan_offer_id") Integer ratesPlanOfferId,@RequestBody RatesPlanOfferDto ratesPlanOfferDto) {
		return ratesPlanOfferService.editRatesPlanDetail(ratesPlanOfferId, ratesPlanOfferDto);
	}
	
	@RequestMapping(value = "/delete/{rates_plan_offer_id}", method = RequestMethod.DELETE)
	public ResponseEntity<RatesPlanOfferDto> deletetRatesPlanOffer(@PathVariable("rates_plan_offer_id") Integer ratesPlanOfferId) {
		return ratesPlanOfferService.deleteRatesPlanDetail(ratesPlanOfferId);
	}

	@RequestMapping(value = "/get/{rates_plan_offer_id}", method = RequestMethod.GET)
	public ResponseEntity<RatesPlanOfferDto> getRatesPlanOffer(@PathVariable("rates_plan_offer_id") Integer ratesPlanOfferId) {
		return ratesPlanOfferService.getRatesPlanDetail(ratesPlanOfferId);
	}

	@RequestMapping(value = "/get/all", method = RequestMethod.GET)
	public List<RatesPlanOfferDto> getAllRatesPlanOffer() {
		return ratesPlanOfferService.getAllRatesPlan();
	}

	@RequestMapping(value = "/get/all/bulk", method = RequestMethod.GET)
	public List<String> getAllRatesPlanOfferBulk() {
		return ratesPlanOfferService.getAllRatesPlansBulk();
	}
}
