package com.bluearcus.controller;

import com.bluearcus.dto.DestinationRatesDto;
import com.bluearcus.service.DestinationRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destination/rates")
@CrossOrigin({"http://172.5.10.2:8090/","http://localhost:5173/","http://127.0.0.1:5173/","http://localhost:5500/","http://127.0.0.1:5500/","http://127.0.0.1:5174/","http://172.5.10.2:9091/"})
public class DestinationRatesController {
	@Autowired
	private DestinationRatesService destinationRatesService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public DestinationRatesDto createDestinationRates(@RequestBody DestinationRatesDto destinationRatesDto) {
		return destinationRatesService.createDestinationRates(destinationRatesDto);
	}

	@RequestMapping(value = "/get/all", method = RequestMethod.GET)
	public List<DestinationRatesDto> getAllDestinationRates() {
		return destinationRatesService.getAllDestinationRates();
	}

	@RequestMapping(value = "/delete/{destination_rates_id}", method = RequestMethod.DELETE)
	public String deleteDestinationRates(@PathVariable("destination_rates_id") Integer destinationRatesId) {
		return destinationRatesService.deleteDestinationRates(destinationRatesId);
	}
}
