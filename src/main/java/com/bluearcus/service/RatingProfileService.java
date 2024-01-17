package com.bluearcus.service;

import com.bluearcus.dto.RatingProfileDto;
import com.bluearcus.dto.RatingProfileVoucherDto;
import com.bluearcus.model.RatingProfile;
import com.bluearcus.model.RatingProfileVoucher;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RatingProfileService {
    RatingProfileDto createRatingProfile(RatingProfileDto ratingProfileDto, String authToken);

    ResponseEntity createRatingProfileVoucher(RatingProfileVoucherDto ratingProfileVoucherDto);

	ResponseEntity editRatingProfileVoucher(Integer ratingProfileId, RatingProfileVoucherDto ratingProfileVoucherDto);
    
    List<RatingProfileVoucherDto> getAllRatingProfileVoucher();

    ResponseEntity getRatingProfile(Integer ratingProfileId);

    List<RatingProfileDto> getAllRatingProfile();
    
    ResponseEntity editRatingProfile(Integer ratingProfileId, String callingParty);

    String deleteRatingProfile(Integer ratingProfileId);
}
