package com.bluearcus.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bluearcus.dto.DeductionDto;
import com.bluearcus.dto.PostpaidAccountsDto;
import com.bluearcus.dto.PostpaidAvailBalanceDto;
import com.bluearcus.dto.PostpaidCustomerBillDto;
import com.bluearcus.exception.CustomMessage;
import com.bluearcus.model.PackAllocationPostpaid;
import com.bluearcus.model.PostpaidAccounts;
import com.bluearcus.model.RatingProfileVoucher;
import com.bluearcus.repo.PackAllocationPostpaidRepo;
import com.bluearcus.repo.PostpaidAccountsRepo;
import com.bluearcus.repo.RatingProfileVoucherRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PostpaidAccountsServiceImpl implements PostpaidAccountsService {

	@Autowired
	private PostpaidAccountsRepo postpaidAccountsRepo;
	
	@Autowired
	private RatingProfileVoucherRepository ratingProfileVoucherRepository;
	
	@Autowired
	private PackAllocationPostpaidRepo packAllocationPostpaidRepo;
	
	@Autowired
	private PostpaidFlatFileService flatFileService;

	@Override
	public ResponseEntity savePostpaidAccount(PostpaidAccountsDto postpaidAccountDto) {
		Optional<PostpaidAccounts> postpaidAccount = postpaidAccountsRepo.findByImsi(postpaidAccountDto.getImsi());
		if (!postpaidAccount.isPresent()) {
			PostpaidAccounts postpaidAccountDb = new PostpaidAccounts();
			postpaidAccountDb.setCustomerId(postpaidAccountDto.getCustomerId() != null ? postpaidAccountDto.getCustomerId() : Integer.valueOf(""));
			postpaidAccountDb.setMsisdn(postpaidAccountDto.getMsisdn() != null ? postpaidAccountDto.getMsisdn() : "");
			postpaidAccountDb.setImsi(postpaidAccountDto.getImsi() != null ? postpaidAccountDto.getImsi() : "");

			postpaidAccountDb.setDataParameterType(postpaidAccountDto.getDataParameterType() != null ? postpaidAccountDto.getDataParameterType() : "");
			postpaidAccountDb.setCsVoiceCallSeconds(convertMinsToSeconds(postpaidAccountDto.getCsVoiceCallSeconds()));
			postpaidAccountDb.setFourGDataOctets(postpaidAccountDto.getFourGDataOctets() != null ? postpaidAccountDto.getFourGDataOctets() : Integer.valueOf(""));
			postpaidAccountDb.setFiveGDataOctets(postpaidAccountDto.getFiveGDataOctets() != null ? postpaidAccountDto.getFiveGDataOctets() : Integer.valueOf(""));
			postpaidAccountDb.setVolteCallSeconds(convertMinsToSeconds(postpaidAccountDto.getVolteCallSeconds()));
			
			if (postpaidAccountDto.getDataParameterType().equalsIgnoreCase("GB")) {
				postpaidAccountDb.setTotalDataOctetsAvailable(convertGigabytesToBytes(postpaidAccountDto.getTotalDataOctetsAvailable()));
			}

			else if (postpaidAccountDto.getDataParameterType().equalsIgnoreCase("MB")) {
				postpaidAccountDb.setTotalDataOctetsAvailable(convertMegabytesToBytes(postpaidAccountDto.getTotalDataOctetsAvailable()));
			}

			else {
				postpaidAccountDb.setTotalDataOctetsAvailable(convertKilobytesToBytes(postpaidAccountDto.getTotalDataOctetsAvailable()));
			}
			
			postpaidAccountDb.setTotalInputDataOctetsAvailable(postpaidAccountDto.getTotalInputDataOctetsAvailable());
			postpaidAccountDb.setTotalOutputDataOctetsAvailable(postpaidAccountDto.getTotalOutputDataOctetsAvailable());
			postpaidAccountDb.setTotalDataOctetsConsumed(postpaidAccountDto.getTotalDataOctetsConsumed());
			postpaidAccountDb.setTotalCallSecondsAvailable(convertMinsToSeconds(postpaidAccountDto.getTotalCallSecondsAvailable()));
			postpaidAccountDb.setTotalCallSecondsConsumed(postpaidAccountDto.getTotalCallSecondsConsumed());
			postpaidAccountDb.setTotalSmsAvailable(postpaidAccountDto.getTotalSmsAvailable());
			postpaidAccountDb.setTotalSmsConsumed(postpaidAccountDto.getTotalSmsConsumed());
			postpaidAccountsRepo.save(postpaidAccountDb);
			PostpaidAccountsDto postpaidAccountDtoNew = new PostpaidAccountsDto(postpaidAccountDb.getAccountId(),
					postpaidAccountDb.getCustomerId(), postpaidAccountDb.getMsisdn(), postpaidAccountDb.getImsi(),null,
					postpaidAccountDb.getDataParameterType(), postpaidAccountDb.getCsVoiceCallSeconds(),
					postpaidAccountDb.getFourGDataOctets(), postpaidAccountDb.getFiveGDataOctets(),
					postpaidAccountDb.getVolteCallSeconds(), postpaidAccountDb.getTotalDataOctetsAvailable(),
					postpaidAccountDb.getTotalInputDataOctetsAvailable(),
					postpaidAccountDb.getTotalOutputDataOctetsAvailable(), postpaidAccountDb.getTotalDataOctetsConsumed(),
					postpaidAccountDb.getTotalCallSecondsAvailable(), postpaidAccountDb.getTotalCallSecondsConsumed(),
					postpaidAccountDb.getTotalSmsAvailable(), postpaidAccountDb.getTotalSmsConsumed());
			
			return new ResponseEntity(postpaidAccountDtoNew, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomMessage(HttpStatus.CONFLICT.value(), "IMSI already exist"));
	}
	
	@Override
	public ResponseEntity savePostpaidDeduction(DeductionDto deductionDto) throws JsonProcessingException {
		Optional<PostpaidAccounts> postpaidAccountsDb = postpaidAccountsRepo.findByImsi(deductionDto.getImsi());
		if (postpaidAccountsDb.isPresent()) {
			PostpaidAccounts postpaidAccounts = postpaidAccountsDb.get();
			
			if (deductionDto.getConsumedTimeSeconds() != 0) {
				Long availableCallSeconds = postpaidAccounts.getTotalCallSecondsAvailable();
				Long consumedCallSeconds = convertMinsToSeconds(deductionDto.getConsumedTimeSeconds());
				Long remainingCallSeconds = availableCallSeconds - consumedCallSeconds;
				
				postpaidAccounts.setTotalCallSecondsAvailable(remainingCallSeconds);
				postpaidAccounts.setTotalCallSecondsConsumed(postpaidAccounts.getTotalCallSecondsConsumed() + consumedCallSeconds);
			}
			
			if (deductionDto.getConsumedSms() != 0) {
				Long totalSms = postpaidAccounts.getTotalSmsAvailable();
				Long consumedSms = deductionDto.getConsumedSms();
				Long remainingSms = totalSms - consumedSms;
				postpaidAccounts.setTotalSmsAvailable(remainingSms);
				postpaidAccounts.setTotalSmsConsumed(postpaidAccounts.getTotalSmsConsumed() + consumedSms);
			}
			
			if (deductionDto.getConsumedOctets().getInput() != 0) {
				Long availableBalance = postpaidAccounts.getTotalDataOctetsAvailable();
				Long consumedBalance = deductionDto.getConsumedOctets().getInput();
				Long outputBalance = availableBalance - consumedBalance;
				postpaidAccounts.setTotalDataOctetsAvailable(outputBalance);
				postpaidAccounts.setTotalInputDataOctetsAvailable(postpaidAccounts.getTotalInputDataOctetsAvailable() + deductionDto.getConsumedOctets().getInput());
				postpaidAccounts.setTotalOutputDataOctetsAvailable(outputBalance);
				postpaidAccounts.setTotalDataOctetsConsumed(postpaidAccounts.getTotalInputDataOctetsAvailable());
			}
			
			
			postpaidAccountsRepo.save(postpaidAccounts);

			PostpaidAccountsDto postpaidAccountsDto = new PostpaidAccountsDto(postpaidAccounts.getAccountId(),
					postpaidAccounts.getCustomerId(), postpaidAccounts.getMsisdn(), postpaidAccounts.getImsi(),null,
					postpaidAccounts.getDataParameterType(), postpaidAccounts.getCsVoiceCallSeconds(),
					postpaidAccounts.getFourGDataOctets(), postpaidAccounts.getFiveGDataOctets(),
					postpaidAccounts.getVolteCallSeconds(), postpaidAccounts.getTotalDataOctetsAvailable(),
					postpaidAccounts.getTotalInputDataOctetsAvailable(),
					postpaidAccounts.getTotalOutputDataOctetsAvailable(), postpaidAccounts.getTotalDataOctetsConsumed(),
					postpaidAccounts.getTotalCallSecondsAvailable(), postpaidAccounts.getTotalCallSecondsConsumed(),
					postpaidAccounts.getTotalSmsAvailable(), postpaidAccounts.getTotalSmsConsumed());
			
			ObjectMapper mapper = new ObjectMapper();
			String customerData = mapper.writeValueAsString(postpaidAccountsDto);
			
			String date = new Date().toInstant().toString();

			// Storing data for Flat file...
			flatFileService.storeCustomerData("Data", date, postpaidAccountsDto.getMsisdn(), customerData);

			return new ResponseEntity<>(postpaidAccountsDto, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomMessage(HttpStatus.CONFLICT.value(), "Invalid IMSI"));
	}

	@Override
	public ResponseEntity getPostpaidAccount(Integer accountId) {
		Optional<PostpaidAccounts> postpaidAccounts =postpaidAccountsRepo.findById(accountId);
		if (postpaidAccounts.isPresent()) {
			PostpaidAccounts postpaidAccountsDb = postpaidAccounts.get();
			PostpaidAccountsDto postpaidAccountsDto = new PostpaidAccountsDto();
			postpaidAccountsDto.setAccountId(postpaidAccountsDb.getAccountId());
			postpaidAccountsDto.setCustomerId(postpaidAccountsDb.getCustomerId());
			postpaidAccountsDto.setMsisdn(postpaidAccountsDb.getMsisdn());
			postpaidAccountsDto.setImsi(postpaidAccountsDb.getImsi());
			postpaidAccountsDto.setDataParameterType(postpaidAccountsDb.getDataParameterType());
			postpaidAccountsDto.setCsVoiceCallSeconds(postpaidAccountsDb.getCsVoiceCallSeconds());
			postpaidAccountsDto.setFourGDataOctets(postpaidAccountsDb.getFourGDataOctets());
			postpaidAccountsDto.setFiveGDataOctets(postpaidAccountsDb.getFiveGDataOctets());
			postpaidAccountsDto.setVolteCallSeconds(postpaidAccountsDb.getVolteCallSeconds());
			postpaidAccountsDto.setTotalDataOctetsAvailable(postpaidAccountsDb.getTotalDataOctetsAvailable());
			postpaidAccountsDto.setTotalInputDataOctetsAvailable(postpaidAccountsDb.getTotalInputDataOctetsAvailable());
			postpaidAccountsDto.setTotalOutputDataOctetsAvailable(postpaidAccountsDb.getTotalOutputDataOctetsAvailable());
			postpaidAccountsDto.setTotalDataOctetsConsumed(postpaidAccountsDb.getTotalDataOctetsConsumed());
			postpaidAccountsDto.setTotalCallSecondsAvailable(postpaidAccountsDb.getTotalCallSecondsAvailable());
			postpaidAccountsDto.setTotalCallSecondsConsumed(postpaidAccountsDb.getTotalCallSecondsConsumed());
			postpaidAccountsDto.setTotalSmsAvailable(postpaidAccountsDb.getTotalSmsAvailable());
			postpaidAccountsDto.setTotalSmsConsumed(postpaidAccountsDb.getTotalSmsConsumed());
			return new ResponseEntity<>(postpaidAccountsDto, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Account Id does n't exist"));
	}
	
	@Override
	public ResponseEntity getPostpaidAccountByCustomerId(Integer customerId) {
		Optional<PostpaidAccounts> postpaidAccount = postpaidAccountsRepo.findByCustomerId(customerId);
		if (postpaidAccount.isPresent()) {
			PostpaidAccounts postpaidAccountDb = postpaidAccount.get();
			PostpaidAccountsDto postpaidAccountDto = new PostpaidAccountsDto();
			postpaidAccountDto.setAccountId(postpaidAccountDb.getAccountId());
			postpaidAccountDto.setCustomerId(postpaidAccountDb.getCustomerId());
			postpaidAccountDto.setMsisdn(postpaidAccountDb.getMsisdn());
			postpaidAccountDto.setImsi(postpaidAccountDb.getImsi());
			postpaidAccountDto.setDataParameterType(postpaidAccountDb.getDataParameterType());
			postpaidAccountDto.setCsVoiceCallSeconds(postpaidAccountDb.getCsVoiceCallSeconds());
			postpaidAccountDto.setFourGDataOctets(postpaidAccountDb.getFourGDataOctets());
			postpaidAccountDto.setFiveGDataOctets(postpaidAccountDb.getFiveGDataOctets());
			postpaidAccountDto.setVolteCallSeconds(postpaidAccountDb.getVolteCallSeconds());
			postpaidAccountDto.setTotalDataOctetsAvailable(postpaidAccountDb.getTotalDataOctetsAvailable());
			postpaidAccountDto.setTotalInputDataOctetsAvailable(postpaidAccountDb.getTotalInputDataOctetsAvailable());
			postpaidAccountDto.setTotalOutputDataOctetsAvailable(postpaidAccountDb.getTotalOutputDataOctetsAvailable());
			postpaidAccountDto.setTotalDataOctetsConsumed(postpaidAccountDb.getTotalDataOctetsConsumed());
			postpaidAccountDto.setTotalCallSecondsAvailable(postpaidAccountDb.getTotalCallSecondsAvailable());
			postpaidAccountDto.setTotalCallSecondsConsumed(postpaidAccountDb.getTotalCallSecondsConsumed());
			postpaidAccountDto.setTotalSmsAvailable(postpaidAccountDb.getTotalSmsAvailable());
			postpaidAccountDto.setTotalSmsConsumed(postpaidAccountDb.getTotalSmsConsumed());
			return new ResponseEntity<>(postpaidAccountDto, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Customer Id does n't exist"));
	}
	
	@Override
	public List<String> getAllPostpaidNumbers() {
		List<PostpaidAccounts> postpaidAccountList = postpaidAccountsRepo.findAll();
		List<String> postpaidNumberList = new ArrayList<>();
		for (PostpaidAccounts postpaidAccount : postpaidAccountList) {
			postpaidNumberList.add(postpaidAccount.getMsisdn());
		}
		return postpaidNumberList;
	}

	@Override
	public ResponseEntity getAvailableBalance(String imsi) {
		Optional<PostpaidAccounts> postpaidAccounts = postpaidAccountsRepo.findByImsi(imsi);
		if (postpaidAccounts.isPresent()) {
			PostpaidAccounts postpaidAccountsDb = postpaidAccounts.get();
			PostpaidAvailBalanceDto postpaidAvailBalanceDto = new PostpaidAvailBalanceDto();
			postpaidAvailBalanceDto.setTotalDataOctetsAvailable(postpaidAccountsDb.getTotalDataOctetsAvailable());
			postpaidAvailBalanceDto.setTotalInputDataOctetsAvailable(postpaidAccountsDb.getTotalInputDataOctetsAvailable());
			postpaidAvailBalanceDto.setTotalOutputDataOctetsAvailable(postpaidAccountsDb.getTotalOutputDataOctetsAvailable());
			postpaidAvailBalanceDto.setTotalCallSecondsAvailable(postpaidAccountsDb.getTotalCallSecondsAvailable());
			postpaidAvailBalanceDto.setTotalSmsAvailable(postpaidAccountsDb.getTotalSmsAvailable());
			return new ResponseEntity<>(postpaidAvailBalanceDto, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid IMSI"));
	}
	
	@Override
	public ResponseEntity generateBillForCustomer(String msisdn) {
		Optional<PostpaidAccounts> postpaidAccount = postpaidAccountsRepo.findByMsisdn(msisdn);
		if (postpaidAccount.isPresent()) {

			PostpaidAccounts postpaidAccountDb = postpaidAccount.get();

			Optional<PackAllocationPostpaid> packAllocation = packAllocationPostpaidRepo.findByMsisdn(postpaidAccountDb.getMsisdn());
			PackAllocationPostpaid packAllocationPostpaid = packAllocation.get();

			Optional<RatingProfileVoucher> ratingProfileVoucher = ratingProfileVoucherRepository.findByPackName(packAllocationPostpaid.getPackName());
			RatingProfileVoucher ratingProfileVoucherDb = ratingProfileVoucher.get();

			int availableDataBalance = 0;
			int availableCallBalance = 0;
			int avaialbleSmsBalance = ratingProfileVoucherDb.getSmsBalance();

			if (ratingProfileVoucherDb.getDataBalanceParameter().equalsIgnoreCase("GB")) {
				int dataBalance = ratingProfileVoucherDb.getDataBalance();
				long value = 1024 * 1024 * 1024;
				long dataInBytes = dataBalance * value;
				availableDataBalance = convertBytesToMegabytes(dataInBytes);
			}

			else if (ratingProfileVoucherDb.getDataBalanceParameter().equalsIgnoreCase("MB")) {
				int dataBalance = ratingProfileVoucherDb.getDataBalance();
				long value = 1024 * 1024;
				long dataInBytes = dataBalance * value;
				availableDataBalance = convertBytesToMegabytes(dataInBytes);
			}

			else {
				int dataBalance = ratingProfileVoucherDb.getDataBalance();
				long value = 1024;
				long dataInBytes = dataBalance * value;
				availableDataBalance = convertBytesToMegabytes(dataInBytes);
			}

			if (ratingProfileVoucherDb.getCallBalanceParameter().equalsIgnoreCase("Mins")) {
				int callBalance = ratingProfileVoucherDb.getCallBalance();
				int value = 60;
				int callsInSeconds = callBalance * value;
				availableCallBalance = callsInSeconds;
			}

			Long consumedDataBalance = postpaidAccountDb.getTotalDataOctetsConsumed();
			Long consumedCallBalance = postpaidAccountDb.getTotalCallSecondsConsumed();
			Long consumedSmsBalance = postpaidAccountDb.getTotalSmsConsumed();
			Date activationDate = packAllocationPostpaid.getActivationDate();
			Date expirationDate = packAllocationPostpaid.getExpirationDate();

			int packPrice = 0;

			String fromDate = fetchReadableDateTime(activationDate);
			String toDate = fetchReadableDateTime(expirationDate);

			String offeredData = availableDataBalance + " MB";
			String totalDataUsed = convertBytesToMegabytes(consumedDataBalance) + " MB";

			System.out.println("totalDataUsed:" + totalDataUsed);

			String offeredCalls = availableCallBalance + " Seconds";
			String totalCallsUsed = convertSecondsToMins(consumedCallBalance) + " Mins";

			System.out.println("totalCallsUsed: " + totalCallsUsed);

			String packName = "";
			String ratesOfferToCustomer = "";

			if (availableDataBalance != 0 && availableCallBalance != 0) {

				ratesOfferToCustomer = ratingProfileVoucherDb.getRatesOffer();
				packPrice = extractFirstIntValue(ratesOfferToCustomer);
				packName = ratingProfileVoucherDb.getPackName();

				if (availableDataBalance == consumedDataBalance || availableDataBalance > consumedDataBalance) {
					PostpaidCustomerBillDto postpaidCustomerBillDto = new PostpaidCustomerBillDto(packName, packPrice,
							0, packPrice, fromDate, toDate, offeredData, totalDataUsed, offeredCalls, totalCallsUsed,
							avaialbleSmsBalance, consumedSmsBalance);
					return new ResponseEntity<>(postpaidCustomerBillDto, HttpStatus.OK);
				}

				PostpaidCustomerBillDto postpaidCustomerBillDto = new PostpaidCustomerBillDto(packName, packPrice, 300,
						packPrice + 300, fromDate, toDate, offeredData, totalDataUsed, offeredCalls, totalCallsUsed,
						avaialbleSmsBalance, consumedSmsBalance);
				return new ResponseEntity<>(postpaidCustomerBillDto, HttpStatus.OK);

			}

			else if (availableDataBalance != 0) {

				ratesOfferToCustomer = ratingProfileVoucherDb.getRatesOffer();
				packPrice = extractFirstIntValue(ratesOfferToCustomer);
				packName = ratingProfileVoucherDb.getPackName();

				if (availableDataBalance == consumedDataBalance || availableDataBalance > consumedDataBalance) {
					PostpaidCustomerBillDto postpaidCustomerBillDto = new PostpaidCustomerBillDto(packName, packPrice,
							0, packPrice, fromDate, toDate, offeredData, totalDataUsed, offeredCalls, totalCallsUsed,
							avaialbleSmsBalance, consumedSmsBalance);
					return new ResponseEntity<>(postpaidCustomerBillDto, HttpStatus.OK);
				}

				PostpaidCustomerBillDto postpaidCustomerBillDto = new PostpaidCustomerBillDto(packName, packPrice, 300,
						packPrice + 300, fromDate, toDate, offeredData, totalDataUsed, offeredCalls, totalCallsUsed,
						avaialbleSmsBalance, consumedSmsBalance);
				return new ResponseEntity<>(postpaidCustomerBillDto, HttpStatus.OK);

			}

			else {

				ratesOfferToCustomer = ratingProfileVoucherDb.getRatesOffer();
				packPrice = extractFirstIntValue(ratesOfferToCustomer);
				packName = ratingProfileVoucherDb.getPackName();

				if (availableCallBalance == consumedCallBalance || availableCallBalance > consumedCallBalance) {
					PostpaidCustomerBillDto postpaidCustomerBillDto = new PostpaidCustomerBillDto(packName, packPrice,
							0, packPrice, fromDate, toDate, offeredData, totalDataUsed, offeredCalls, totalCallsUsed,
							avaialbleSmsBalance, consumedSmsBalance);
					return new ResponseEntity<>(postpaidCustomerBillDto, HttpStatus.OK);
				}

				PostpaidCustomerBillDto postpaidCustomerBillDto = new PostpaidCustomerBillDto(packName, packPrice, 300,
						packPrice + 300, fromDate, toDate, offeredData, totalDataUsed, offeredCalls, totalCallsUsed,
						avaialbleSmsBalance, consumedSmsBalance);
				return new ResponseEntity<>(postpaidCustomerBillDto, HttpStatus.OK);

			}

		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid msisdn"));
	}
	
	public static long convertGigabytesToBytes(Long gigaBytes) {
		// 1 GB = 1024^3 bytes
		BigDecimal gigaBytesBigDecimal = new BigDecimal(String.valueOf(gigaBytes));
		BigDecimal bytesBigDecimal = gigaBytesBigDecimal.multiply(BigDecimal.valueOf(Math.pow(1024, 3)));
		return bytesBigDecimal.longValue();
	}
	
	public static int convertBytesToGigabytes(Long bytes) {
		long divider = 1024 * 1024 * 1024;
		long gigabytes = bytes / divider;
		int gbValue = (int) gigabytes;
		return gbValue;
	}
	
	public static long convertMegabytesToBytes(Long megaBytes) {
		// 1 MB = 1024^2 bytes
		BigDecimal megaBytesBigDecimal = new BigDecimal(String.valueOf(megaBytes));
		BigDecimal bytesBigDecimal = megaBytesBigDecimal.multiply(BigDecimal.valueOf(Math.pow(1024, 2)));
		return bytesBigDecimal.longValue();
	}
	
	public static int convertBytesToMegabytes(Long bytes) {
		long divider = 1024 * 1024;
		long megabytes = bytes / divider;
		int mbValue = (int) megabytes;
		return mbValue;
	}
	
	public static long convertKilobytesToBytes(Long kiloBytes) {
		// 1 KB = 1024^1 bytes
		BigDecimal kiloBytesBigDecimal = new BigDecimal(String.valueOf(kiloBytes));
		BigDecimal bytesBigDecimal = kiloBytesBigDecimal.multiply(BigDecimal.valueOf(Math.pow(1024, 1)));
		return bytesBigDecimal.longValue();
	}
	
	public static int convertBytesToKilobytes(Long bytes) {
		int divider = 1024;
		long kilobytes = bytes / divider;
		int kbValue = (int) kilobytes;
		return kbValue;
	}

	public static long convertMinsToSeconds(Long mins) {
		// 1 Min = 60^1 seconds
		BigDecimal minsBigDecimal = new BigDecimal(String.valueOf(mins));
		BigDecimal secondsBigDecimal = minsBigDecimal.multiply(BigDecimal.valueOf(Math.pow(60, 1)));
		return secondsBigDecimal.longValue();
	}
	
	public static int convertSecondsToMins(Long seconds) {
		int divider = 60;
		long mins = seconds / divider;
		int minsValue = (int) mins;
		return minsValue;
	}
	
	public static int extractFirstIntValue(String value) {
		Pattern pattern = Pattern.compile("\\b\\d+\\b");
		Matcher matcher = pattern.matcher(value);

		if (matcher.find()) {
			int firstValue=Integer.parseInt(matcher.group());
			System.out.println(firstValue);
			return firstValue;
		} else {
			return -1;
		}
	}
	
	public static String fetchReadableDateTime(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String formattedDate = simpleDateFormat.format(date);
		return formattedDate;
	}

}
