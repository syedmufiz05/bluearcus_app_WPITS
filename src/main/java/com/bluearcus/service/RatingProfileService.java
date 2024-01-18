package com.bluearcus.service;

import com.bluearcus.dto.RatingProfileVoucherDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RatingProfileService {
	ResponseEntity createRatingProfileVoucher(RatingProfileVoucherDto ratingProfileVoucherDto);

	ResponseEntity editRatingProfileVoucher(Integer ratingProfileId, RatingProfileVoucherDto ratingProfileVoucherDto);

	ResponseEntity getRatingProfileVoucher(Integer ratingProfileId);

	List<RatingProfileVoucherDto> getAllRatingProfileVoucher();

	ResponseEntity deleteRatingProfileVoucher(Integer ratingProfileId);
}
