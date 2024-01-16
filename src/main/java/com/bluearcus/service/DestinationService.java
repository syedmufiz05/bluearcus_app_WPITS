package com.bluearcus.service;

import com.bluearcus.dto.DestinationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DestinationService {
    DestinationDto addDestination(DestinationDto destinationDto, String authToken) throws JsonProcessingException;

    ResponseEntity getDestinationDetail(Integer destinationId);

    List<DestinationDto> getAllDestinationDetail();

    ResponseEntity editDestination(Integer destinationId, DestinationDto destinationDto) throws JsonProcessingException;

    String deleteDestination(Integer destinationId);
}
