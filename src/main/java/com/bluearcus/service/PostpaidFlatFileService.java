package com.bluearcus.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bluearcus.dto.ConsumedCallDataSmsDto;
import com.bluearcus.dto.PostpaidCustomerBillDto;
import com.bluearcus.model.PackAllocationPostpaid;
import com.bluearcus.model.RatingProfileVoucher;
import com.bluearcus.repo.PackAllocationPostpaidRepo;
import com.bluearcus.repo.RatingProfileVoucherRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PostpaidFlatFileService {
	
	@Autowired
	private PackAllocationPostpaidRepo packAllocationPostpaidRepo;
	
	@Autowired
	private RatingProfileVoucherRepository ratingProfileVoucherRepository;

	public void storeCustomerData(String packtype, String dateFolder, String msisdn, String customerData) {
		try {

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date dt = simpleDateFormat.parse(dateFolder);
			dateFolder = simpleDateFormat.format(dt);

			String directory = "/home/apache-tomcat-8.5.85/webapps/Postpaid Accounts/" + packtype + "/" + dateFolder + "/";

			
			Path path = Paths.get(directory);

			if (path.toFile().exists() == false) {
				boolean b = path.toFile().mkdirs();
				System.out.println(b);
			}

			String fileName = msisdn + ".txt";

			Path filePath = path.resolve(fileName);

			FileWriter fileWriter = new FileWriter(filePath.toFile());

			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		
			bufferedWriter.write(customerData);

			bufferedWriter.close();

			System.out.println("Dynamic text file created successfully at: " + filePath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResponseEntity consumedCallDataSmsByCustomer(String fileName, String startDate, String endDate) throws IOException {
		
		LocalDate startingDate = LocalDate.parse(startDate);
		LocalDate endingDate = LocalDate.parse(endDate);
		List<LocalDate> dateRange = new ArrayList<>();
		
		String packName = "";
		Long availableDataInBytes = 0l;
		int availableDataInMB = 0;
		Long availableCallsInSeconds = 0l;
		int availableSms = 0;
		Long totalConsumedData = 0l;
		Long totalConsumedCalls = 0l;
		int totalConsumedSms = 0;
		int customerId = 0;
		int packPrice = 0;
		
		while (!startingDate.isAfter(endingDate)) {
			dateRange.add(startingDate);
			startingDate = startingDate.plusDays(1);
		}

		for (LocalDate localDate : dateRange) {

			String directory = "/home/apache-tomcat-8.5.85/webapps/Postpaid Accounts/Data/" + localDate + "/" + fileName;

			try (BufferedReader bufferedReader = new BufferedReader(new FileReader(directory))) {
				String data = bufferedReader.readLine();
				System.out.println(data);
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode jsonNode = objectMapper.readTree(data);
				totalConsumedData = totalConsumedData + jsonNode.get("total_data_octets_consumed").asLong();
				totalConsumedCalls = totalConsumedCalls + jsonNode.get("total_call_seconds_consumed").asLong();
				totalConsumedSms = totalConsumedSms + jsonNode.get("total_sms_consumed").asInt();
				customerId = jsonNode.get("customer_id").asInt();
			} catch (Exception e) {}
			
		}
		
		Optional<PackAllocationPostpaid> packAllocationPostpaid = packAllocationPostpaidRepo.findByCustomerId(customerId);
		if (packAllocationPostpaid.isPresent()) {
			
			PackAllocationPostpaid packAllocationPostpaidDb = packAllocationPostpaid.get();
			packName = packAllocationPostpaidDb.getPackName();
			
			Optional<RatingProfileVoucher> ratingProfileVoucher = ratingProfileVoucherRepository.findByPackName(packName);
			RatingProfileVoucher ratingProfileVoucherDb = ratingProfileVoucher.get();
			
			packPrice = PostpaidAccountsServiceImpl.extractFirstIntValue(ratingProfileVoucherDb.getRatesOffer());
			
			if (ratingProfileVoucherDb.getDataBalanceParameter().equalsIgnoreCase("GB")) {
				availableDataInBytes = PostpaidAccountsServiceImpl.convertGigabytesToBytes(ratingProfileVoucherDb.getDataBalance().longValue());
				availableDataInMB = PostpaidAccountsServiceImpl.convertBytesToMegabytes(availableDataInBytes);
			}
			
			else if (ratingProfileVoucherDb.getDataBalanceParameter().equalsIgnoreCase("MB")) {
			availableDataInBytes = PostpaidAccountsServiceImpl.convertMegabytesToBytes(ratingProfileVoucherDb.getDataBalance().longValue());
			availableDataInMB = PostpaidAccountsServiceImpl.convertBytesToMegabytes(availableDataInBytes);
			}
			
			else {
			availableDataInBytes =	PostpaidAccountsServiceImpl.convertKilobytesToBytes(ratingProfileVoucherDb.getDataBalance().longValue());
			availableDataInMB = PostpaidAccountsServiceImpl.convertBytesToMegabytes(availableDataInBytes);
			}
			
			if (ratingProfileVoucherDb.getCallBalanceParameter().equalsIgnoreCase("Mins")) {
				availableCallsInSeconds = PostpaidAccountsServiceImpl.convertMinsToSeconds(ratingProfileVoucherDb.getCallBalance().longValue());
			}
			
			else {
				availableCallsInSeconds = ratingProfileVoucherDb.getCallBalance().longValue();
			}
			availableSms = ratingProfileVoucherDb.getSmsBalance();
		}
		
		System.out.println("Consumed data:" + totalConsumedData);
		System.out.println("Consumed call:" + totalConsumedCalls);
		System.out.println("Consumed sms:" + totalConsumedSms);
		
		String packOfferedData = availableDataInMB + " MB";
		String packConsumedData = PostpaidAccountsServiceImpl.convertBytesToMegabytes(totalConsumedData) + " MB";
		String packOfferedCalls = availableCallsInSeconds + " Seconds";
		String packConsumedCalls = PostpaidAccountsServiceImpl.convertSecondsToMins(totalConsumedCalls) + " Mins";
		Long packConsumedSms = Long.valueOf(totalConsumedSms);
		
		PostpaidCustomerBillDto postpaidCustomerBillDto = null;
		
		System.out.println("availableDataInBytes:" + availableDataInBytes);
		System.out.println("availableCallsInSeconds:" + availableCallsInSeconds);
		System.out.println("totalConsumedCalls:" + totalConsumedCalls);
		
		if (availableDataInBytes >= totalConsumedData && availableCallsInSeconds >= totalConsumedCalls) {
			postpaidCustomerBillDto = new PostpaidCustomerBillDto(packName, packPrice, 0, packPrice, startDate, endDate,
					packOfferedData, packConsumedData, packOfferedCalls, packConsumedCalls, availableSms,
					packConsumedSms);
			return new ResponseEntity<>(postpaidCustomerBillDto, HttpStatus.OK);
		}

		postpaidCustomerBillDto = new PostpaidCustomerBillDto(packName, packPrice, 300, packPrice + 300, startDate,
				endDate, packOfferedData, packConsumedData, packOfferedCalls, packConsumedCalls, availableSms,
				packConsumedSms);
		return new ResponseEntity<>(postpaidCustomerBillDto, HttpStatus.OK);

	}

}
