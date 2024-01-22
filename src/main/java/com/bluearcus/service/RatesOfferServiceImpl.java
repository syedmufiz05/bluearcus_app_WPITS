package com.bluearcus.service;

import com.bluearcus.dto.RatesOfferDto;
import com.bluearcus.exception.CustomMessage;
import com.bluearcus.model.RatesOffer;
import com.bluearcus.repo.RatesOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RatesOfferServiceImpl implements RatesOfferService {
	@Autowired
	private RatesOfferRepository ratesOfferRepository;

	@Override
	public ResponseEntity saveRatesOffer(RatesOfferDto ratesOfferDto) {
		Optional<RatesOffer> ratesOffer = ratesOfferRepository
				.findById(ratesOfferDto.getRatesId() != null ? ratesOfferDto.getRatesId() : 0);
		if (!ratesOffer.isPresent()) {
			RatesOffer ratesOfferDb = new RatesOffer();
			ratesOfferDb.setPrice(ratesOfferDto.getPrice() != null ? ratesOfferDto.getPrice() : Integer.valueOf(""));
			ratesOfferDb.setPriceType(ratesOfferDto.getPriceType() != null ? ratesOfferDto.getPriceType() : "");
			ratesOfferDb.setPeriod(ratesOfferDto.getPeriod() != null ? ratesOfferDto.getPeriod() : Integer.valueOf(""));
			ratesOfferDb.setDescription(ratesOfferDto.getDescription() != null ? ratesOfferDto.getDescription() : "");
			ratesOfferRepository.save(ratesOfferDb);
			RatesOfferDto ratesOfferDtoNew = new RatesOfferDto(ratesOfferDb.getId(), ratesOfferDb.getPrice(),
					ratesOfferDb.getPriceType(), ratesOfferDb.getPeriod(), ratesOfferDb.getDescription());
			return new ResponseEntity<>(ratesOfferDtoNew, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new CustomMessage(HttpStatus.CONFLICT.value(), "Rates Id already exist"));
	}

	@Override
	public List<String> getAllRatesOfferBulk() {
		List<RatesOfferDto> ratesOfferDtoList = ratesOfferRepository.fecthAllRatesOffer();
		List<String> ratesVoucherList = new ArrayList<>();
		for (RatesOfferDto ratesOfferDto : ratesOfferDtoList) {
			if (ratesOfferDto.getPrice() == 99) {
				ratesOfferDto.setDescription(ratesOfferDto.getPrice() + " for " + ratesOfferDto.getPeriod() + " days");
				ratesVoucherList.add(ratesOfferDto.getDescription());
			}
			if (ratesOfferDto.getPrice() == 199) {
				ratesOfferDto.setDescription(ratesOfferDto.getPrice() + " for " + ratesOfferDto.getPeriod() + " days");
				ratesVoucherList.add(ratesOfferDto.getDescription());
			}
			if (ratesOfferDto.getPrice() == 299) {
				ratesOfferDto.setDescription(ratesOfferDto.getPrice() + " for " + ratesOfferDto.getPeriod() + " days");
				ratesVoucherList.add(ratesOfferDto.getDescription());
			}
			if (ratesOfferDto.getPrice() == 399) {
				ratesOfferDto.setDescription(ratesOfferDto.getPrice() + " for " + ratesOfferDto.getPeriod() + " days");
				ratesVoucherList.add(ratesOfferDto.getDescription());
			}
			if (ratesOfferDto.getPrice() == 499) {
				ratesOfferDto.setDescription(ratesOfferDto.getPrice() + " for " + ratesOfferDto.getPeriod() + " days");
				ratesVoucherList.add(ratesOfferDto.getDescription());
			}
		}
		return ratesVoucherList;
	}

	@Override
	public List<RatesOfferDto> getAllRatesOffer() {
		return ratesOfferRepository.fecthAllRatesOffer();
	}

	@Override
	public ResponseEntity getRatesOffer(Integer ratesId) {
		Optional<RatesOffer> ratesOffer = ratesOfferRepository.findById(ratesId);
		if (ratesOffer.isPresent()) {
			RatesOffer ratesOfferDb = ratesOffer.get();
			RatesOfferDto ratesOfferDto = new RatesOfferDto();
			ratesOfferDto.setRatesId(ratesOfferDb.getId());
			ratesOfferDto.setPrice(ratesOfferDb.getPrice());
			ratesOfferDto.setPriceType(ratesOfferDb.getPriceType());
			ratesOfferDto.setPeriod(ratesOfferDb.getPeriod());
			ratesOfferDto.setDescription(ratesOfferDb.getDescription());
			return new ResponseEntity<>(ratesOfferDto, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Rates Id does n't exist"));
	}

	@Override
	public List<String> getAllCurrencyISOCodes() {
		List<String> currencyISOCodes = new ArrayList<>();
		Set<Currency> currencies = Currency.getAvailableCurrencies();
		for (Currency currency : currencies) {
			currencyISOCodes.add(currency.getDisplayName() + " " + currency.getCurrencyCode());
		}
		return currencyISOCodes;
	}
}
