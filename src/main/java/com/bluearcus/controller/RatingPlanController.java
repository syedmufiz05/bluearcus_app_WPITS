package com.bluearcus.controller;

import com.bluearcus.dto.RatingPlanDto;
import com.bluearcus.service.RatingPlanService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating/plan")
@CrossOrigin("*") 
public class RatingPlanController {
	@Autowired
	private RatingPlanService ratingPlanService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public RatingPlanDto createRatingPlan(@RequestBody RatingPlanDto ratingPlanDto,
			HttpServletRequest httpServletRequest) {
		String authToken = httpServletRequest.getHeader("Authorization").replace("Bearer", "");
		return ratingPlanService.createRatingPlan(ratingPlanDto, authToken);
	}

	@RequestMapping(value = "/delete/{rating_plan_id}", method = RequestMethod.DELETE)
	public String deleteRatingPlan(@PathVariable("rating_plan_id") Integer ratingPlanId) {
		return ratingPlanService.deleteRatingPlan(ratingPlanId);
	}
}
