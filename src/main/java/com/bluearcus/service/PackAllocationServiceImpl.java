package com.bluearcus.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bluearcus.dto.PackAllocationDto;
import com.bluearcus.exception.CustomMessage;
import com.bluearcus.model.PackAllocationPostpaid;
import com.bluearcus.model.PackAllocationPrepaid;
import com.bluearcus.model.PostpaidAccounts;
import com.bluearcus.model.PrepaidAccounts;
import com.bluearcus.model.RatingProfileVoucher;
import com.bluearcus.repo.PackAllocationPostpaidRepo;
import com.bluearcus.repo.PackAllocationPrepaidRepo;
import com.bluearcus.repo.PostpaidAccountsRepo;
import com.bluearcus.repo.PrepaidAccountsRepository;
import com.bluearcus.repo.RatingProfileRepository;
import com.bluearcus.repo.RatingProfileVoucherRepository;

@Service
public class PackAllocationServiceImpl implements PackAllocationService {

	@Autowired
	private PackAllocationPrepaidRepo packAllocationPrepaidRepo;
	
	@Autowired
	private PackAllocationPostpaidRepo packAllocationPostpaidRepo;
	
	@Autowired
	private PrepaidAccountsRepository prepaidAccountsRepository;
	
	@Autowired
	private PostpaidAccountsRepo postpaidAccountsRepo;

	@Autowired
	private RatingProfileVoucherRepository ratingProfileVoucherRepository;

