package com.bluearcus.controller;

import com.bluearcus.dto.DestinationDto;
import com.bluearcus.service.DestinationService;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destination")
@CrossOrigin({"http://172.5.10.2:8090/","http://localhost:5173/","http://127.0.0.1:5173/","http://localhost:5500/","http://127.0.0.1:5500/"})
public class DestinationController {
	@Autowired
	private DestinationService destinationService;

	@RequestMapping(value = "/detail/add", method = RequestMethod.POST)
	public DestinationDto saveDestinationDetails(@RequestBody DestinationDto destinationDto,
			HttpServletRequest httpServletRequest) throws JsonProcessingException {
		String authToken = httpServletRequest.getHeader("Authorization").replace("Bearer", "");
		return destinationService.addDestination(destinationDto, authToken);
	}

	@RequestMapping(value = "/detail/get/{destination_id}", method = RequestMethod.GET)
	public ResponseEntity<DestinationDto> getDestinationDetails(@PathVariable("destination_id") Integer destinationId) {
		return destinationService.getDestinationDetail(destinationId);
	}

	@RequestMapping(value = "/detail/get/all", method = RequestMethod.GET)
	public List<DestinationDto> getDestinationAllDetails() {
		return destinationService.getAllDestinationDetail();
	}

	@RequestMapping(value = "/detail/edit/{destination_id}", method = RequestMethod.PUT)
	public ResponseEntity<DestinationDto> editDestinationDetails(@PathVariable("destination_id") Integer destinationId,
			@RequestBody DestinationDto destinationDto) throws JsonProcessingException {
		return destinationService.editDestination(destinationId, destinationDto);
	}

	@RequestMapping(value = "/detail/delete/{destination_id}", method = RequestMethod.DELETE)
	public String deleteDestinationDetails(@PathVariable("destination_id") Integer destinationId) {
		return destinationService.deleteDestination(destinationId);
	}
}
