package com.example.Timesheet.com.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@ApiIgnore
public class LoginController {
	String url = "jdbc:sqlserver://localhost:1433;databaseName=timesheetdb;user=admin;password=Cgi12345*"; //local
	//TODO refaire les couches pour le login
	@GetMapping("/login")
	
	public boolean login(@RequestParam(value="username") String username, @RequestParam(value="password") String password) throws SQLException {
				boolean boolReturn = false;
				String sqlQuery ="IF EXISTS (SELECT * FROM Login WHERE username ='"+username+"' AND password='"+password+"')" + 
						" SELECT 1 " + 
						" ELSE " + 
						"SELECT 2";
				
				Connection conn = DriverManager.getConnection(url); 
				Statement statement = conn.createStatement(); 
				ResultSet rs = statement.executeQuery(sqlQuery);
				 while (rs.next()) {
					 if(rs.getInt("")==1) {
						 
						 boolReturn= true;
					 } 			  
			
					}

						return boolReturn;
	}

}
