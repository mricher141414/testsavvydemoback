package com.example.Timesheet.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Timesheet.com.dto.TimesheetDTO;
import com.example.Timesheet.com.mapper.TimesheetMapper;
import com.example.Timesheet.com.model.Role;
import com.example.Timesheet.com.model.Timesheet;
import com.example.Timesheet.com.service.TimesheetService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TimesheetController {
	
	@Autowired
	private TimesheetService timesheetService = new TimesheetService();
	private TimesheetMapper timesheetMapper = new TimesheetMapper();
	
	@GetMapping("/timesheet")
	public List<Timesheet> findAllRoles(){
		
		return timesheetService.getTimesheets();
		
	}
	
	@PostMapping("/timesheet")
	public void post(TimesheetDTO timesheetDto) {
		Timesheet timesheet = this.timesheetMapper.DTOtoTimesheet(timesheetDto);
		
		this.timesheetService.postTimesheet(timesheet);
		
	}

}
