package com.simple.spring.simpleservices;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencsv.CSVReader;

public class Services {

	final String FOLDER_PATH = "./db/";

	// Output users based on salary range (name, salary)
	public String getUsers(UserRepository userRepo, Double minimum, Double maximum) {
		ObjectMapper om = new ObjectMapper();
		om.configure(SerializationFeature.INDENT_OUTPUT, true);

		JSONObject result = new JSONObject();
		JSONParser parser = new JSONParser();

		try {
			result.put("results", (JSONArray) parser.parse(userRepo.findBySalaryBetween(minimum, maximum).toString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		try {
			return om.writeValueAsString(result).toString();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return "An error occured";
	}

	// Load all csv files in the directory path
	public void loadCSV(UserRepository userRepo) {
		final File folder = new File(FOLDER_PATH);
		listFiles(userRepo, folder);
	}

	// List all files in directory path
	public void listFiles(final UserRepository userRepo, final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			System.out.println(fileEntry.getName());
			// Assumption: all files in this directory should be csv
			readAndStoreInDB(userRepo, folder + "/" + fileEntry.getName());
		}
	}

	// Read csv file format and save into DB
	public void readAndStoreInDB(UserRepository userRepo, String csvFile) {
		CSVReader reader = null;

		try {
			reader = new CSVReader(new FileReader(csvFile));
			String[] line;
			int lineNo = 0;

			// Read line by line of .csv until it comes to null
			while ((line = reader.readNext()) != null) {

				lineNo++;

				String name = line[0];
				String salary = line[1];

				// check if either salary or name is empty we will skip to the next row
				if (name.equals("") && salary.equals("")) {
					// if empty we skip
					continue;
				}

				// Assume header is at the first row
				// Note: If this row does not comply in csv,
				// then the column name will be messed up too
				if (lineNo == 1) {
					continue;
				}

				try {
					Double.parseDouble(salary);
					User user = new User(name, Double.parseDouble(salary));
					// Only save into repository if name is unique
					if (user != null && !userRepo.existsByName(name)) {
						userRepo.save(user);
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
