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
import com.example.Timesheet.com.dto.TimesheetRowDto;
import com.example.Timesheet.com.dto.TimesheetRowWithProject;
import com.example.Timesheet.com.mapper.TimesheetRowMapper;
import com.example.Timesheet.com.model.TimesheetRow;
import com.example.Timesheet.com.service.ProjectService;
import com.example.Timesheet.com.service.TimesheetRowProjectService;
import com.example.Timesheet.com.service.TimesheetRowService;
import com.example.Timesheet.com.service.TimesheetService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "TimesheetRowController")
public class TimesheetRowController {
	
	@Autowired
	private TimesheetRowService timesheetRowService = new TimesheetRowService();
	
	@Autowired
	private TimesheetService timesheetService = new TimesheetService();
	
	@Autowired
	private ProjectService projectService = new ProjectService();
	
	@Autowired
	private TimesheetRowMapper timesheetRowMapper = new TimesheetRowMapper();
	
	@Autowired
	private TimesheetRowProjectService timeProjectService;
	
	@GetMapping("/timesheetRow")
	@ApiOperation("Returns a list of all timesheet rows in the system.")
	public List<TimesheetRow> getAll(){
		
		return timesheetRowService.getTimesheetRows();
		
	}
	
	@GetMapping("/timesheetRow/one")
	@ApiOperation(value = "Returns the timesheet row with the specified identifier.", notes = "404 if the row's identifier cannot be found.")
	public ResponseEntity<?> getOne(@ApiParam(value = "Id of the timesheet row to be found.", required = true, defaultValue = "1") @RequestParam(value="id") int id){
		
		Optional<TimesheetRow> optionalRow = timesheetRowService.getById(id);
		
		if(optionalRow.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetRowIdNotFound, "/timesheetRow/one");
		}
		
		TimesheetRowDto rowDto = timesheetRowMapper.TimesheetRowToDTO(optionalRow.get());
		TimesheetRowWithProject returnRow = timesheetRowMapper.rowDtoToRowTimeProject(rowDto);
		
		return GlobalFunctions.createOkResponseFromObject(returnRow);
	}
	
	@PostMapping("/timesheetRow")
	@ApiOperation(value = "Creates a new timesheet row in the system", notes = "404 if the project id or timesheet id in the body cannot be found.")
	public ResponseEntity<String> create(@ApiParam(value = "Timesheet row information for the new row to be created.", required = true)@RequestBody TimesheetRowDto timesheetRowDto) {
		
		if(timesheetRowDto.getTimesheetId() != null) {
			if(this.timesheetService.getById(timesheetRowDto.getTimesheetId()).isPresent() == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetIdNotFound, "/timesheetRow");
			}
		}
		
		TimesheetRow timesheetRow = this.timesheetRowMapper.DTOtoTimesheetRow(timesheetRowDto, 0);
		
		timesheetRow = timesheetRowService.save(timesheetRow);
		
		return GlobalFunctions.createOkResponseFromObject(timesheetRow);
		
	}
	
	@PutMapping("/timesheetRow")
	@ApiOperation(value = "Updates a timesheet row in the system by their identifier.", notes = "404 if any of the row's identifier in the address, project id or timesheet id specified in the body is not found")
	public ResponseEntity<String> edit(@ApiParam("Timesheet row information to be modified. There is no need to keep values that will not be modified.") @RequestBody TimesheetRowDto timesheetRowDto,
										@ApiParam(value = "Id of the timesheet row to be modified. Cannot be null.", required = true) @RequestParam int id) {
		
		if(timesheetRowService.getById(id).isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetRowIdNotFound, "/timesheetRow");
		}
		
		if(timesheetRowDto.getTimesheetId() != null) {
			if(this.timesheetService.getById(timesheetRowDto.getTimesheetId()).isPresent() == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetIdNotFound, "/timesheetRow");
			}
		}
		
		TimesheetRow timesheetRow = timesheetRowMapper.DTOtoTimesheetRow(timesheetRowDto, id);
		
		timesheetRow = timesheetRowService.save(timesheetRow);
		
		return GlobalFunctions.createOkResponseFromObject(timesheetRow);
	}
	
	@DeleteMapping("/timesheetRow")
	@ApiOperation(value = "Delete a timesheet row in the system by their identifier.", notes = "404 if the row's identifier cannot be found. <br> 400 if the row is still referenced by a timeProject.")
	public ResponseEntity<String> delete(@ApiParam(value = "Id of the timesheet row to be deleted", required = true) @RequestParam(value="id") int id) {
		
		Optional<TimesheetRow> optionalRow = this.timesheetRowService.getById(id);
		
		if(optionalRow.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetRowIdNotFound, "/timesheetRow");
		}
		
		TimesheetRow timesheetRow = optionalRow.get();
		
		if(timeProjectService.getByTimesheetRowId(id).size() > 0) {
			return GlobalFunctions.createBadRequest(GlobalMessages.TimeProjectUsesTimesheetRowCannotDelete, "/timesheetRow");
		}
		
		this.timesheetRowService.deleteTimesheetRow(timesheetRow);
		
		return GlobalFunctions.createOkResponseFromObject(timesheetRow);
	}
}
