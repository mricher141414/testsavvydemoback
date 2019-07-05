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
import com.example.Timesheet.com.dto.TimesheetRowProjectDto;
import com.example.Timesheet.com.mapper.TimesheetRowProjectMapper;
import com.example.Timesheet.com.model.TimesheetRowProject;
import com.example.Timesheet.com.service.ProjectService;
import com.example.Timesheet.com.service.TimesheetRowProjectService;
import com.example.Timesheet.com.service.TimesheetRowService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "TimesheetRowProjectController")
public class TimesheetRowProjectController implements Serializable {

	private static final long serialVersionUID = 4971158221323338282L;
	private static final Logger log = LogManager.getLogger(TimesheetRowProjectController.class);

	@Autowired
	private TimesheetRowProjectService timesheetRowProjectService;
	
	@Autowired
	private TimesheetRowProjectMapper timesheetRowProjectMapper;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private TimesheetRowService timesheetRowService;
	
	
	@GetMapping(Paths.TimesheetRowProjectBasicPath)
	@ApiOperation(value = "Returns a list of all timesheetRowProjects in the system.",
					response = TimesheetRowProject.class, responseContainer = "List")
	public List<TimesheetRowProject> getAll(){
		log.debug("Entering getAll");
		return timesheetRowProjectService.getAll();
	}
	
	@PostMapping(Paths.TimesheetRowProjectBasicPath)
	@ApiOperation(value = "Creates a new timesheetRowProject in the system.", notes = "404 if a projectId or timesheetRowId is provided and the entity it is referring to cannot be found.",
					response = TimesheetRowProject.class)
	public ResponseEntity<String> create(@ApiParam(value = "TimesheetRowProject information for the new timesheetRowProject to be created.", required = true)@RequestBody TimesheetRowProjectDto timesheetRowProjectDto) {
		log.debug("Entering create");
		
		if(timesheetRowProjectDto.getProjectId() != null) {
			if(projectService.getById(timesheetRowProjectDto.getProjectId()).isPresent() == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.ProjectIdParameterNotFound, Paths.TimesheetRowProjectBasicPath);
			}
		}
		
		if(timesheetRowProjectDto.getTimesheetRowId() != null) {
			if(timesheetRowService.getById(timesheetRowProjectDto.getTimesheetRowId()).isPresent() == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetRowIdParameterNotFound, Paths.TimesheetRowProjectBasicPath);
			}
		}
		
		TimesheetRowProject timesheetRowProject = timesheetRowProjectMapper.dtoToTimesheetRowProject(timesheetRowProjectDto, 0);
		timesheetRowProject = timesheetRowProjectService.save(timesheetRowProject);
		return GlobalFunctions.createOkResponseFromObject(timesheetRowProject);
	}
	
	@PutMapping(Paths.TimesheetRowProjectBasicPath)
	@ApiOperation(value = "Updates a timesheetRowProject in the system by their identifier.", notes = "404 if the timesheetRowProject's identifier, or the provided projectId or timesheetRowId cannot be found",
					response = TimesheetRowProject.class)
	public ResponseEntity<?> edit(@ApiParam("timesheetRowProject information to be modified")@RequestBody TimesheetRowProjectDto timesheetRowProjectDto,
										@ApiParam(value = "Id of the timesheetRowProject to be modified. Cannot be null", required = true)@RequestParam(value="id") int id) {
		log.debug("Entering edit with id parameter of " + id);
		
		if(timesheetRowProjectService.getById(id).isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetRowProjectIdNotFound, Paths.TimesheetRowProjectBasicPath);
		}
		
		if(timesheetRowProjectDto.getProjectId() != null) {
			if(projectService.getById(timesheetRowProjectDto.getProjectId()).isPresent() == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.ProjectIdParameterNotFound, Paths.TimesheetRowProjectBasicPath);
			}
		}
		
		if(timesheetRowProjectDto.getTimesheetRowId() != null) {
			if(timesheetRowService.getById(timesheetRowProjectDto.getTimesheetRowId()).isPresent() == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetRowIdParameterNotFound, Paths.TimesheetRowProjectBasicPath);
			}
		}
		
		try {
			TimesheetRowProject timesheetRowProject = timesheetRowProjectMapper.dtoToTimesheetRowProject(timesheetRowProjectDto, id);
			timesheetRowProject = timesheetRowProjectService.save(timesheetRowProject);
			return GlobalFunctions.createOkResponseFromObject(timesheetRowProject);
		}
		catch (OptimisticLockException e) {
			return GlobalFunctions.createConflictResponse(GlobalMessages.TimesheetRowProjectNotUpToDate, Paths.TimesheetRowBasicPath);
		}
	}
	
	@DeleteMapping(Paths.TimesheetRowProjectBasicPath)
	@ApiOperation(value = "Deletes a timesheetRowProject from the system.", notes = "404 if the timeshhetRowProject's identifier cannot be found", 
					response = TimesheetRowProject.class)
	public ResponseEntity<?> delete(@ApiParam(value = "Id of the timesheetRowProject to be deleted. Cannot be null.", required = true)@RequestParam(value="id") int id) {
		log.debug("Entering delete with id parameter of " + id);
		
		Optional<TimesheetRowProject> optionalTimesheetRowProject = timesheetRowProjectService.getById(id);
		
		if(optionalTimesheetRowProject.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetRowProjectIdNotFound, Paths.TimesheetRowProjectBasicPath);
		}
		
		TimesheetRowProject timesheetRowProject = optionalTimesheetRowProject.get();
		
		timesheetRowProjectService.delete(timesheetRowProject);
		return GlobalFunctions.createOkResponseFromObject(timesheetRowProject);
	}
}
