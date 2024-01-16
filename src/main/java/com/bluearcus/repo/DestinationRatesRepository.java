package com.bluearcus.repo;

import com.bluearcus.dto.DestinationRatesDto;
import com.bluearcus.model.DestinationRates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationRatesRepository extends JpaRepository<DestinationRates, Integer> {
    @Query("select new com.bluearcus.dto.DestinationRatesDto(destRates.id,destRates.destination.id,destRates.rates.id)from DestinationRates destRates")
    List<DestinationRatesDto> fetchAllDestination();
}
