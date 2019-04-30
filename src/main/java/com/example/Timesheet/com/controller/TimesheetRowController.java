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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Timesheet.com.GlobalFunctions;
import com.example.Timesheet.com.GlobalVars;
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
	public ResponseEntity<?> findAllTimesheetRows(@RequestParam(value="id") int id){
		
		Optional<TimesheetRow> optionalRow = timesheetRowService.getById(id);
		
		if(optionalRow.isPresent()) {
			return new ResponseEntity<TimesheetRow>(optionalRow.get(), HttpStatus.OK);
		}
		else {
			return GlobalFunctions.createNotFoundResponse(GlobalVars.TimesheetRowIdNotFound, "/timesheetRow/one");
		}
	}
	
	@PostMapping("/timesheetRow")
	public String post(@RequestBody TimesheetRowDTO timesheetRowDto) {
		TimesheetRow timesheetRow = this.timesheetRowMapper.DTOtoTimesheetRow(timesheetRowDto);
		
		this.timesheetRowService.postTimesheetRow(timesheetRow);
		
		return "{\"id\": "+timesheetRow.getId()+"}";
		
	}
	
	@DeleteMapping("/timesheetRow")
	public ResponseEntity<String> delete(@RequestParam(value="id") int id) {
		
		Optional<TimesheetRow> optionalRow = this.timesheetRowService.getById(id);
		
		if(optionalRow.isPresent()) {
		
			TimesheetRow timesheetRow = optionalRow.get();
			this.timesheetRowService.deleteTimesheetRow(timesheetRow);
			
			return new ResponseEntity<String>(GlobalVars.TimesheetRowDeleteSuccessful, HttpStatus.OK);
		}
		else {
			return GlobalFunctions.createNotFoundResponse(GlobalVars.TimesheetRowIdNotFound, "/timesheetRow");
		}
	}

}
