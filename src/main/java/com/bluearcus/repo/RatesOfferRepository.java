package com.bluearcus.repo;

import com.bluearcus.dto.RatesOfferDto;
import com.bluearcus.model.RatesOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatesOfferRepository extends JpaRepository<RatesOffer, Integer> {
    @Query("select new com.bluearcus.dto.RatesOfferDto(ratesOffer.id,ratesOffer.price,ratesOffer.priceType,ratesOffer.period,ratesOffer.description)from RatesOffer ratesOffer")
    List<RatesOfferDto> fecthAllRatesOffer();
}
