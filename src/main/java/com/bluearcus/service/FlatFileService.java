package com.bluearcus.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class FlatFileService {

	public void storeUserData(String packtype, String dateFolder, Integer customerId, String customerData) {
		try {

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date dt = simpleDateFormat.parse(dateFolder);
			dateFolder = simpleDateFormat.format(dt);

			//String directory = "C:\\apache-tomcat-8.5.95\\webapps\\Postpaid Accounts\\" + packtype + "\\" + dateFolder + "\\";
			String directory = "/home/apache-tomcat-8.5.85/webapps/Postpaid Accounts/" + packtype + "/" + dateFolder + "/";

			Path path = Paths.get(directory);

			if (path.toFile().exists() == false) {
				boolean b = path.toFile().mkdirs();
				System.out.println(b);
			}

			String fileName = "customer_id " + customerId + ".txt";

			Path filePath = path.resolve(fileName);

			FileWriter fileWriter = new FileWriter(filePath.toFile());

			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			customerData = customerData.replaceAll(",", " |");
			
			bufferedWriter.write(customerData);

			bufferedWriter.close();

			System.out.println("Dynamic text file created successfully at: " + filePath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
