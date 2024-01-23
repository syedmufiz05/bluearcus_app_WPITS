package com.bluearcus.service;

import com.bluearcus.dto.RatesPlanOfferDto;
import com.bluearcus.exception.CustomMessage;
import com.bluearcus.model.RatesPlanOffer;
import com.bluearcus.repo.RatesPlanOfferRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RatesPlanOfferServiceImpl implements RatesPlanOfferService {
	@Autowired
	private RatesPlanOfferRepository ratesPlanOfferRepository;

	@Override
	public ResponseEntity saveRatesPlanDetail(RatesPlanOfferDto ratesPlanOfferDto) {
		Optional<RatesPlanOffer> ratesPlanOffer = ratesPlanOfferRepository.findById(
				ratesPlanOfferDto.getRatesPlanOfferId() != null ? ratesPlanOfferDto.getRatesPlanOfferId() : 0);
		if (!ratesPlanOffer.isPresent()) {
			RatesPlanOffer ratesPlanOfferDb = new RatesPlanOffer();
			ratesPlanOfferDb.setName(ratesPlanOfferDto.getName() != null ? ratesPlanOfferDto.getName() : "");
			ratesPlanOfferDb.setPeriod(
					ratesPlanOfferDto.getPeriod() != null ? ratesPlanOfferDto.getPeriod() : Integer.valueOf(""));
			ratesPlanOfferDb.setDescription(
					ratesPlanOfferDto.getDescription() != null ? ratesPlanOfferDto.getDescription() : "");
			ratesPlanOfferDb.setActive(ratesPlanOfferDto.getActive() != null ? ratesPlanOfferDto.getActive() : false);
			ratesPlanOfferRepository.save(ratesPlanOfferDb);
			RatesPlanOfferDto ratesPlanOfferDtoNew = new RatesPlanOfferDto(ratesPlanOfferDb.getId(),
					ratesPlanOfferDb.getName(), ratesPlanOfferDb.getPeriod(), ratesPlanOfferDb.getDescription(),
					ratesPlanOfferDb.getActive());
			return new ResponseEntity<>(ratesPlanOfferDtoNew, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new CustomMessage(HttpStatus.CONFLICT.value(), "Rating Plan Id already exist"));
	}

	@Override
	public List<String> getAllRatesPlansBulk() {
		List<RatesPlanOfferDto> ratesPlanOfferDtoList = ratesPlanOfferRepository.fetchAllRatesPlan();
		List<String> ratesPlanVoucherList = new ArrayList<>();
		for (RatesPlanOfferDto ratesPlanOfferDto : ratesPlanOfferDtoList) {
			ratesPlanOfferDto.setDescription(ratesPlanOfferDto.getName() + " for " + ratesPlanOfferDto.getPeriod() + " days");
			ratesPlanVoucherList.add(ratesPlanOfferDto.getDescription());
		}
		return ratesPlanVoucherList;
	}

	@Override
	public ResponseEntity getRatesPlanDetail(Integer ratesPlanOfferId) {
		Optional<RatesPlanOffer> ratesPlanOffer = ratesPlanOfferRepository.findById(ratesPlanOfferId);
		if (ratesPlanOffer.isPresent()) {
			RatesPlanOffer ratesPlanOfferDb = ratesPlanOffer.get();
			RatesPlanOfferDto ratesPlanOfferDto = new RatesPlanOfferDto();
			ratesPlanOfferDto.setRatesPlanOfferId(ratesPlanOfferDb.getId());
			ratesPlanOfferDto.setName(ratesPlanOfferDb.getName());
			ratesPlanOfferDto.setPeriod(ratesPlanOfferDb.getPeriod());
			ratesPlanOfferDto.setDescription(ratesPlanOfferDb.getDescription());
			ratesPlanOfferDto.setActive(ratesPlanOfferDb.getActive());
			return new ResponseEntity<>(ratesPlanOfferDto, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Rating Plan Id does n't exist"));
	}

	@Override
	public List<RatesPlanOfferDto> getAllRatesPlan() {
		return ratesPlanOfferRepository.fetchAllRatesPlan();
	}

	@Override
	public ResponseEntity editRatesPlanDetail(Integer ratesPlanOfferId, RatesPlanOfferDto ratesPlanOfferDto) {
		Optional<RatesPlanOffer> ratesPlanOffer = ratesPlanOfferRepository.findById(ratesPlanOfferId);
		if (ratesPlanOffer.isPresent()) {
			RatesPlanOffer ratesPlanOfferDb = ratesPlanOffer.get();
			ratesPlanOfferDb.setName(
					ratesPlanOfferDto.getName() != null ? ratesPlanOfferDto.getName() : ratesPlanOfferDb.getName());
			ratesPlanOfferDb.setPeriod(ratesPlanOfferDto.getPeriod() != null ? ratesPlanOfferDto.getPeriod()
					: ratesPlanOfferDb.getPeriod());
			ratesPlanOfferDb
					.setDescription(ratesPlanOfferDto.getDescription() != null ? ratesPlanOfferDto.getDescription()
							: ratesPlanOfferDb.getDescription());
			ratesPlanOfferDb.setActive(ratesPlanOfferDto.getActive() != null ? ratesPlanOfferDto.getActive()
					: ratesPlanOfferDb.getActive());
			ratesPlanOfferRepository.save(ratesPlanOfferDb);
			RatesPlanOfferDto ratesPlanOfferDtoNew = new RatesPlanOfferDto(ratesPlanOfferDb.getId(),
					ratesPlanOfferDb.getName(), ratesPlanOfferDb.getPeriod(), ratesPlanOfferDb.getDescription(),
					ratesPlanOfferDb.getActive());
			return new ResponseEntity<>(ratesPlanOfferDtoNew, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Rating Plan Id does n't exist"));
	}

	@Transactional
	@Override
	public ResponseEntity deleteRatesPlanDetail(Integer ratesPlanOfferId) {
		Optional<RatesPlanOffer> ratesPlanOffer = ratesPlanOfferRepository.findById(ratesPlanOfferId);
		if (ratesPlanOffer.isPresent()) {
			ratesPlanOfferRepository.deleteById(ratesPlanOfferId);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new CustomMessage(HttpStatus.OK.value(), "Deleted Successfully..."));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Rating Plan Id does n't exist"));
	}
}
