package com.example.Timesheet.com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Timesheet.com.GlobalFunctions;
import com.example.Timesheet.com.GlobalMessages;
import com.example.Timesheet.com.dto.TimesheetStatusDto;
import com.example.Timesheet.com.mapper.TimesheetStatusMapper;
import com.example.Timesheet.com.model.TimesheetStatus;
import com.example.Timesheet.com.service.TimesheetService;
import com.example.Timesheet.com.service.TimesheetStatusService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "TimesheetStatusController")
public class TimesheetStatusController {
	
	@Autowired
	private TimesheetStatusService timesheetStatusService = new TimesheetStatusService();
	private TimesheetStatusMapper timesheetStatusMapper = new TimesheetStatusMapper();
	
	@Autowired
	private TimesheetService timesheetService = new TimesheetService();
	
	@GetMapping("/timesheetStatus")
	@ApiOperation("Returns a list of all timesheet statuses in the system.")
	public List<TimesheetStatus> getAll(){
		
		return timesheetStatusService.getAll();
		
	}
	
	@PostMapping("/timesheetStatus")
	@ApiOperation("Creates a new timesheet status in the system")
	public String create(@ApiParam(value = "Timesheet status information for the new status to be created", required = true)@RequestBody TimesheetStatusDto timesheetStatusDto) {
		
		TimesheetStatus timesheetStatus = this.timesheetStatusMapper.DTOtoTimesheetStatus(timesheetStatusDto, 0);
		
		this.timesheetStatusService.save(timesheetStatus);
		
		return "{\"id\": "+timesheetStatus.getId()+"}";
	}
	
	@PutMapping("/timesheetStatus")
	@ApiOperation(value = "Updates a timesheet status in the system by their identifier.", notes = "404 if the status's identifier cannot be found.")
	public ResponseEntity<String> edit(@ApiParam(value = "Timesheet status information to be modified. There is no need to keep values that will not be modified.")@RequestBody TimesheetStatusDto timesheetStatusDto,
										@ApiParam(value = "Id of the timesheet status to be modified. Cannot be null.", required = true) @RequestParam int id) {
		
		if(timesheetStatusService.getById(id).isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetStatusIdNotFound, "/timesheetStatus");
		}
		
		TimesheetStatus timesheetStatus = timesheetStatusMapper.DTOtoTimesheetStatus(timesheetStatusDto, id);
		
		timesheetStatusService.save(timesheetStatus);
		
		return new ResponseEntity<String>(GlobalMessages.TimesheetPutSuccessful, HttpStatus.OK);
	}
	
	@DeleteMapping("/timesheetStatus")
	@ApiOperation(value = "Delets a timesheet status in the system by their identifier.", notes = "404 of the status's identifier cannot be found. 400 if timesheets still use the status.")
	public ResponseEntity<String> delete(@ApiParam(value = "Id of the timesheet status to be deleted. Cannot be null.", required = true) @RequestParam int id) {
		
		Optional<TimesheetStatus> optionalStatus = timesheetStatusService.getById(id);
		
		if (optionalStatus.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetStatusIdNotFound, "/timesheetStatus");
		}
		
		TimesheetStatus timesheetStatus = optionalStatus.get();
		
		if(timesheetService.getByTimesheetStatusId(id).size() > 0) {
			return GlobalFunctions.createBadRequest(GlobalMessages.TimesheetUsesTimesheetStatusCannotDelete, "/timesheetStatus");
		}
		
		timesheetStatusService.delete(timesheetStatus);
		return new ResponseEntity<String>(GlobalMessages.TimesheetStatusDeleteSuccessful, HttpStatus.OK);
	}
}
