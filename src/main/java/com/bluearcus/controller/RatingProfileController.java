package com.bluearcus.controller;

import com.bluearcus.dto.RatingProfileVoucherDto;
import com.bluearcus.service.RatingProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating/profile")
@CrossOrigin("*")
public class RatingProfileController {
	@Autowired
	private RatingProfileService ratingProfileService;

	@PostMapping("/create")
	public ResponseEntity<RatingProfileVoucherDto> createRatingProfileVoucher(@RequestBody RatingProfileVoucherDto ratingProfileVoucherDto) {
		return ratingProfileService.createRatingProfileVoucher(ratingProfileVoucherDto);
	}

	@PutMapping("/edit/{rating_profile_id}")
	public ResponseEntity<RatingProfileVoucherDto> editRatingProfileVoucher(@PathVariable("rating_profile_id") Integer ratingProfileId , @RequestBody RatingProfileVoucherDto ratingProfileVoucherDto) {
		return ratingProfileService.editRatingProfileVoucher(ratingProfileId, ratingProfileVoucherDto);
	}

	@DeleteMapping("/delete/{rating_profile_id}")
	public ResponseEntity<RatingProfileVoucherDto> deleteRatingProfileVoucher(@PathVariable("rating_profile_id") Integer ratingProfileId) {
		return ratingProfileService.deleteRatingProfileVoucher(ratingProfileId);
	}

	@GetMapping("/get/{rating_profile_id}")
	public ResponseEntity<RatingProfileVoucherDto> getRatingProfileVoucher(@PathVariable("rating_profile_id") Integer ratingProfileId) {
		return ratingProfileService.getRatingProfileVoucher(ratingProfileId);
	}
	
	@GetMapping("/get/pack/name")
	public ResponseEntity<RatingProfileVoucherDto> getVoucherViaPackName(@RequestParam("pack_name") String packName) {
		return ratingProfileService.getRatingProfileVoucherByPackName(packName);
	}
	
	@GetMapping("/get/ctg/name")
	public List<RatingProfileVoucherDto> getVoucherViaCtgName(@RequestParam("ctg_name") String ctgName) {
		return ratingProfileService.getVoucherByCtgName(ctgName);
	}
	
	@GetMapping("/search")
	public List<RatingProfileVoucherDto> getRatingProfileVoucherByCategory(@RequestParam String keyword) {
		return ratingProfileService.searchVoucherByName(keyword);
	}

	@GetMapping("/get/all")
	public List<RatingProfileVoucherDto> getAllRatingProfileVoucher() {
		return ratingProfileService.getAllRatingProfileVoucher();
	}
	
	@RequestMapping(value = "/get/all/{pack_for}", method = RequestMethod.GET)
	public List<RatingProfileVoucherDto> getRatingProfileVoucherByPackFor(@PathVariable("pack_for") String packFor) {
		return ratingProfileService.getRatingProfileVoucherByPackFor(packFor);
	}
	
	@GetMapping("/get/all/data/parameters")
	public List<String> getAllDataParameters() {
		return ratingProfileService.getAllDataBalanceParams();
	}

	@GetMapping("/get/all/call/parameters")
	public List<String> getAllCallParameters() {
		return ratingProfileService.getAllCallBalanceParams();
	}
}
