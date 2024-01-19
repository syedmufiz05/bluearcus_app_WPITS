package com.bluearcus.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

@Service
public class FlatFileService {
	public void storeUserData(String packtype, String date, Integer customerId, String customerData) {
		try {

			String directory = "C:\\apache-tomcat-8.5.95\\webapps\\Postpaid Accounts\\Call\\19-01-2024";

			Path path = Paths.get(directory);

			String fileName = "customer_id " + customerId + ".txt";

			Path filePath = path.resolve(fileName);

			// Create a FileWriter object with the file path
			FileWriter fileWriter = new FileWriter(filePath.toFile());

			// Create a BufferedWriter object to write text efficiently
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			// Write the content to the file
			customerData = customerData.replaceAll(",", " |");
			bufferedWriter.write(customerData);

			// Close the BufferedWriter to release resources
			bufferedWriter.close();

			System.out.println("Dynamic text file created successfully at: " + filePath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
