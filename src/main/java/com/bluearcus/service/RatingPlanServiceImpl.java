package com.bluearcus.service;

import com.bluearcus.dto.RatingPlanDto;
import com.bluearcus.model.DestinationRates;
import com.bluearcus.model.RatingPlan;
import com.bluearcus.repo.DestinationRatesRepository;
import com.bluearcus.repo.RatingPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingPlanServiceImpl implements RatingPlanService {
    @Autowired
    private DestinationRatesRepository destinationRatesRepository;
    @Autowired
    private RatingPlanRepository ratingPlanRepository;

    @Override
    public RatingPlanDto createRatingPlan(RatingPlanDto ratingPlanDto, String authToken) {
        Optional<RatingPlan> ratingPlan = ratingPlanRepository.findByRatingPlanId(ratingPlanDto.getRatingPlanId());
        if (ratingPlan.isPresent()) {
            RatingPlan ratingPlanDb = ratingPlan.get();
            DestinationRates destinationRates = new DestinationRates();
            destinationRatesRepository.save(destinationRates);
            ratingPlanDb.setDestinationRates(destinationRates);
            ratingPlanRepository.save(ratingPlanDb);
            return new RatingPlanDto(ratingPlanDb.getRatingPlanId(), destinationRates.getId());
        }
        RatingPlan ratingPlanNew = new RatingPlan();
        DestinationRates destinationRates = new DestinationRates();
        destinationRatesRepository.save(destinationRates);
        ratingPlanNew.setDestinationRates(destinationRates);
        ratingPlanRepository.save(ratingPlanNew);
        return new RatingPlanDto(ratingPlanNew.getRatingPlanId(), destinationRates.getId());
    }

    @Override
    public String deleteRatingPlan(Integer ratingPlanId) {
        ratingPlanRepository.deleteByRatingPlanId(ratingPlanId);
        return "Successfully deleted...";
    }
}