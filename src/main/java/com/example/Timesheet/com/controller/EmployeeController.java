package com.example.Timesheet.com.controller;

import java.sql.SQLException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Timesheet.com.GlobalFunctions;
import com.example.Timesheet.com.GlobalVars;
import com.example.Timesheet.com.dto.EmployeeDto;
import com.example.Timesheet.com.mapper.EmployeeMapper;
import com.example.Timesheet.com.model.Employee;
import com.example.Timesheet.com.service.EmployeeService;
import com.example.Timesheet.com.service.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.example.Timesheet.com.service.DepartementService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "EmployeeController")
public class EmployeeController {
	
	 @Autowired
	 EmployeeService employeeService = new EmployeeService();
	 
	 @Autowired
	 DepartementService departementService = new DepartementService();
	 
	 @Autowired
	 RoleService roleService = new RoleService();
	 
	 @Autowired
	 EmployeeMapper employeeMapper = new EmployeeMapper();
	 
	 @GetMapping("/employee")
	 @ApiOperation("Returns a list of all employees in the system.")
	 public List<Employee> getAll() throws SQLException {		
		 return employeeService.getAll();
	 }
	 
	  @PutMapping("/employee")
	  @ApiOperation(value = "Updates an employee in the system by their identifier.", notes = "404 if any of the employee's identifier specified in the address, department id, role id or manager id specified in the body is not found")
	  public ResponseEntity<String> edit(@ApiParam("Employee information to be modified. There is no need to keep values that will not be modified.")@RequestBody EmployeeDto employeeDto,
		  											@ApiParam(value = "Id of the employee to be modified. Cannot be null.", required = true)@RequestParam(value="id") int id) throws SQLException {
	
	  if (employeeDto.getDepartementId() != null) {
		  if(departementService.getById(employeeDto.getDepartementId()).isPresent() == false) {
			  return GlobalFunctions.createNotFoundResponse(GlobalVars.DepartementIdNotFound, "/employee");
		  }
	  }
	  
	  if (employeeDto.getManagerId() != null) {
		  if(employeeService.getById(employeeDto.getManagerId()).isPresent() == false) {
			  return GlobalFunctions.createNotFoundResponse(GlobalVars.ManagerIdNotFound, "/employee");
		  }
	  }
	  
	  if (employeeDto.getRoleId() != null) {
		  if(roleService.getById(employeeDto.getRoleId()).isPresent() == false) {
			  return GlobalFunctions.createNotFoundResponse(GlobalVars.RoleIdNotFound, "/employee");
			  }
		  }
		  
		  Employee employee = employeeMapper.dtoToEmployee(employeeDto, id);
		  employeeService.saveEmployee(employee);
		
		  return new ResponseEntity<String>(GlobalVars.EmployeePutSuccessful, HttpStatus.OK);
	  }
}
