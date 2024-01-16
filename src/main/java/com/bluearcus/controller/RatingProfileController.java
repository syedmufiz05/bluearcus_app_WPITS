package com.bluearcus.controller;

import com.bluearcus.dto.RatingProfileDto;
import com.bluearcus.dto.RatingProfileVoucherDto;
import com.bluearcus.service.RatingProfileService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating/profile")
@CrossOrigin("http://172.5.10.2:8090/")
public class RatingProfileController {
	@Autowired
	private RatingProfileService ratingProfileService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public RatingProfileDto createRatingProfile(@RequestBody RatingProfileDto ratingProfileDto,
			HttpServletRequest httpServletRequest) {
		String authToken = httpServletRequest.getHeader("Authorization").replace("Bearer", "");
		return ratingProfileService.createRatingProfile(ratingProfileDto, authToken);
	}

	@RequestMapping(value = "/voucher/create", method = RequestMethod.POST)
	public ResponseEntity<RatingProfileVoucherDto> createRatingProfileVoucher(
			@RequestBody RatingProfileVoucherDto ratingProfileVoucherDto) {
		return ratingProfileService.createRatingProfileVoucher(ratingProfileVoucherDto);
	}

	@RequestMapping(value = "/voucher/edit", method = RequestMethod.PUT)
	public ResponseEntity<RatingProfileVoucherDto> editRatingProfileVoucher(
			@RequestBody RatingProfileVoucherDto ratingProfileVoucherDto) {
		return ratingProfileService.editRatingProfileVoucher(ratingProfileVoucherDto);
	}

	@RequestMapping(value = "/voucher/get/all", method = RequestMethod.GET)
	public List<RatingProfileVoucherDto> getAllRatingProfileVoucher() {
		return ratingProfileService.getAllRatingProfileVoucher();
	}

	@RequestMapping(value = "/get/all/ratingprofile", method = RequestMethod.GET)
	public List<RatingProfileDto> getAll() {
		return ratingProfileService.getAllRatingProfile();
	}

	@RequestMapping(value = "/get/{rating_profile_id}", method = RequestMethod.GET)
	public ResponseEntity<RatingProfileDto> getRatingProfile(
			@PathVariable("rating_profile_id") Integer ratingProfileId) {
		return ratingProfileService.getRatingProfile(ratingProfileId);
	}

	@RequestMapping(value = "/edit/{rating_profile_id}", method = RequestMethod.PUT)
	public ResponseEntity<RatingProfileDto> editRatingProfile(
			@PathVariable("rating_profile_id") Integer ratingProfileId,
			@RequestParam("calling_party") String callingParty) {
		return ratingProfileService.editRatingProfile(ratingProfileId, callingParty);
	}

	@RequestMapping(value = "/delete/{rating_profile_id}", method = RequestMethod.DELETE)
	public String deleteRatingProfile(@PathVariable("rating_profile_id") Integer ratingProfileId) {
		return ratingProfileService.deleteRatingProfile(ratingProfileId);
	}
}
