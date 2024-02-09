package com.bluearcus.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bluearcus.dto.PackAllocationDto;
import com.bluearcus.exception.CustomMessage;
import com.bluearcus.model.PackAllocation;
import com.bluearcus.model.PrepaidAccounts;
import com.bluearcus.model.RatingProfileVoucher;
import com.bluearcus.repo.PackAllocationRepo;
import com.bluearcus.repo.PrepaidAccountsRepository;
import com.bluearcus.repo.RatingProfileRepository;
import com.bluearcus.repo.RatingProfileVoucherRepository;

@Service
public class PackAllocationServiceImpl implements PackAllocationService {

	@Autowired
	private PackAllocationRepo packAllocationRepo;
	
	@Autowired
	private PrepaidAccountsRepository prepaidAccountsRepository;

	@Autowired
	private RatingProfileVoucherRepository ratingProfileVoucherRepository;

	@Override
	public ResponseEntity savePackAllocationDetail(PackAllocationDto packAllocationDto) {
		Optional<RatingProfileVoucher> ratingProfileVoucher = ratingProfileVoucherRepository.findById(packAllocationDto.getPackId());
		if (ratingProfileVoucher.isPresent()) {
			
			RatingProfileVoucher ratingProfileVoucherDb = ratingProfileVoucher.get();
			PackAllocation packAllocation = new PackAllocation();
			packAllocation.setActivationDate(new Date());
			
			LocalDateTime activationDate = CallSessionUsageServiceImpl.convertDateToLocalDateTime(packAllocation.getActivationDate());
			System.out.println("activationDate:" + activationDate);

			LocalDateTime expirationDate = activationDate.plusMonths(1);
			System.out.println("expirationDate:" + expirationDate);
			
			Date expirationDateDb = CallSessionUsageServiceImpl.convertLocalDateTimeToDate(expirationDate);
			packAllocation.setExpirationDate(expirationDateDb);
			System.out.println("expirationDateDb:" + expirationDateDb);
			
			String activationDateDto = CallSessionUsageServiceImpl.fetchReadableDateTime(packAllocation.getActivationDate());
            System.out.println("activationDateDto:"+activationDateDto);
            
			String expirationDateDto = CallSessionUsageServiceImpl.fetchReadableDateTime(expirationDateDb);
			System.out.println("expirationDateDto:"+expirationDateDto);
			
			packAllocation.setRatingProfileVoucher(convertDtoToList(ratingProfileVoucherDb));
			
			PrepaidAccounts prepaidAccounts = new PrepaidAccounts();
			prepaidAccounts.setCustomerId(0);
			prepaidAccounts.setImsi(packAllocationDto.getImsi());
			prepaidAccounts.setMsisdn(packAllocationDto.getMsisdn());
			prepaidAccounts.setCalledStationId("");
			prepaidAccounts.setMonitoringKey("");
			prepaidAccounts.setAction("");
			prepaidAccounts.setDataParameterType(ratingProfileVoucherDb.getDataBalanceParameter());
			prepaidAccounts.setCsVoiceCallSeconds(0L);
			if (ratingProfileVoucherDb.getDataBalanceParameter().equalsIgnoreCase("GB")) {
				prepaidAccounts.setTotalDataOctetsAvailable(convertGigabytesToBytes(ratingProfileVoucherDb.getDataBalance().longValue()));
			}
			if (ratingProfileVoucherDb.getDataBalanceParameter().equalsIgnoreCase("MB")) {
				prepaidAccounts.setTotalDataOctetsAvailable(convertMegabytesToBytes(ratingProfileVoucherDb.getDataBalance().longValue()));
			}
			else {
				prepaidAccounts.setTotalDataOctetsAvailable(convertKilobytesToBytes(ratingProfileVoucherDb.getDataBalance().longValue()));
			}
			prepaidAccounts.setTotalCallSecondsAvailable(convertMinsToSeconds(ratingProfileVoucherDb.getCallBalance().longValue()));
			prepaidAccounts.setTotalSmsAvailable(ratingProfileVoucherDb.getSmsBalance().longValue());
			prepaidAccounts.setTotalDataOctetsConsumed(0L);
			prepaidAccounts.setTotalOutputDataOctetsAvailable(0L);
			prepaidAccounts.setTotalInputDataOctetsAvailable(0L);
			prepaidAccounts.setTotalCallSecondsConsumed(0L);
			prepaidAccounts.setTotalSmsConsumed(0L);
			prepaidAccounts.setFourGDataOctets(0);
		    prepaidAccounts.setFiveGDataOctets(0);
		    prepaidAccounts.setVolteCallSeconds(0L);
		    prepaidAccountsRepository.save(prepaidAccounts);
		    
		    packAllocation.setPrepaidAccount(prepaidAccounts);
		    packAllocationRepo.save(packAllocation);
		    
			PackAllocationDto packAllocationDtoNew = new PackAllocationDto(packAllocation.getId(),
					packAllocationDto.getMsisdn(), packAllocationDto.getImsi(), activationDateDto, expirationDateDto,
					ratingProfileVoucherDb.getId(), prepaidAccounts.getAccountId());

			return new ResponseEntity<>(packAllocationDtoNew, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Please select the valid pack"));
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
	
	private static List<RatingProfileVoucher> convertDtoToList(RatingProfileVoucher ratingProfileVoucher) {
		List<RatingProfileVoucher> ratingProfileVoucherList = new ArrayList<>();
		ratingProfileVoucherList.add(ratingProfileVoucher);
		return ratingProfileVoucherList;
	}

}
