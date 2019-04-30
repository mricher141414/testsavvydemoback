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

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TimesheetStatusController {
	
	@Autowired
	private TimesheetStatusService timesheetStatusService = new TimesheetStatusService();
	private TimesheetStatusMapper timesheetStatusMapper = new TimesheetStatusMapper();
	
	@GetMapping("/timesheetStatus")
	public List<TimesheetStatus> findAllTimesheetRows(){
		
		return timesheetStatusService.getAll();
		
	}
	
	@PostMapping("/timesheetStatus")
	public String post(@RequestBody TimesheetStatusDTO timesheetStatusDto) {
		
		TimesheetStatus timesheetRow = this.timesheetStatusMapper.DTOtoTimesheetStatus(timesheetStatusDto);
		
		this.timesheetStatusService.post(timesheetRow);
		
		return "{\"id\": "+timesheetRow.getId()+"}";
	}
	
	

}
