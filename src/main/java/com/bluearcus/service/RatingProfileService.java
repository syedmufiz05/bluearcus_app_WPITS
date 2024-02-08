package com.bluearcus.service;

import com.bluearcus.dto.RatingProfileVoucherDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RatingProfileService {
	ResponseEntity createRatingProfileVoucher(RatingProfileVoucherDto ratingProfileVoucherDto);

	ResponseEntity editRatingProfileVoucher(Integer ratingProfileId, RatingProfileVoucherDto ratingProfileVoucherDto);

	ResponseEntity getRatingProfileVoucher(Integer ratingProfileId);

	ResponseEntity getRatingProfileVoucherByPackName(String packName);

	List<RatingProfileVoucherDto> getAllRatingProfileVoucher();

	ResponseEntity deleteRatingProfileVoucher(Integer ratingProfileId);

	List<RatingProfileVoucherDto> searchVoucherByName(String category);
	
	List<RatingProfileVoucherDto> getVoucherByCtgName(String ctgName);
	
	List<String> getAllDataBalanceParams();
	
	List<String> getAllCallBalanceParams();
}
