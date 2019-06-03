package cgi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CalcSalary {
	String apiAddress = "http://localhost:8080";
	Timesheet[] sheets = null;
	float salary;
	
	public CalcSalary() throws Exception {
		getSheets();
		if (!(sheets != null)) {
			throw new Exception("Queue is Empty");
		}
		else
		{
			for (Timesheet timesheet : sheets) {
				getEmployeeSalary(timesheet.getEmployeeId());
				//post JSON(employeeid: timesheet.getEmployeeId(), salary: (timesheet.getTotal*salary));
			}
		}
	}
	
	public Float getEmployeeSalary(int id) {
		
		URI geturl;
		try {
			geturl = new URI(apiAddress + "/employee/one?id=" + id);
			CloseableHttpClient client = HttpClients.createDefault();
		    HttpGet httpGet = new HttpGet(geturl);
		    System.out.print(geturl.toString() + '\n');
		    
		    try {
		    	CloseableHttpResponse response = client.execute(httpGet);
			    ObjectMapper mapper = new ObjectMapper();
		    	salary = mapper.readValue(response.getEntity().getContent()  ,  Employee.class).getSalary();
		    } catch (IOException e) {
		    }
		} catch (URISyntaxException e) {
		}
		
		return salary;
	}
	
	public void getSheets() {
		
		URI geturl;
		try {
			geturl = new URI(apiAddress + "/timesheet/all");
			CloseableHttpClient client = HttpClients.createDefault();
		    HttpGet httpGet = new HttpGet(geturl);
		    System.out.print(geturl.toString() + '\n');
		    
		    try {
		    	CloseableHttpResponse response = client.execute(httpGet);
			    ObjectMapper mapper = new ObjectMapper();
		    	sheets = mapper.readValue(response.getEntity().getContent()  ,  Timesheet[].class);
		    			
		    } catch (IOException e) {
		    }
		} catch (URISyntaxException e) {
		}
	}
	
	public String getString(InputStream inputStream){
		
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	
	    try {
	        while ((line = bufferedReader.readLine()) != null) {
	        	stringBuilder.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	        	inputStream.close();
	        } catch (IOException e) {
	        }
	    }
	return stringBuilder.toString();
	}
				    
				    
				    
				    
				    
				    
				 
//				    String json = "{"id":1,"name":"John"}";
//				    StringEntity entity = new StringEntity(json);
//				    httpPost.setEntity(entity);
//				    httpPost.setHeader("Accept", "application/json");
//				    httpPost.setHeader("Content-type", "application/json");
//				 
//				    CloseableHttpResponse response = client.execute(httpPost);
//				    assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
				    //client.close();
		
	}

