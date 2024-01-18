package com.bluearcus.service;

import com.bluearcus.dto.RatingProfileVoucherDto;
import com.bluearcus.exception.CustomMessage;
import com.bluearcus.model.RatingProfileVoucher;
import com.bluearcus.repo.RatingProfileVoucherRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class RatingProfileServiceImpl implements RatingProfileService {
	@Autowired
	private RatingProfileVoucherRepository ratingProfileVoucherRepository;

	@Override
	public ResponseEntity createRatingProfileVoucher(RatingProfileVoucherDto ratingProfileVoucherDto) {
		Optional<RatingProfileVoucher> ratingProfileVoucherDb = ratingProfileVoucherRepository.findById(
				ratingProfileVoucherDto.getRatingProfileId() != null ? ratingProfileVoucherDto.getRatingProfileId()
						: 0);
		if (!ratingProfileVoucherDb.isPresent()) {
			RatingProfileVoucher ratingProfileVoucher = new RatingProfileVoucher();
			ratingProfileVoucher.setPackName(
					ratingProfileVoucherDto.getPackName() != null ? ratingProfileVoucherDto.getPackName() : "");
			ratingProfileVoucher.setPackType(
					ratingProfileVoucherDto.getPackType() != null ? ratingProfileVoucherDto.getPackType() : "");
			ratingProfileVoucher.setCategoryOffer(ratingProfileVoucherDto.getCategoryNameDtoList().toString() != null
					? ratingProfileVoucherDto.getCategoryNameDtoList().toString()
					: "");
			ratingProfileVoucher.setRatesOffer(ratingProfileVoucherDto.getRatesOfferDtoList().toString() != null
					? ratingProfileVoucherDto.getRatesOfferDtoList().toString()
					: "");
			ratingProfileVoucher.setCallBalance(
					ratingProfileVoucherDto.getCallBalance() != null ? ratingProfileVoucherDto.getCallBalance()
							: Integer.valueOf(""));
			ratingProfileVoucher.setSmsBalance(
					ratingProfileVoucherDto.getSmsBalance() != null ? ratingProfileVoucherDto.getSmsBalance()
							: Integer.valueOf(""));
			ratingProfileVoucher.setDataBalance(ratingProfileVoucherDto.getDataBalance());
			ratingProfileVoucherRepository.save(ratingProfileVoucher);
			List<String> categoryList = convertStringToList(ratingProfileVoucher.getCategoryOfferList());
			List<String> ratesOfferList = convertStringToList(ratingProfileVoucher.getRatesOfferList());
			RatingProfileVoucherDto ratingProfileDtoNew = new RatingProfileVoucherDto(ratingProfileVoucher.getId(),
					ratingProfileVoucher.getPackName(), ratingProfileVoucher.getPackType(),
					ratingProfileVoucherDto.getCallBalance(), ratingProfileVoucherDto.getSmsBalance(),
					ratingProfileVoucherDto.getDataBalance(), categoryList, ratesOfferList);
			return new ResponseEntity<>(ratingProfileDtoNew, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new CustomMessage(HttpStatus.CONFLICT.value(), "Duplicate Rating Profile Id"));
	}

	@Override
	public ResponseEntity editRatingProfileVoucher(Integer ratingProfileId,
			RatingProfileVoucherDto ratingProfileVoucherDto) {
		Optional<RatingProfileVoucher> ratingProfileVoucher = ratingProfileVoucherRepository.findById(ratingProfileId);
		if (ratingProfileVoucher.isPresent()) {
			RatingProfileVoucher ratingProfileVoucherDb = ratingProfileVoucher.get();
			ratingProfileVoucherDb
					.setPackName(ratingProfileVoucherDto.getPackName() != null ? ratingProfileVoucherDto.getPackName()
							: ratingProfileVoucherDb.getPackName());
			ratingProfileVoucherDb
					.setPackType(ratingProfileVoucherDto.getPackType() != null ? ratingProfileVoucherDto.getPackType()
							: ratingProfileVoucherDb.getPackType());
			ratingProfileVoucherDb.setCategoryOffer(ratingProfileVoucherDto.getCategoryNameDtoList().toString() != null
					? ratingProfileVoucherDto.getCategoryNameDtoList().toString()
					: ratingProfileVoucherDb.getCategoryOfferList());
			ratingProfileVoucherDb.setRatesOffer(ratingProfileVoucherDto.getRatesOfferDtoList().toString() != null
					? ratingProfileVoucherDto.getRatesOfferDtoList().toString()
					: ratingProfileVoucherDb.getRatesOfferList());
			ratingProfileVoucherDb.setCallBalance(
					ratingProfileVoucherDto.getCallBalance() != null ? ratingProfileVoucherDto.getCallBalance()
							: ratingProfileVoucherDb.getCallBalance());
			ratingProfileVoucherDb.setSmsBalance(
					ratingProfileVoucherDto.getSmsBalance() != null ? ratingProfileVoucherDto.getSmsBalance()
							: ratingProfileVoucherDb.getSmsBalance());
			ratingProfileVoucherDb.setDataBalance(
					ratingProfileVoucherDto.getDataBalance() != null ? ratingProfileVoucherDto.getDataBalance()
							: ratingProfileVoucherDb.getDataBalance());
			ratingProfileVoucherRepository.save(ratingProfileVoucherDb);
			List<String> categoryList = convertStringToList(ratingProfileVoucherDb.getCategoryOfferList());
			List<String> ratesOfferList = convertStringToList(ratingProfileVoucherDb.getRatesOfferList());
			RatingProfileVoucherDto ratingProfileVoucherDtoNew = new RatingProfileVoucherDto(
					ratingProfileVoucherDb.getId(), ratingProfileVoucherDb.getPackName(),
					ratingProfileVoucherDb.getPackType(), ratingProfileVoucherDb.getCallBalance(),
					ratingProfileVoucherDb.getSmsBalance(), ratingProfileVoucherDb.getDataBalance(), categoryList,
					ratesOfferList);
			return new ResponseEntity<>(ratingProfileVoucherDtoNew, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid Rating Profile Id"));
	}

	@Override
	public List<RatingProfileVoucherDto> getAllRatingProfileVoucher() {
		List<RatingProfileVoucher> ratingProfileVoucherDbList = ratingProfileVoucherRepository.findAll();
		List<RatingProfileVoucherDto> ratingProfileVoucherDtoList = new ArrayList<>();
		for (RatingProfileVoucher ratingProfileVoucher : ratingProfileVoucherDbList) {
			RatingProfileVoucherDto ratingProfileVoucherDto = new RatingProfileVoucherDto();
			ratingProfileVoucherDto.setRatingProfileId(ratingProfileVoucher.getId());
			ratingProfileVoucherDto.setPackName(ratingProfileVoucher.getPackName());
			ratingProfileVoucherDto.setPackType(ratingProfileVoucher.getPackType());
			ratingProfileVoucherDto.setCallBalance(ratingProfileVoucher.getCallBalance());
			ratingProfileVoucherDto.setSmsBalance(ratingProfileVoucher.getSmsBalance());
			ratingProfileVoucherDto.setDataBalance(ratingProfileVoucher.getDataBalance());
			ratingProfileVoucherDto
					.setCategoryNameDtoList(convertStringToList(ratingProfileVoucher.getCategoryOfferList()));
			ratingProfileVoucherDto.setRatesOfferDtoList(convertStringToList(ratingProfileVoucher.getRatesOfferList()));
			ratingProfileVoucherDtoList.add(ratingProfileVoucherDto);
		}
		return ratingProfileVoucherDtoList;
	}

	@Override
	public ResponseEntity getRatingProfileVoucher(Integer ratingProfileId) {
		Optional<RatingProfileVoucher> ratingProfileVoucherDb = ratingProfileVoucherRepository
				.findById(ratingProfileId);
		if (ratingProfileVoucherDb.isPresent()) {
			RatingProfileVoucher ratingProfileVoucher = ratingProfileVoucherDb.get();
			RatingProfileVoucherDto ratingProfileVoucherDto = new RatingProfileVoucherDto();
			ratingProfileVoucherDto.setRatingProfileId(ratingProfileVoucher.getId());
			ratingProfileVoucherDto.setPackName(ratingProfileVoucher.getPackName());
			ratingProfileVoucherDto.setPackType(ratingProfileVoucher.getPackType());
			ratingProfileVoucherDto.setCallBalance(ratingProfileVoucher.getCallBalance());
			ratingProfileVoucherDto.setSmsBalance(ratingProfileVoucher.getSmsBalance());
			ratingProfileVoucherDto.setDataBalance(ratingProfileVoucher.getDataBalance());
			ratingProfileVoucherDto
					.setCategoryNameDtoList(convertStringToList(ratingProfileVoucher.getCategoryOfferList()));
			ratingProfileVoucherDto.setRatesOfferDtoList(convertStringToList(ratingProfileVoucher.getRatesOfferList()));
			return new ResponseEntity<>(ratingProfileVoucherDto, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid Rating Profile Id"));
	}

	@Transactional
	@Override
	public ResponseEntity deleteRatingProfileVoucher(Integer ratingProfileId) {
		Optional<RatingProfileVoucher> ratingProfileVoucherDb = ratingProfileVoucherRepository
				.findById(ratingProfileId);
		if (ratingProfileVoucherDb.isPresent()) {
			ratingProfileVoucherRepository.deleteById(ratingProfileId);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new CustomMessage(HttpStatus.OK.value(), "Deleted Successfully..."));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid Rating Profile Id"));
	}

	private static List<String> convertStringToList(String data) {
		String[] dataArray = data.split(",");
		return Arrays.asList(dataArray);
	}
}
