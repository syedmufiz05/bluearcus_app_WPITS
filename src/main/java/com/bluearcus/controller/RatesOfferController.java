package com.bluearcus.controller;

import com.bluearcus.dto.RatesOfferDto;
import com.bluearcus.service.RatesOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rates/offer")
@CrossOrigin("*")
public class RatesOfferController {
	@Autowired
	private RatesOfferService ratesOfferService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<RatesOfferDto> saveRatesOfferDetail(@RequestBody RatesOfferDto ratesOfferDto) {
		return ratesOfferService.saveRatesOffer(ratesOfferDto);
	}
	
	@RequestMapping(value = "/edit/{rates_id}", method = RequestMethod.PUT)
	public ResponseEntity<RatesOfferDto> editRatesOfferDetail(@PathVariable("rates_id") Integer ratesId, @RequestBody RatesOfferDto ratesOfferDto) {
		return ratesOfferService.editRatesOffer(ratesId, ratesOfferDto);
	}
	
	@RequestMapping(value = "/delete/{rates_id}", method = RequestMethod.DELETE)
	public ResponseEntity<RatesOfferDto> deleteRatesOfferDetail(@PathVariable("rates_id") Integer ratesId){
		return ratesOfferService.deleteRatesOffer(ratesId);
	}
	
	@RequestMapping(value = "/get/{rates_id}", method = RequestMethod.GET)
	public ResponseEntity<RatesOfferDto> getRatesOfferDetail(@PathVariable("rates_id") Integer ratesId) {
		return ratesOfferService.getRatesOffer(ratesId);
	}

	@RequestMapping(value = "/get/all", method = RequestMethod.GET)
	public List<RatesOfferDto> getAllRatesOffer() {
		return ratesOfferService.getAllRatesOffer();
	}

	@RequestMapping(value = "/bulk/get/all", method = RequestMethod.GET)
	public List<String> getAllRatesOfferBulk() {
		return ratesOfferService.getAllRatesOfferBulk();
	}
	
	@RequestMapping(value = "/get/all/currency/iso/codes", method = RequestMethod.GET)
	public List<String> getAllCurrencyISOCodes() {
		return ratesOfferService.getAllCurrencyISOCodes();
	}

}
