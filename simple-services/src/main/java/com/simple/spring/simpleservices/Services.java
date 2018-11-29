package com.simple.spring.simpleservices;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.opencsv.CSVReader;

class Services {
	
	// Churn out users' data by reading from .csv according to query filter
	@SuppressWarnings("unchecked")
	public JSONObject getUsers(Double min, Double max) {
		String csvFile = "./db/users.csv"; //csv file containing users' data
	    CSVReader reader = null;
	    
	    JSONArray listOfUsers = new JSONArray();
	    JSONObject results = new JSONObject();
	    
	    try {
	        reader = new CSVReader(new FileReader(csvFile));
	        String[] line;
	        int lineNo = 0;
	        String colName[] = new String[2];
	        colName[0] = "name"; //default header name if .csv header missing
	        colName[1] = "salary"; //default header name if .csv header missing
	        
	        //Read line by line of .csv until it comes to null
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
	            	colName[0] = name;
	            	colName[1] = salary;
	            	continue;
	            }
	            
	            
	            
	            JSONObject user = new JSONObject();
	            user.put(colName[0], name);
	            try {
	            	if ( Double.parseDouble(salary) < min || Double.parseDouble(salary) > max) {
	            		// skip because it exists our range
	            		continue;
	            	}
	            	user.put(colName[1], Double.parseDouble(salary));
	            } catch (NumberFormatException e) {
	            	user.put(colName[1], salary);
	            }
	            
	            listOfUsers.add(user);
	            
	            
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    results.put("results", listOfUsers);
	    
		return results;
		
	}
	
	private void setFilter() {
		
	}
	
}
