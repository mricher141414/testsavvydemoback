package com.example.Timesheet.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Timesheet.com.dto.TimesheetRowDTO;
import com.example.Timesheet.com.mapper.TimesheetRowMapper;
import com.example.Timesheet.com.model.TimesheetRow;
import com.example.Timesheet.com.service.TimesheetRowService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TimesheetRowController {
	
	@Autowired
	private TimesheetRowService timesheetRowService = new TimesheetRowService();
	private TimesheetRowMapper timesheetRowMapper = new TimesheetRowMapper();
	
	@GetMapping("/timesheetRow")
	public List<TimesheetRow> findAllTimesheetRows(){
		
		return timesheetRowService.getTimesheetRows();
		
	}
	
	@GetMapping("/timesheetRow/one")
	public TimesheetRow findAllTimesheetRows(@RequestParam(value="id") int id){
		
		return timesheetRowService.getById(id);
		
	}
	
	@PostMapping("/timesheetRow")
	public void post(@RequestBody TimesheetRowDTO timesheetRowDto) {
		

		TimesheetRow timesheetRow = this.timesheetRowMapper.DTOtoTimesheetRow(timesheetRowDto);
		
		this.timesheetRowService.postTimesheetRow(timesheetRow);
		
	}
	
	@DeleteMapping("/timesheetRow")
	public void delete(@RequestBody TimesheetRowDTO timesheetRowDto) {
		
		TimesheetRow timesheetRow = this.timesheetRowMapper.DTOtoTimesheetRow(timesheetRowDto);
		
		this.timesheetRowService.deleteTimesheetRow(timesheetRow);
		
	}

}
