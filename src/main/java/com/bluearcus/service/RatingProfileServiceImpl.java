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
		Optional<RatingProfileVoucher> ratingProfileVoucherDb = ratingProfileVoucherRepository.findById(ratingProfileVoucherDto.getRatingProfileId() != null ? ratingProfileVoucherDto.getRatingProfileId() : 0);
		if (!ratingProfileVoucherDb.isPresent()) {
			RatingProfileVoucher ratingProfileVoucher = new RatingProfileVoucher();
			ratingProfileVoucher.setPackName(ratingProfileVoucherDto.getPackName() != null ? ratingProfileVoucherDto.getPackName() : "");
			ratingProfileVoucher.setPackType(ratingProfileVoucherDto.getPackType() != null ? ratingProfileVoucherDto.getPackType() : "");
			ratingProfileVoucher.setCategoryName(ratingProfileVoucherDto.getCategoryName() != null ? ratingProfileVoucherDto.getCategoryName() : "");
			ratingProfileVoucher.setRatesOffer(ratingProfileVoucherDto.getRatesOffer() != null ? ratingProfileVoucherDto.getRatesOffer() : "");
			ratingProfileVoucher.setCallBalance(ratingProfileVoucherDto.getCallBalance() != null ? ratingProfileVoucherDto.getCallBalance() : 0);
			ratingProfileVoucher.setCallBalanceParameter(ratingProfileVoucherDto.getCallBalanceParameter() != null ? ratingProfileVoucherDto.getCallBalanceParameter() : "");
			ratingProfileVoucher.setSmsBalance(ratingProfileVoucherDto.getSmsBalance() != null ? ratingProfileVoucherDto.getSmsBalance() : 0);
			ratingProfileVoucher.setDataBalance(ratingProfileVoucherDto.getDataBalance() != null ? ratingProfileVoucherDto.getDataBalance() : 0);
			ratingProfileVoucher.setDataBalanceParameter(ratingProfileVoucherDto.getDataBalanceParameter() != null ? ratingProfileVoucherDto.getDataBalanceParameter() : "");
			ratingProfileVoucherRepository.save(ratingProfileVoucher);
		
			
			RatingProfileVoucherDto ratingProfileDtoNew = new RatingProfileVoucherDto(ratingProfileVoucher.getId(),
					ratingProfileVoucher.getPackName(), ratingProfileVoucher.getPackType(),
					ratingProfileVoucher.getCallBalance(), ratingProfileVoucher.getCallBalanceParameter(),
					ratingProfileVoucher.getSmsBalance(), ratingProfileVoucher.getDataBalance(),
					ratingProfileVoucher.getDataBalanceParameter(), ratingProfileVoucher.getCategoryName(),
					ratingProfileVoucher.getRatesOffer());
			return new ResponseEntity<>(ratingProfileDtoNew, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomMessage(HttpStatus.CONFLICT.value(), "Duplicate Rating Profile Id"));
	}

	@Override
	public ResponseEntity editRatingProfileVoucher(Integer ratingProfileId , RatingProfileVoucherDto ratingProfileVoucherDto) {
		Optional<RatingProfileVoucher> ratingProfileVoucher = ratingProfileVoucherRepository.findById(ratingProfileId);
		if (ratingProfileVoucher.isPresent()) {
			RatingProfileVoucher ratingProfileVoucherDb = ratingProfileVoucher.get();
			ratingProfileVoucherDb.setPackName(ratingProfileVoucherDto.getPackName() != null ? ratingProfileVoucherDto.getPackName() : ratingProfileVoucherDb.getPackName());
			ratingProfileVoucherDb.setPackType(ratingProfileVoucherDto.getPackType() != null ? ratingProfileVoucherDto.getPackType() : ratingProfileVoucherDb.getPackType());
			ratingProfileVoucherDb.setCategoryName(ratingProfileVoucherDto.getCategoryName() != null ? ratingProfileVoucherDto.getCategoryName() :ratingProfileVoucherDb.getCategoryName());
			ratingProfileVoucherDb.setRatesOffer(ratingProfileVoucherDto.getRatesOffer() != null ? ratingProfileVoucherDto.getRatesOffer() : ratingProfileVoucherDb.getRatesOffer());
			ratingProfileVoucherDb.setCallBalance(ratingProfileVoucherDto.getCallBalance() != null ? ratingProfileVoucherDto.getCallBalance() : ratingProfileVoucherDb.getCallBalance());
			ratingProfileVoucherDb.setCallBalanceParameter(ratingProfileVoucherDto.getCallBalanceParameter() != null ? ratingProfileVoucherDto.getCallBalanceParameter() :ratingProfileVoucherDb.getCallBalanceParameter());
			ratingProfileVoucherDb.setSmsBalance(ratingProfileVoucherDto.getSmsBalance() != null ? ratingProfileVoucherDto.getSmsBalance() : ratingProfileVoucherDb.getSmsBalance());
			ratingProfileVoucherDb.setDataBalance(ratingProfileVoucherDto.getDataBalance() != null ? ratingProfileVoucherDto.getDataBalance() : ratingProfileVoucherDb.getDataBalance());
			ratingProfileVoucherDb.setDataBalanceParameter(ratingProfileVoucherDto.getDataBalanceParameter() != null ? ratingProfileVoucherDto.getDataBalanceParameter() : ratingProfileVoucherDb.getDataBalanceParameter());
			ratingProfileVoucherRepository.save(ratingProfileVoucherDb);
			
			RatingProfileVoucherDto ratingProfileVoucherDtoNew = new RatingProfileVoucherDto(
					ratingProfileVoucherDb.getId(), ratingProfileVoucherDb.getPackName(),
					ratingProfileVoucherDb.getPackType(), ratingProfileVoucherDb.getCallBalance(),
					ratingProfileVoucherDb.getCallBalanceParameter(), ratingProfileVoucherDb.getSmsBalance(),
					ratingProfileVoucherDb.getDataBalance(), ratingProfileVoucherDb.getDataBalanceParameter(),
					ratingProfileVoucherDb.getCategoryName(), ratingProfileVoucherDb.getRatesOffer());
			return new ResponseEntity<>(ratingProfileVoucherDtoNew, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid Rating Profile Id"));
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
			ratingProfileVoucherDto.setCallBalanceParameter(ratingProfileVoucher.getCallBalanceParameter());
			ratingProfileVoucherDto.setSmsBalance(ratingProfileVoucher.getSmsBalance());
			ratingProfileVoucherDto.setDataBalance(ratingProfileVoucher.getDataBalance());
			ratingProfileVoucherDto.setDataBalanceParameter(ratingProfileVoucher.getDataBalanceParameter());
			ratingProfileVoucherDto.setCategoryName(ratingProfileVoucher.getCategoryName());
			ratingProfileVoucherDto.setRatesOffer(ratingProfileVoucher.getRatesOffer());
			ratingProfileVoucherDtoList.add(ratingProfileVoucherDto);
		}
		return ratingProfileVoucherDtoList;
	}

	@Override
	public ResponseEntity getRatingProfileVoucher(Integer ratingProfileId) {
		Optional<RatingProfileVoucher> ratingProfileVoucherDb = ratingProfileVoucherRepository.findById(ratingProfileId);
		if (ratingProfileVoucherDb.isPresent()) {
			RatingProfileVoucher ratingProfileVoucher = ratingProfileVoucherDb.get();
			RatingProfileVoucherDto ratingProfileVoucherDto = new RatingProfileVoucherDto();
			ratingProfileVoucherDto.setRatingProfileId(ratingProfileVoucher.getId());
			ratingProfileVoucherDto.setPackName(ratingProfileVoucher.getPackName());
			ratingProfileVoucherDto.setPackType(ratingProfileVoucher.getPackType());
			ratingProfileVoucherDto.setCallBalance(ratingProfileVoucher.getCallBalance());
			ratingProfileVoucherDto.setCallBalanceParameter(ratingProfileVoucher.getCallBalanceParameter());
			ratingProfileVoucherDto.setSmsBalance(ratingProfileVoucher.getSmsBalance());
			ratingProfileVoucherDto.setDataBalance(ratingProfileVoucher.getDataBalance());
			ratingProfileVoucherDto.setDataBalanceParameter(ratingProfileVoucher.getDataBalanceParameter());
			ratingProfileVoucherDto.setCategoryName(ratingProfileVoucher.getCategoryName());
			ratingProfileVoucherDto.setRatesOffer(ratingProfileVoucher.getRatesOffer());
			return new ResponseEntity<>(ratingProfileVoucherDto, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid Rating Profile Id"));
	}
	
	@Override
	public ResponseEntity getRatingProfileVoucherByPackName(String packName) {
		Optional<RatingProfileVoucher> ratingProfileVoucherDb = ratingProfileVoucherRepository.findByPackName(packName);
		if (ratingProfileVoucherDb.isPresent()) {
			RatingProfileVoucher ratingProfileVoucher = ratingProfileVoucherDb.get();
			RatingProfileVoucherDto ratingProfileVoucherDto = new RatingProfileVoucherDto();
			ratingProfileVoucherDto.setRatingProfileId(ratingProfileVoucher.getId());
			ratingProfileVoucherDto.setPackName(ratingProfileVoucher.getPackName());
			ratingProfileVoucherDto.setPackType(ratingProfileVoucher.getPackType());
			ratingProfileVoucherDto.setCallBalance(ratingProfileVoucher.getCallBalance());
			ratingProfileVoucherDto.setCallBalanceParameter(ratingProfileVoucher.getCallBalanceParameter());
			ratingProfileVoucherDto.setSmsBalance(ratingProfileVoucher.getSmsBalance());
			ratingProfileVoucherDto.setDataBalance(ratingProfileVoucher.getDataBalance());
			ratingProfileVoucherDto.setDataBalanceParameter(ratingProfileVoucher.getDataBalanceParameter());
			ratingProfileVoucherDto.setCategoryName(ratingProfileVoucher.getCategoryName());
			ratingProfileVoucherDto.setRatesOffer(ratingProfileVoucher.getRatesOffer());
			return new ResponseEntity<>(ratingProfileVoucherDto, HttpStatus.OK);	
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid pack name"));
	}

	@Transactional
	@Override
	public ResponseEntity deleteRatingProfileVoucher(Integer ratingProfileId) {
		Optional<RatingProfileVoucher> ratingProfileVoucherDb = ratingProfileVoucherRepository.findById(ratingProfileId);
		if (ratingProfileVoucherDb.isPresent()) {
			ratingProfileVoucherRepository.deleteById(ratingProfileId);
			return ResponseEntity.status(HttpStatus.OK).body(new CustomMessage(HttpStatus.OK.value(), "Deleted Successfully..."));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid Rating Profile Id"));
	}
	
	@Override
	public List<RatingProfileVoucherDto> searchVoucherByName(String keyword) {
		List<RatingProfileVoucher> ratingProfileVoucherDb = ratingProfileVoucherRepository.searchVoucherByName(keyword);
		List<RatingProfileVoucherDto> ratingProfileVoucherDtoList = new ArrayList<>();
		for (RatingProfileVoucher ratingProfileVoucher : ratingProfileVoucherDb) {
			RatingProfileVoucherDto ratingProfileVoucherDto = new RatingProfileVoucherDto();
			ratingProfileVoucherDto.setRatingProfileId(ratingProfileVoucher.getId());
			ratingProfileVoucherDto.setPackName(ratingProfileVoucher.getPackName());
			ratingProfileVoucherDto.setPackType(ratingProfileVoucher.getPackType());
			ratingProfileVoucherDto.setCallBalance(ratingProfileVoucher.getCallBalance());
			ratingProfileVoucherDto.setCallBalanceParameter(ratingProfileVoucher.getCallBalanceParameter());
			ratingProfileVoucherDto.setSmsBalance(ratingProfileVoucher.getSmsBalance());
			ratingProfileVoucherDto.setDataBalance(ratingProfileVoucher.getDataBalance());
			ratingProfileVoucherDto.setDataBalanceParameter(ratingProfileVoucher.getDataBalanceParameter());
			ratingProfileVoucherDto.setCategoryName(ratingProfileVoucher.getCategoryName());
			ratingProfileVoucherDto.setRatesOffer(ratingProfileVoucher.getRatesOffer());
			ratingProfileVoucherDtoList.add(ratingProfileVoucherDto);
		}
		return ratingProfileVoucherDtoList;
	}
	
	@Override
	public List<RatingProfileVoucherDto> getVoucherByCtgName(String ctgName) {
		List<RatingProfileVoucher> ratingProfileVouchersDb = ratingProfileVoucherRepository.findByCategoryName(ctgName);
		List<RatingProfileVoucherDto> ratingProfileVoucherDtoList = new ArrayList<>();
		for (RatingProfileVoucher ratingProfileVoucher : ratingProfileVouchersDb) {
			RatingProfileVoucherDto ratingProfileVoucherDto = new RatingProfileVoucherDto();
			ratingProfileVoucherDto.setRatingProfileId(ratingProfileVoucher.getId());
			ratingProfileVoucherDto.setPackName(ratingProfileVoucher.getPackName());
			ratingProfileVoucherDto.setPackType(ratingProfileVoucher.getPackType());
			ratingProfileVoucherDto.setCallBalance(ratingProfileVoucher.getCallBalance());
			ratingProfileVoucherDto.setCallBalanceParameter(ratingProfileVoucher.getCallBalanceParameter());
			ratingProfileVoucherDto.setSmsBalance(ratingProfileVoucher.getSmsBalance());
			ratingProfileVoucherDto.setDataBalance(ratingProfileVoucher.getDataBalance());
			ratingProfileVoucherDto.setDataBalanceParameter(ratingProfileVoucher.getDataBalanceParameter());
			ratingProfileVoucherDto.setCategoryName(ratingProfileVoucher.getCategoryName());
			ratingProfileVoucherDto.setRatesOffer(ratingProfileVoucher.getRatesOffer());
			ratingProfileVoucherDtoList.add(ratingProfileVoucherDto);
		}
		return ratingProfileVoucherDtoList;
	}
	
	private static List<String> convertStringToList(String data) {
		String filteredData = data.replaceAll("[\\[\\]\"]", "");
		String[] dataArray = filteredData.split(",");
		return Arrays.asList(dataArray);
	}

	@Override
	public List<String> getAllDataBalanceParams() {
		List<String> dataBalance = new ArrayList<String>();
		dataBalance.add("GB");
		dataBalance.add("MB");
		dataBalance.add("KB");
		return dataBalance;
	}

	@Override
	public List<String> getAllCallBalanceParams() {
		List<String> callBalance = new ArrayList<String>();
		callBalance.add("Hours");
		callBalance.add("Mins");
		callBalance.add("Seconds");
		return callBalance;
	}

}
