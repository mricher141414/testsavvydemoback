package com.example.Timesheet.com;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class GlobalFunctions {
	private static Timestamp createTimestamp() {
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		return new Timestamp(now.getTime());
	}
	
	public static ResponseEntity<String> createNotFoundResponse(String message, String path) {
		String json = "{"
				+ "\"timestamp\": \""+ createTimestamp() + "\","
				+ "\"status:\": 404,"
				+ "\"error: \": \"Not Found\","
				+ "\"message:\": \""+ message + "\","
				+ "\"path\": \""+ path + "\""
				+ "}";
		
		return new ResponseEntity<String>(json,HttpStatus.NOT_FOUND);
	}
	
	public static ResponseEntity<String> createBadRequest(String message, String path) {
		String json = "{"
				+ "\"timestamp\": \""+ createTimestamp() + "\","
				+ "\"status:\": 400,"
				+ "\"error: \": \"Bad Request\","
				+ "\"message:\": \""+ message + "\","
				+ "\"path\": \""+ path + "\""
				+ "}";
		return new ResponseEntity<String>(json,HttpStatus.BAD_REQUEST);
	}
	
	public static ResponseEntity<String> createConflictResponse(String message, String path) {
		String json = "{"
				+ "\"timestamp\": \""+ createTimestamp() + "\","
				+ "\"status:\": 409,"
				+ "\"error: \": \"Conflict\","
				+ "\"message:\": \""+ message + "\","
				+ "\"path\": \""+ path + "\""
				+ "}";
		return new ResponseEntity<String>(json,HttpStatus.CONFLICT);
	}
	
	public static <T> ResponseEntity<String> createOkResponseFromObject(T object) {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		
		String json = "";
		
		try {
			json = ow.writeValueAsString(object);
		}
		catch(JsonProcessingException ex) {
			ex.printStackTrace();
		}
		
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	
	public static java.sql.Date findLatestSunday (java.sql.Date userDate) {
		

		int dayOfWeek = getDayOfWeek(userDate);
		
		if(dayOfWeek != Calendar.SUNDAY) {
			int timeDifference = (dayOfWeek - Calendar.SUNDAY) * GlobalVars.MillisecondsPerDay;
			userDate.setTime(userDate.getTime() - timeDifference);
		}
		
		return userDate;
	}
	
	public static int getDayOfWeek(java.sql.Date userDate) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(userDate);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		
		return dayOfWeek;
	}
}
