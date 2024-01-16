package com.bluearcus.repo;

import com.bluearcus.dto.RatesDto;
import com.bluearcus.model.Rates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatesRepository extends JpaRepository<Rates, Integer> {
    @Query("select new com.bluearcus.dto.RatesDto(rates.id,rates.destName,rates.destType,rates.ratesIndex,rates.description,rates.isRatesActive,rates.accessLogs.id) from Rates rates")
    List<RatesDto> fetchAllRates();
}
