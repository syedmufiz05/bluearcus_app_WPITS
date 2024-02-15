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

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bluearcus.dto.ConsumedCallDataSmsDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PostpaidFlatFileService {

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

//			customerData = customerData.replaceAll(",", " |");
			
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
		Long totalConsumedData = 0l;
		Long totalConsumedCall = 0l;
		int totalConsumedSms = 0;
		
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
				totalConsumedCall = totalConsumedCall + jsonNode.get("total_call_seconds_consumed").asLong();
				totalConsumedSms = totalConsumedSms + jsonNode.get("total_sms_consumed").asInt();
			} catch (Exception e) {}
			
		}
		
		System.out.println("Consumed data:" + totalConsumedData);
		System.out.println("Consumed call:" + totalConsumedCall);
		System.out.println("Consumed sms:" + totalConsumedSms);
		
		ConsumedCallDataSmsDto callDataSmsDto = new ConsumedCallDataSmsDto(totalConsumedData, totalConsumedCall, totalConsumedSms);

		return new ResponseEntity<>(callDataSmsDto, HttpStatus.OK);
	}

}
