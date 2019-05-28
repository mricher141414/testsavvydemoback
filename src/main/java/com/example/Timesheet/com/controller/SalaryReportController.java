package com.example.Timesheet.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Timesheet.com.GlobalFunctions;
import com.example.Timesheet.com.GlobalMessages;
import com.example.Timesheet.com.dto.SalaryReportDto;
import com.example.Timesheet.com.mapper.SalaryReportMapper;
import com.example.Timesheet.com.model.SalaryReport;
import com.example.Timesheet.com.service.EmployeeService;
import com.example.Timesheet.com.service.SalaryReportService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "SalaryReportController")
public class SalaryReportController {

	@Autowired
	private SalaryReportService salaryReportService;
	
	@Autowired
	private SalaryReportMapper salaryReportMapper;
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/salaryreport")
	@ApiOperation("Returns a list of all salary reports in the system.")
	public List<SalaryReport> getAll() {
		return salaryReportService.getAll();
	}
	
	@PostMapping("/salaryreport")
	@ApiOperation(value = "Creates a new salary report in the system.", notes = "404 if employee id in the body is not null, but the employee it's referencing cannot be found.")
	public ResponseEntity<String> create(@ApiParam(value = "Salary report information for the new one to be created", required = true)@RequestBody SalaryReportDto salaryReportDto) {
		
		if(salaryReportDto.getEmployeeId() != null) {
			if(employeeService.employeeExists(salaryReportDto.getEmployeeId()) == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdParameterNotFound, "/salaryreport");
			}
		}
		
		SalaryReport salaryReport = salaryReportMapper.dtoToSalaryReport(salaryReportDto, 0);
		salaryReportService.save(salaryReport);
		
		return GlobalFunctions.createOkResponseFromObject(salaryReport);
	}
	
	@DeleteMapping("/salaryreport/all")
	@ApiOperation(value = "Deletes all salary reports from the system")
	public void deleteAll() {
		salaryReportService.deleteAll();
	}
}
