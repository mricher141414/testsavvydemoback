package com.example.Timesheet.com.controller;

import java.io.Serializable;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.example.Timesheet.com.Paths;
import com.example.Timesheet.com.dto.SalaryReportDto;
import com.example.Timesheet.com.mapper.SalaryReportMapper;
import com.example.Timesheet.com.model.Role;
import com.example.Timesheet.com.model.SalaryReport;
import com.example.Timesheet.com.service.EmployeeService;
import com.example.Timesheet.com.service.SalaryReportService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "SalaryReportController")
public class SalaryReportController implements Serializable {

	private static final long serialVersionUID = 2397879932845739576L;
	private static final Logger log = LogManager.getLogger(SalaryReportController.class);
	
	@Autowired
	private SalaryReportService salaryReportService;
	
	@Autowired
	private SalaryReportMapper salaryReportMapper;
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping(Paths.SalaryReportBasicPath)
	@ApiOperation(value = "Returns a list of all salary reports in the system.", 
					response = SalaryReport.class, responseContainer = "List")
	public List<SalaryReport> getAll() {
		log.debug("Entering getAll");
		return salaryReportService.getAll();
	}
	
	@PostMapping(Paths.SalaryReportBasicPath)
	@ApiOperation(value = "Creates a new salary report in the system.", notes = "404 if employee id in the body is not null, but the employee it's referencing cannot be found.",
					response = SalaryReport.class)
	public ResponseEntity<String> create(@ApiParam(value = "Salary report information for the new one to be created", required = true)@RequestBody SalaryReportDto salaryReportDto) {
		log.debug("Entering create");
		
		if(salaryReportDto.getEmployeeId() != null) {
			if(employeeService.employeeExists(salaryReportDto.getEmployeeId()) == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdParameterNotFound, Paths.SalaryReportBasicPath);
			}
		}
		
		SalaryReport salaryReport = salaryReportMapper.dtoToSalaryReport(salaryReportDto, 0);
		salaryReportService.save(salaryReport);
		
		return GlobalFunctions.createOkResponseFromObject(salaryReport);
	}
	
	@DeleteMapping(Paths.SalaryReportDeleteAll)
	@ApiOperation(value = "Deletes all salary reports from the system")
	public void deleteAll() {
		log.debug("Entering deleteAll");
		
		salaryReportService.deleteAll();
	}
}