	@Override
	public ResponseEntity packAllocationForPrepaid(PackAllocationDto packAllocationDto) {
		Optional<RatingProfileVoucher> ratingProfileVoucher = ratingProfileVoucherRepository.findById(packAllocationDto.getPackId());
		if (ratingProfileVoucher.isPresent()) {
			
			String activationDateDto, expirationDateDto;
			PackAllocationPrepaid packAllocationPrepaid = null;

			RatingProfileVoucher ratingProfileVoucherDb = ratingProfileVoucher.get();

			int packActivationDays = findIntIntoString(ratingProfileVoucherDb.getRatesOffer());
			
			Optional<PackAllocationPrepaid> prepaidPack = packAllocationPrepaidRepo.findByCustomerId(packAllocationDto.getCustomerId());
			
			if (prepaidPack.isPresent()) {
				packAllocationPrepaid = prepaidPack.get();

				packAllocationPrepaid.setActivationDate(new Date());

				LocalDateTime activationDate = CallSessionUsageServiceImpl.convertDateToLocalDateTime(packAllocationPrepaid.getActivationDate());

				LocalDateTime expirationDate = activationDate.plusDays(packActivationDays);

				Date expirationDateDb = CallSessionUsageServiceImpl.convertLocalDateTimeToDate(expirationDate);

				packAllocationPrepaid.setExpirationDate(expirationDateDb);

				activationDateDto = CallSessionUsageServiceImpl.fetchReadableDateTime(packAllocationPrepaid.getActivationDate());

				expirationDateDto = CallSessionUsageServiceImpl.fetchReadableDateTime(expirationDateDb);
				
			}
			else {

				packAllocationPrepaid = new PackAllocationPrepaid();

				packAllocationPrepaid.setActivationDate(new Date());

				LocalDateTime activationDate = CallSessionUsageServiceImpl.convertDateToLocalDateTime(packAllocationPrepaid.getActivationDate());

				LocalDateTime expirationDate = activationDate.plusDays(packActivationDays);

				Date expirationDateDb = CallSessionUsageServiceImpl.convertLocalDateTimeToDate(expirationDate);

				packAllocationPrepaid.setExpirationDate(expirationDateDb);

				activationDateDto = CallSessionUsageServiceImpl.fetchReadableDateTime(packAllocationPrepaid.getActivationDate());

				expirationDateDto = CallSessionUsageServiceImpl.fetchReadableDateTime(expirationDateDb);
			}

			Optional<PrepaidAccounts> prepaidAccountDb = prepaidAccountsRepository.findByImsi(packAllocationDto.getImsi());

			if (prepaidAccountDb.isPresent()) {
				PrepaidAccounts prepaidAccount = prepaidAccountDb.get();

				if (ratingProfileVoucherDb.getDataBalanceParameter().equalsIgnoreCase("GB")) {
					prepaidAccount.setTotalDataOctetsAvailable(convertGigabytesToBytes(ratingProfileVoucherDb.getDataBalance().longValue()));
				}

				else if (ratingProfileVoucherDb.getDataBalanceParameter().equalsIgnoreCase("MB")) {
					prepaidAccount.setTotalDataOctetsAvailable(convertMegabytesToBytes(ratingProfileVoucherDb.getDataBalance().longValue()));
				}

				else {
					prepaidAccount.setTotalDataOctetsAvailable(convertKilobytesToBytes(ratingProfileVoucherDb.getDataBalance().longValue()));
				}

				prepaidAccount.setTotalCallSecondsAvailable(convertMinsToSeconds(ratingProfileVoucherDb.getCallBalance().longValue()));
				prepaidAccount.setTotalSmsAvailable(ratingProfileVoucherDb.getSmsBalance().longValue());
				prepaidAccount.setTotalDataOctetsConsumed(0L);
				prepaidAccount.setTotalOutputDataOctetsAvailable(0L);
				prepaidAccount.setTotalInputDataOctetsAvailable(0L);
				prepaidAccount.setTotalCallSecondsConsumed(0L);
				prepaidAccount.setTotalSmsConsumed(0L);
				prepaidAccountsRepository.save(prepaidAccount);

				packAllocationPrepaid.setImsi(prepaidAccount.getImsi());
				packAllocationPrepaid.setMsisdn(prepaidAccount.getMsisdn());
				packAllocationPrepaid.setPackName(ratingProfileVoucherDb.getPackName());
				packAllocationPrepaid.setCustomerId(prepaidAccount.getCustomerId());

				packAllocationPrepaidRepo.save(packAllocationPrepaid);

				PackAllocationDto packAllocationDtoNew = new PackAllocationDto(packAllocationPrepaid.getId(),
						packAllocationDto.getMsisdn(), packAllocationDto.getImsi(), activationDateDto,
						expirationDateDto, ratingProfileVoucherDb.getId(), ratingProfileVoucherDb.getPackName(),
						prepaidAccount.getCustomerId());

				return new ResponseEntity<>(packAllocationDtoNew, HttpStatus.OK);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid IMSI"));
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Please select the valid pack"));
	}
	
	@Override
	public ResponseEntity packAllocationForPostpaid(PackAllocationDto packAllocationDto) {
		Optional<RatingProfileVoucher> ratingProfileVoucher = ratingProfileVoucherRepository.findById(packAllocationDto.getPackId());
		if (ratingProfileVoucher.isPresent()) {
			
			RatingProfileVoucher ratingProfileVoucherDb = ratingProfileVoucher.get();
			
			int packActivationDays = findIntIntoString(ratingProfileVoucherDb.getRatesOffer());
			
			PackAllocationPostpaid packAllocationPostpaid = new PackAllocationPostpaid();
			
			packAllocationPostpaid.setActivationDate(new Date());
			
			LocalDateTime activationDate = CallSessionUsageServiceImpl.convertDateToLocalDateTime(packAllocationPostpaid.getActivationDate());
            LocalDateTime expirationDate = activationDate.plusDays(packActivationDays);
			Date expirationDateDb = CallSessionUsageServiceImpl.convertLocalDateTimeToDate(expirationDate);
			
			packAllocationPostpaid.setExpirationDate(expirationDateDb);
			
			String activationDateDto = CallSessionUsageServiceImpl.fetchReadableDateTime(packAllocationPostpaid.getActivationDate());
            String expirationDateDto = CallSessionUsageServiceImpl.fetchReadableDateTime(expirationDateDb);
			
			Optional<PostpaidAccounts> postpaidAccountDb = postpaidAccountsRepo.findByImsi(packAllocationDto.getImsi());
			
			if (postpaidAccountDb.isPresent()) {
				PostpaidAccounts postpaidAccount = postpaidAccountDb.get();

				if (ratingProfileVoucherDb.getDataBalanceParameter().equalsIgnoreCase("GB")) {
					postpaidAccount.setTotalDataOctetsAvailable(convertGigabytesToBytes(ratingProfileVoucherDb.getDataBalance().longValue()));
				}

				else if (ratingProfileVoucherDb.getDataBalanceParameter().equalsIgnoreCase("MB")) {
					postpaidAccount.setTotalDataOctetsAvailable(convertMegabytesToBytes(ratingProfileVoucherDb.getDataBalance().longValue()));
				}
				
				else {
					postpaidAccount.setTotalDataOctetsAvailable(convertKilobytesToBytes(ratingProfileVoucherDb.getDataBalance().longValue()));
				}

				postpaidAccount.setTotalCallSecondsAvailable(convertMinsToSeconds(ratingProfileVoucherDb.getCallBalance().longValue()));
				postpaidAccount.setTotalSmsAvailable(ratingProfileVoucherDb.getSmsBalance().longValue());
				postpaidAccount.setTotalDataOctetsConsumed(0L);
				postpaidAccount.setTotalOutputDataOctetsAvailable(0L);
				postpaidAccount.setTotalInputDataOctetsAvailable(0L);
				postpaidAccount.setTotalCallSecondsConsumed(0L);
				postpaidAccount.setTotalSmsConsumed(0L);
				postpaidAccountsRepo.save(postpaidAccount);
				
				packAllocationPostpaid.setImsi(postpaidAccount.getImsi());
				packAllocationPostpaid.setMsisdn(postpaidAccount.getMsisdn());
				packAllocationPostpaid.setCustomerId(postpaidAccount.getCustomerId());
				packAllocationPostpaid.setPackName(ratingProfileVoucherDb.getPackName());
				
				packAllocationPostpaidRepo.save(packAllocationPostpaid);

				PackAllocationDto packAllocationDtoNew = new PackAllocationDto(packAllocationPostpaid.getId(),
						packAllocationDto.getMsisdn(), packAllocationDto.getImsi(), activationDateDto,
						expirationDateDto, ratingProfileVoucherDb.getId(), packAllocationPostpaid.getPackName(),
						postpaidAccount.getCustomerId());

				return new ResponseEntity<>(packAllocationDtoNew, HttpStatus.OK);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid MSISDN"));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Please select the valid pack"));
	}
	
	@Override
	public ResponseEntity getAssignedPrepaidPack(Integer customerId) {
		Optional<PackAllocationPrepaid> prepaidPack = packAllocationPrepaidRepo.findByCustomerId(customerId);
		if (prepaidPack.isPresent()) {
			PackAllocationPrepaid prepaidPackDb = prepaidPack.get();
			PackAllocationDto packAllocationDto=new PackAllocationDto();
			packAllocationDto.setId(prepaidPackDb.getId());
			packAllocationDto.setCustomerId(prepaidPackDb.getCustomerId());
			packAllocationDto.setActivationDate(CallSessionUsageServiceImpl.fetchReadableDateTime(prepaidPackDb.getActivationDate()));
			packAllocationDto.setExpirationDate(CallSessionUsageServiceImpl.fetchReadableDateTime(prepaidPackDb.getExpirationDate()));
			packAllocationDto.setImsi(prepaidPackDb.getImsi());
			packAllocationDto.setMsisdn(prepaidPackDb.getMsisdn());
		    packAllocationDto.setPackName(prepaidPackDb.getPackName());	
		    
			Optional<RatingProfileVoucher> ratingProfileVoucher = ratingProfileVoucherRepository.findByPackName(prepaidPackDb.getPackName());
			RatingProfileVoucher ratingProfileVoucherDb=ratingProfileVoucher.get();
			
			packAllocationDto.setPackId(ratingProfileVoucherDb.getId());
			return new ResponseEntity<>(packAllocationDto, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid Customer Id"));
	}

	@Override
	public ResponseEntity getAssignedPostpaidPack(Integer customerId) {
		Optional<PackAllocationPostpaid> postpaidPack = packAllocationPostpaidRepo.findByCustomerId(customerId);
		if (postpaidPack.isPresent()) {
			PackAllocationPostpaid postpaidPackDb = postpaidPack.get();
			PackAllocationDto packAllocationDto=new PackAllocationDto();
			packAllocationDto.setId(postpaidPackDb.getId());
			packAllocationDto.setCustomerId(postpaidPackDb.getCustomerId());
			packAllocationDto.setActivationDate(CallSessionUsageServiceImpl.fetchReadableDateTime(postpaidPackDb.getActivationDate()));
			packAllocationDto.setExpirationDate(CallSessionUsageServiceImpl.fetchReadableDateTime(postpaidPackDb.getExpirationDate()));
			packAllocationDto.setImsi(postpaidPackDb.getImsi());
			packAllocationDto.setMsisdn(postpaidPackDb.getMsisdn());
		    packAllocationDto.setPackName(postpaidPackDb.getPackName());	
		    
			Optional<RatingProfileVoucher> ratingProfileVoucher = ratingProfileVoucherRepository.findByPackName(postpaidPackDb.getPackName());
			RatingProfileVoucher ratingProfileVoucherDb=ratingProfileVoucher.get();
			
			packAllocationDto.setPackId(ratingProfileVoucherDb.getId());
			return new ResponseEntity<>(packAllocationDto, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid Customer Id"));
	}
	
	private static long convertGigabytesToBytes(Long gigaBytes) {
		// 1 GB = 1024^3 bytes
		BigDecimal gigaBytesBigDecimal = new BigDecimal(String.valueOf(gigaBytes));
		BigDecimal bytesBigDecimal = gigaBytesBigDecimal.multiply(BigDecimal.valueOf(Math.pow(1024, 3)));
		return bytesBigDecimal.longValue();
	}
	
	private static long convertMegabytesToBytes(Long megaBytes) {
		// 1 MB = 1024^2 bytes
		BigDecimal megaBytesBigDecimal = new BigDecimal(String.valueOf(megaBytes));
		BigDecimal bytesBigDecimal = megaBytesBigDecimal.multiply(BigDecimal.valueOf(Math.pow(1024, 2)));
		return bytesBigDecimal.longValue();
	}
	
	private static long convertKilobytesToBytes(Long kiloBytes) {
		// 1 KB = 1024^1 bytes
		BigDecimal kiloBytesBigDecimal = new BigDecimal(String.valueOf(kiloBytes));
		BigDecimal bytesBigDecimal = kiloBytesBigDecimal.multiply(BigDecimal.valueOf(Math.pow(1024, 1)));
		return bytesBigDecimal.longValue();
	}

	private static long convertMinsToSeconds(Long mins) {
		// 1 Min = 60^1 seconds
		BigDecimal minsBigDecimal = new BigDecimal(String.valueOf(mins));
		BigDecimal secondsBigDecimal = minsBigDecimal.multiply(BigDecimal.valueOf(Math.pow(60, 1)));
		return secondsBigDecimal.longValue();
	}
	 
	private int findIntIntoString(String value) {
		// Define a regular expression pattern to find integers
		Pattern pattern = Pattern.compile("\\b\\d+\\b");

		// Create a matcher with the input string
		Matcher matcher = pattern.matcher(value);

		int intValue = 0;

		// Find and print all integers in the string
		while (matcher.find()) {
			String match = matcher.group();
			intValue = Integer.parseInt(match);
			System.out.println("Found integer: " + intValue);
		}
		System.out.println("intValue:" + intValue);
		return intValue;
	}

}
