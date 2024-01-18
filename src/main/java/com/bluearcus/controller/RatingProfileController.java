package com.bluearcus.controller;

import com.bluearcus.dto.RatingProfileVoucherDto;
import com.bluearcus.service.RatingProfileService;

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

	@RequestMapping(value = "/voucher/create", method = RequestMethod.POST)
	public ResponseEntity<RatingProfileVoucherDto> createRatingProfileVoucher(
			@RequestBody RatingProfileVoucherDto ratingProfileVoucherDto) {
		return ratingProfileService.createRatingProfileVoucher(ratingProfileVoucherDto);
	}

	@RequestMapping(value = "/voucher/edit/{rating_profile_id}", method = RequestMethod.PUT)
	public ResponseEntity<RatingProfileVoucherDto> editRatingProfileVoucher(
			@PathVariable("rating_profile_id") Integer ratingProfileId,
			@RequestBody RatingProfileVoucherDto ratingProfileVoucherDto) {
		return ratingProfileService.editRatingProfileVoucher(ratingProfileId, ratingProfileVoucherDto);
	}

	@RequestMapping(value = "/voucher/delete/{rating_profile_id}", method = RequestMethod.DELETE)
	public ResponseEntity<RatingProfileVoucherDto> deleteRatingProfileVoucher(
			@PathVariable("rating_profile_id") Integer ratingProfileId) {
		return ratingProfileService.deleteRatingProfileVoucher(ratingProfileId);
	}

	@RequestMapping(value = "/voucher/get/{rating_profile_id}", method = RequestMethod.GET)
	public ResponseEntity<RatingProfileVoucherDto> getRatingProfileVoucher(
			@PathVariable("rating_profile_id") Integer ratingProfileId) {
		return ratingProfileService.getRatingProfileVoucher(ratingProfileId);
	}

	@RequestMapping(value = "/voucher/get/all", method = RequestMethod.GET)
	public List<RatingProfileVoucherDto> getAllRatingProfileVoucher() {
		return ratingProfileService.getAllRatingProfileVoucher();
	}

}
