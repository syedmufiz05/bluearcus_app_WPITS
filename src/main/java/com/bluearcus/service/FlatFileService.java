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
	private static final String FILE_PATH = "C:\\Users\\ajays\\OneDrive\\Documents\\Postpaid Accounts\\flatfile.txt";

	public void storeData(String data) {
//		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
//			// Join fields with the delimiter and write to the file
//			data = data.replaceAll(",", " |");
//			writer.write(data);
//			writer.newLine(); // Move to the next line for the next record
//			System.out.println("Data successfully stored in the flat file database.");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public void storeUserData(String packtype, String date, Integer customerId, String customerData) {
		try {

			String directory = "C:\\Users\\ajays\\OneDrive\\Documents\\Postpaid Accounts";

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
			// Handle any potential IO exceptions
			e.printStackTrace();
		}
	}
}
