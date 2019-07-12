package com.example.Timesheet.com.service;

import java.io.Serializable;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Timesheet.com.dao.IEmployeeDao;
import com.example.Timesheet.com.model.Employee;

@Service
public class LoginService implements Serializable {

	private static final long serialVersionUID = 3900345531028310388L;
	private static final Logger log = LogManager.getLogger(LoginService.class);
	
	@Autowired
	private IEmployeeDao employeeDao;
	
	public Optional<Employee> attemptLogin(String email, String password) {
		log.debug("Entering attemptLogin with email parameter of " + email);
		
		password = password.replace("{\"password\":\"", "");
		password = password.replace("\"}", "");
		
		Optional<Employee> optionalEmployee = employeeDao.findByEmail(email);
		
		if(optionalEmployee.isPresent() == false) {
			return optionalEmployee;
		}
		
		if(optionalEmployee.get().getPassword().equals(password) == false) {
			Optional<Employee> empty = Optional.empty();
			return empty;
		}
		
		return optionalEmployee;
	}
	
	
}
