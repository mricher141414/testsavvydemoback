package com.example.Timesheet.com.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.Timesheet.com.Paths;
import com.example.Timesheet.com.dto.TimesheetRowDto;
import com.example.Timesheet.com.dto.TimesheetRowWithProject;
import com.example.Timesheet.com.mapper.TimesheetRowMapper;
import com.example.Timesheet.com.model.Role;
import com.example.Timesheet.com.model.TimesheetRow;
import com.example.Timesheet.com.service.TimesheetRowProjectService;
import com.example.Timesheet.com.service.TimesheetRowService;
import com.example.Timesheet.com.service.TimesheetService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "TimesheetRowController")
public class TimesheetRowController implements Serializable {
	
	private static final long serialVersionUID = 13957869395221319L;
	private static final Logger log = LogManager.getLogger(TimesheetRowController.class);

	@Autowired
	private TimesheetRowService timesheetRowService = new TimesheetRowService();
	
	@Autowired
	private TimesheetService timesheetService = new TimesheetService();
	
	@Autowired
	private TimesheetRowMapper timesheetRowMapper = new TimesheetRowMapper();
	
	@Autowired
	private TimesheetRowProjectService timeProjectService;
	
	@GetMapping(Paths.TimesheetRowBasicPath)
	@ApiOperation(value = "Returns a list of all timesheet rows in the system.", 
					response = TimesheetRow.class, responseContainer = "List")
	public List<TimesheetRow> getAll(){
		log.debug("Entering getAll");
		return timesheetRowService.getTimesheetRows();		
	}
	
	@GetMapping(Paths.TimesheetRowGetOne)
	@ApiOperation(value = "Returns the timesheet row with the specified identifier.", notes = "404 if the row's identifier cannot be found.", 
					response = TimesheetRow.class)
	public ResponseEntity<?> getOne(@ApiParam(value = "Id of the timesheet row to be found.", required = true, defaultValue = "1") @RequestParam(value="id") int id){
		log.debug("Entering getOne with id parameter of " + id);
		
		Optional<TimesheetRow> optionalRow = timesheetRowService.getById(id);
		
		if(optionalRow.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetRowIdNotFound, Paths.TimesheetRowGetOne);
		}
		
		TimesheetRowDto rowDto = timesheetRowMapper.timesheetRowToDto(optionalRow.get());
		TimesheetRowWithProject returnRow = timesheetRowMapper.rowDtoToRowWithProject(rowDto);
		
		return GlobalFunctions.createOkResponseFromObject(returnRow);
	}
	
	@PostMapping(Paths.TimesheetRowBasicPath)
	@ApiOperation(value = "Creates a new timesheet row in the system", notes = "404 if the project id or timesheet id in the body cannot be found.",
					response = TimesheetRow.class)
	public ResponseEntity<String> create(@ApiParam(value = "Timesheet row information for the new row to be created.", required = true)@RequestBody TimesheetRowDto timesheetRowDto) {
		log.debug("Entering create");
		
		if(timesheetRowDto.getTimesheetId() != null) {
			if(this.timesheetService.getById(timesheetRowDto.getTimesheetId()).isPresent() == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetIdNotFound, Paths.TimesheetRowBasicPath);
			}
		}
		
		TimesheetRow timesheetRow = this.timesheetRowMapper.dtoToTimesheetRow(timesheetRowDto, 0);
		
		timesheetRow = timesheetRowService.save(timesheetRow);
		
		return GlobalFunctions.createOkResponseFromObject(timesheetRow);
		
	}
	
	@PutMapping(Paths.TimesheetRowBasicPath)
	@ApiOperation(value = "Updates a timesheet row in the system by their identifier.", notes = "404 if any of the row's identifier in the address, project id or timesheet id specified in the body is not found",
					response = TimesheetRow.class)
	public ResponseEntity<String> edit(@ApiParam("Timesheet row information to be modified. There is no need to keep values that will not be modified.") @RequestBody TimesheetRowDto timesheetRowDto,
										@ApiParam(value = "Id of the timesheet row to be modified. Cannot be null.", required = true) @RequestParam int id) {
		log.debug("Entering edit with id parameter of " + id);
		
		if(timesheetRowService.getById(id).isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetRowIdNotFound, Paths.TimesheetRowBasicPath);
		}
		
		if(timesheetRowDto.getTimesheetId() != null) {
			if(this.timesheetService.getById(timesheetRowDto.getTimesheetId()).isPresent() == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetIdNotFound, Paths.TimesheetRowBasicPath);
			}
		}
		
		TimesheetRow timesheetRow = timesheetRowMapper.dtoToTimesheetRow(timesheetRowDto, id);
		
		timesheetRow = timesheetRowService.save(timesheetRow);
		
		return GlobalFunctions.createOkResponseFromObject(timesheetRow);
	}
	
	@DeleteMapping(Paths.TimesheetRowBasicPath)
	@ApiOperation(value = "Delete a timesheet row in the system by their identifier.", notes = "404 if the row's identifier cannot be found. <br> 400 if the row is still referenced by a timesheetRowProject.",
					response = TimesheetRow.class)
	public ResponseEntity<String> delete(@ApiParam(value = "Id of the timesheet row to be deleted", required = true) @RequestParam(value="id") int id) {
		log.debug("Entering delete with id parameter of " + id);
		
		Optional<TimesheetRow> optionalRow = this.timesheetRowService.getById(id);
		
		if(optionalRow.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetRowIdNotFound, Paths.TimesheetRowBasicPath);
		}
		
		TimesheetRow timesheetRow = optionalRow.get();
		
		if(timeProjectService.getByTimesheetRowId(id).size() > 0) {
			return GlobalFunctions.createBadRequest(GlobalMessages.TimeProjectUsesTimesheetRowCannotDelete, Paths.TimesheetRowBasicPath);
		}
		
		this.timesheetRowService.deleteTimesheetRow(timesheetRow);
		
		return GlobalFunctions.createOkResponseFromObject(timesheetRow);
	}
}
