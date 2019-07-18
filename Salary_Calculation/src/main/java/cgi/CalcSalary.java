package cgi;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CalcSalary implements Serializable {
	
	private static final long serialVersionUID = 5290241843047963933L;
	
	String apiAddress = "http://localhost:8080";
	List<Timesheet> sheets = new ArrayList<Timesheet>();
	
	public CalcSalary() throws Exception {
		
		resetReportList();
		
		getSheets();
		
		if (sheets.isEmpty()) {
			throw new Exception("Queue is Empty");
		}
		else
		{
			for (Timesheet timesheet : sheets) {
				SalaryReport salaryReport = new SalaryReport();
				
				salaryReport.setPaycheck(getEmployeeSalary(timesheet.getEmployeeId()) * timesheet.getTotal());
				salaryReport.setEmployeeId(timesheet.getEmployeeId());
				postSalaryReport(salaryReport);
			}
			System.out.println("Task completed");
			System.out.println("Press enter to end the process");
			
			System.in.read();
		}
	}
	
	public Float getEmployeeSalary(int employeeId) {
		
		String inline = getJsonResult(apiAddress + "/employee/one?id=" + employeeId);
		Float salary = 0F;
		
		JSONParser parse = new JSONParser();
		JSONObject jsonEmployee;
		try {
			jsonEmployee = (JSONObject)parse.parse(inline);
			
			salary = Float.valueOf(String.valueOf(jsonEmployee.get("salary")));
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return salary;
	}
	
	public void getSheets() {
		
		String queueInline = getJsonResult(apiAddress + "/queue");
		
		JSONParser parse = new JSONParser();
		JSONArray jsonArray;
		try {
			jsonArray = (JSONArray)parse.parse(queueInline);
			
			for(int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonItem = (JSONObject) (jsonArray.get(i));
		
				String inline = getJsonResult(apiAddress + "/timesheet/one?id=" + jsonItem.get("timesheetId"));
				
				JSONObject jsonObject = (JSONObject)parse.parse(inline);
				
				Timesheet timesheet = new Timesheet();
				timesheet.setId(Integer.valueOf(String.valueOf(jsonObject.get("id"))));
				timesheet.setTotal(Float.valueOf(String.valueOf(jsonObject.get("total"))));
				timesheet.setNotes(String.valueOf(jsonObject.get("notes")));
				timesheet.setStartDate(Date.valueOf(String.valueOf(jsonObject.get("startDate"))));
				timesheet.setEndDate(Date.valueOf(String.valueOf(jsonObject.get("endDate"))));
				timesheet.setEmployeeId(Integer.valueOf(String.valueOf(jsonObject.get("employeeId"))));
				timesheet.setTimesheetStatusId(Integer.valueOf(String.valueOf(jsonObject.get("timesheetStatusId"))));
				
				sheets.add(timesheet);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public String getJsonResult(String url) {
		String inline = "";
		
		try {
			URL getUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection)getUrl.openConnection();
			conn.setRequestMethod("GET");
			
			conn.connect();
			
			if(conn.getResponseCode() != 200) {
				throw new RuntimeException("HttpResponseCode:" + conn.getResponseCode());
			}
			else {
				Scanner sc = new Scanner(getUrl.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				sc.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return inline;
	}
	
	public void postSalaryReport(SalaryReport report) {
		
		String url = apiAddress + "/salaryreport";
		try {
			URL postUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection)postUrl.openConnection();
			
			String reportJson = "{"
					+ "\"employeeId\": " + report.getEmployeeId() + ","
					+ "\"paycheck\": " + report.getPaycheck()
					+ "}";
			
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.addRequestProperty("Content-Type",  "application/" + "json");
			
			conn.setRequestProperty("Content-Length", Integer.toString(reportJson.length()));
			conn.getOutputStream().write(reportJson.getBytes());
			
			conn.connect();
			
			if(conn.getResponseCode() == 200) {
				System.out.println("The connection was successful");
			}
			else {
				throw new RuntimeException("HttpResponseCode: " + conn.getResponseCode());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void resetReportList() {
		
		String url = apiAddress + "/salaryreport/all";
		try {
			URL deleteUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection)deleteUrl.openConnection();
			
			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			conn.setRequestMethod("DELETE");
			conn.connect();
			
			conn.getInputStream();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
