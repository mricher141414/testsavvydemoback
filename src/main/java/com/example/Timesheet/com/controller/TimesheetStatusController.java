package com.example.Timesheet.com.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

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
public class TimesheetStatusController implements Serializable {
	
	private static final long serialVersionUID = -3673883789362246261L;
	private static final Logger log = LogManager.getLogger(TimesheetStatusController.class);

	@Autowired
	private TimesheetStatusService timesheetStatusService = new TimesheetStatusService();
	
	@Autowired
	private TimesheetStatusMapper timesheetStatusMapper = new TimesheetStatusMapper();
	
	@Autowired
	private TimesheetService timesheetService = new TimesheetService();
	
	@GetMapping(Paths.TimesheetStatusBasicPath)
	@ApiOperation(value = "Returns a list of all timesheet statuses in the system.",
					response = TimesheetStatus.class, responseContainer = "List")
	public List<TimesheetStatus> getAll(){
		log.debug("Entering getAll");
		return timesheetStatusService.getAll();
	}
	
	@GetMapping(Paths.TimesheetStatusGetOne)
	@ApiOperation(value = "Returns the timesheetStatus with the specified identifier.", notes = "404 if the status's identifier cannot be found.",
					response = TimesheetStatus.class)
	public ResponseEntity<?> getOne(@ApiParam(value = "Id of the timesheetStatus to be found.", required = true) @RequestParam(value="id") int id) {
		log.debug("Entering getOne with id parameter of " + id);
		
		Optional<TimesheetStatus> optionalTimesheetStatus = timesheetStatusService.getById(id);
		
		if(optionalTimesheetStatus.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetStatusIdNotFound, Paths.TimesheetStatusGetOne);
		}
		
		return GlobalFunctions.createOkResponseFromObject(optionalTimesheetStatus.get());
	}
	
	@PostMapping(Paths.TimesheetStatusBasicPath)
	@ApiOperation(value = "Creates a new timesheet status in the system",
					response = TimesheetStatus.class)
	public ResponseEntity<String> create(@ApiParam(value = "Timesheet status information for the new status to be created", required = true)@RequestBody TimesheetStatusDto timesheetStatusDto) {
		log.debug("Entering create");
		
		TimesheetStatus timesheetStatus = this.timesheetStatusMapper.dtoToTimesheetStatus(timesheetStatusDto, 0);
		
		timesheetStatus = timesheetStatusService.save(timesheetStatus);
		
		return GlobalFunctions.createOkResponseFromObject(timesheetStatus);
	}
	
	@PutMapping(Paths.TimesheetStatusBasicPath)
	@ApiOperation(value = "Updates a timesheet status in the system by their identifier.", notes = "404 if the status's identifier cannot be found.",
					response = TimesheetStatus.class)
	public ResponseEntity<String> edit(@ApiParam(value = "Timesheet status information to be modified. There is no need to keep values that will not be modified.")@RequestBody TimesheetStatusDto timesheetStatusDto,
										@ApiParam(value = "Id of the timesheet status to be modified. Cannot be null.", required = true) @RequestParam int id) {
		log.debug("Entering edit with id parameter of " + id);
		
		if(timesheetStatusService.getById(id).isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetStatusIdNotFound, Paths.TimesheetStatusBasicPath);
		}
		
		try {
			
		}
		catch (OptimisticLockException e) {
			return GlobalFunctions.createConflictResponse(GlobalMessages.TimesheetStatusNotUpToDate, Paths.TimesheetStatusBasicPath);
		}
		TimesheetStatus timesheetStatus = timesheetStatusMapper.dtoToTimesheetStatus(timesheetStatusDto, id);
		
		timesheetStatus = timesheetStatusService.save(timesheetStatus);
		
		return GlobalFunctions.createOkResponseFromObject(timesheetStatus);
	}
	
	@DeleteMapping(Paths.TimesheetStatusBasicPath)
	@ApiOperation(value = "Delets a timesheet status in the system by their identifier.", notes = "404 of the status's identifier cannot be found. 400 if timesheets still use the status.", 
					response = TimesheetStatus.class)
	public ResponseEntity<String> delete(@ApiParam(value = "Id of the timesheet status to be deleted. Cannot be null.", required = true) @RequestParam int id) {
		log.debug("Entering delete with id parameter of " + id);
		
		Optional<TimesheetStatus> optionalStatus = timesheetStatusService.getById(id);
		
		if (optionalStatus.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetStatusIdNotFound, Paths.TimesheetStatusBasicPath);
		}
		
		TimesheetStatus timesheetStatus = optionalStatus.get();
		
		if(timesheetService.getByTimesheetStatusId(id).size() > 0) {
			return GlobalFunctions.createBadRequest(GlobalMessages.TimesheetUsesTimesheetStatusCannotDelete, Paths.TimesheetStatusBasicPath);
		}
		
		timesheetStatusService.delete(timesheetStatus);
		return GlobalFunctions.createOkResponseFromObject(timesheetStatus);
	}
}
