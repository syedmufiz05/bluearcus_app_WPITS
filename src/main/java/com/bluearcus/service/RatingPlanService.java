package com.bluearcus.service;

import com.bluearcus.dto.RatingPlanDto;

public interface RatingPlanService {
	RatingPlanDto createRatingPlan(RatingPlanDto ratingPlanDto, String authToken);

	String deleteRatingPlan(Integer ratingPlanId);
}
