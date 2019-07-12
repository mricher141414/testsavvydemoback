package com.example.Timesheet.com.controller;

import java.io.Serializable;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Timesheet.com.GlobalFunctions;
import com.example.Timesheet.com.GlobalMessages;
import com.example.Timesheet.com.Paths;
import com.example.Timesheet.com.dto.UserDto;
import com.example.Timesheet.com.mapper.EmployeeMapper;
import com.example.Timesheet.com.model.Employee;
import com.example.Timesheet.com.service.LoginService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "LoginController")
public class LoginController implements Serializable {
	
	private static final long serialVersionUID = 6706588763372985645L;
	private static final Logger log = LogManager.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private EmployeeMapper employeeMapper;
	
	@PostMapping(Paths.LoginPath)
	@ApiOperation(value = "Returns a userDto object if the login was successful. 400 if the email-password combination doesn't exist",
					response = UserDto.class)
	public ResponseEntity<String> login(@ApiParam("email of the user trying to connect") @RequestParam(value="email") String email, 
										@ApiParam("password of the user trying to connect") @RequestBody String password) {
		log.debug("Enering login with email of " + email);
		
		Optional<Employee> optionalEmployee = loginService.attemptLogin(email, password);
		
		if(optionalEmployee.isPresent() == false) {
			return GlobalFunctions.createBadRequest(GlobalMessages.EmailPasswordIncorrect, Paths.LoginPath);
		}
		
		UserDto user = employeeMapper.employeeToUserDto(optionalEmployee.get());
		
		return GlobalFunctions.createOkResponseFromObject(user);
	}

}
