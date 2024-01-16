package com.bluearcus.service;

import com.bluearcus.dto.DestinationRatesDto;

import java.util.List;

public interface DestinationRatesService {
    DestinationRatesDto createDestinationRates(DestinationRatesDto destinationRatesDto);

    List<DestinationRatesDto> getAllDestinationRates();

    String deleteDestinationRates(Integer destinationRatesId);
}
