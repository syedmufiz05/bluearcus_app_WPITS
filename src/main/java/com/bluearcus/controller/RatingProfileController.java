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

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<RatingProfileVoucherDto> createRatingProfileVoucher(@RequestBody RatingProfileVoucherDto ratingProfileVoucherDto) {
		return ratingProfileService.createRatingProfileVoucher(ratingProfileVoucherDto);
	}

	@RequestMapping(value = "/edit/{rating_profile_id}", method = RequestMethod.PUT)
	public ResponseEntity<RatingProfileVoucherDto> editRatingProfileVoucher(@PathVariable("rating_profile_id") Integer ratingProfileId , @RequestBody RatingProfileVoucherDto ratingProfileVoucherDto) {
		return ratingProfileService.editRatingProfileVoucher(ratingProfileId, ratingProfileVoucherDto);
	}

	@RequestMapping(value = "/delete/{rating_profile_id}", method = RequestMethod.DELETE)
	public ResponseEntity<RatingProfileVoucherDto> deleteRatingProfileVoucher(@PathVariable("rating_profile_id") Integer ratingProfileId) {
		return ratingProfileService.deleteRatingProfileVoucher(ratingProfileId);
	}

	@RequestMapping(value = "/get/{rating_profile_id}", method = RequestMethod.GET)
	public ResponseEntity<RatingProfileVoucherDto> getRatingProfileVoucher(@PathVariable("rating_profile_id") Integer ratingProfileId) {
		return ratingProfileService.getRatingProfileVoucher(ratingProfileId);
	}
	
	@RequestMapping(value = "/get/pack/name", method = RequestMethod.GET)
	public ResponseEntity<RatingProfileVoucherDto> getVoucherViaPackName(@RequestParam("pack_name") String packName) {
		return ratingProfileService.getRatingProfileVoucherByPackName(packName);
	}
	
	@RequestMapping(value = "/get/ctg/name", method = RequestMethod.GET)
	public List<RatingProfileVoucherDto> getVoucherViaCtgName(@RequestParam("ctg_name") String ctgName) {
		return ratingProfileService.getVoucherByCtgName(ctgName);
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public List<RatingProfileVoucherDto> getRatingProfileVoucherByCategory(@RequestParam ("keyword") String keyword) {
		return ratingProfileService.searchVoucherByName(keyword);
	}

	@RequestMapping(value = "/get/all", method = RequestMethod.GET)
	public List<RatingProfileVoucherDto> getAllRatingProfileVoucher() {
		return ratingProfileService.getAllRatingProfileVoucher();
	}
	
	@RequestMapping(value = "/get/all/data/parameters", method = RequestMethod.GET)
	public List<String> getAllDataParameters() {
		return ratingProfileService.getAllDataBalanceParams();
	}

	@RequestMapping(value = "/get/all/call/parameters", method = RequestMethod.GET)
	public List<String> getAllCallParameters() {
		return ratingProfileService.getAllCallBalanceParams();
	}
}
