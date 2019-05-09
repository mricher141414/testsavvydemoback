package com.example.Timesheet.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Timesheet.com.dto.TimesheetStatusDTO;
import com.example.Timesheet.com.mapper.TimesheetStatusMapper;
import com.example.Timesheet.com.model.TimesheetStatus;
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
	
	@GetMapping("/timesheetStatus")
	@ApiOperation("Returns a list of all timesheet statuses in the system.")
	public List<TimesheetStatus> findAllTimesheetStatuses(){
		
		return timesheetStatusService.getAll();
		
	}
	
	@PostMapping("/timesheetStatus")
	@ApiOperation("Creates a new timesheet status in the system")
	public String createTimesheetStatus(@ApiParam(value = "Timesheet status information for the new status to be created", required = true)@RequestBody TimesheetStatusDTO timesheetStatusDto) {
		
		TimesheetStatus timesheetRow = this.timesheetStatusMapper.DTOtoTimesheetStatus(timesheetStatusDto, 0);
		
		this.timesheetStatusService.post(timesheetRow);
		
		return "{\"id\": "+timesheetRow.getId()+"}";
	}
	
	

}
