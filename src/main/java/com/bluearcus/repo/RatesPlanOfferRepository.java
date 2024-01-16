package com.bluearcus.repo;

import com.bluearcus.dto.RatesPlanOfferDto;
import com.bluearcus.model.RatesPlanOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatesPlanOfferRepository extends JpaRepository<RatesPlanOffer, Integer> {
    @Query("select new com.bluearcus.dto.RatesPlanOfferDto(ratesPlanOffer.id,ratesPlanOffer.name,ratesPlanOffer.period,ratesPlanOffer.description,ratesPlanOffer.active) from RatesPlanOffer ratesPlanOffer")
    List<RatesPlanOfferDto> fetchAllRatesPlan();
}
