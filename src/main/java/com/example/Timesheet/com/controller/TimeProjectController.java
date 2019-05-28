package com.example.Timesheet.com.controller;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

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
import com.example.Timesheet.com.GlobalVars;
import com.example.Timesheet.com.dto.DepartmentDto;
import com.example.Timesheet.com.dto.TimeProjectDto;
import com.example.Timesheet.com.mapper.TimeProjectMapper;
import com.example.Timesheet.com.model.Department;
import com.example.Timesheet.com.model.Project;
import com.example.Timesheet.com.model.TimeProject;
import com.example.Timesheet.com.service.ProjectService;
import com.example.Timesheet.com.service.TimeProjectService;
import com.example.Timesheet.com.service.TimesheetRowService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "TimeProjectController")
public class TimeProjectController {

	@Autowired
	private TimeProjectService timeProjectService;
	
	@Autowired
	private TimeProjectMapper timeProjectMapper;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private TimesheetRowService timesheetRowService;
	
	
	@GetMapping("/timeproject")
	@ApiOperation("Returns a list of all timeProjects in the system.")
	public List<TimeProject> getAll(){
		return timeProjectService.getAll();
	}
	
	@PostMapping("/timeproject")
	@ApiOperation("Create a new timeProject in the system.")
	public ResponseEntity<String> create(@ApiParam(value = "TimeProject information for the new time_project to be created.", required = true)@RequestBody TimeProjectDto timeProjectDto) {
		
		if(timeProjectDto.getProjectId() != null) {
			if(projectService.getById(timeProjectDto.getProjectId()).isPresent() == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.ProjectIdParameterNotFound, "/timeproject");
			}
		}
		
		if(timeProjectDto.getTimesheetRowId() != null) {
			if(timesheetRowService.getById(timeProjectDto.getTimesheetRowId()).isPresent() == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetRowIdParameterNotFound, "/timeproject");
			}
		}
		
		TimeProject timeProject = timeProjectMapper.DtoToTimeProject(timeProjectDto, 0);
		timeProjectService.save(timeProject);
		return GlobalFunctions.createOkResponseFromObject(timeProject);
	}
	
	@PutMapping("/timeproject")
	@ApiOperation(value = "Updates a timeProject in the system by their identifier.", notes = "404 if the time_project's identifier is not found")
	public ResponseEntity<?> edit(@ApiParam("timeProject information to be modified")@RequestBody TimeProjectDto timeProjectDto,
										@ApiParam(value = "Id of the timeProject to be modified. Cannot be null", required = true)@RequestParam(value="id") int id) {
		
		if(timeProjectService.getById(id).isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimeProjectIdNotFound, "/timeproject");
		}
		
		if(timeProjectDto.getProjectId() != null) {
			if(projectService.getById(timeProjectDto.getProjectId()).isPresent() == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.ProjectIdParameterNotFound, "/timeproject");
			}
		}
		
		if(timeProjectDto.getTimesheetRowId() != null) {
			if(timesheetRowService.getById(timeProjectDto.getTimesheetRowId()).isPresent() == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetRowIdParameterNotFound, "/timeproject");
			}
		}
		
		TimeProject timeProject = timeProjectMapper.DtoToTimeProject(timeProjectDto, id);
		timeProjectService.save(timeProject);
		return GlobalFunctions.createOkResponseFromObject(timeProject);
	}
	
	@DeleteMapping("/timeproject")
	@ApiOperation(value = "Deletes a timeProject from the system.", notes = "404 if the timeProject's identifier cannot be found")
	public ResponseEntity<?> delete(@ApiParam(value = "Id of the timeProject to be deleted. Cannot be null.", required = true)@RequestParam(value="id") int id) {

		Optional<TimeProject> optionalTimeProject = timeProjectService.getById(id);
		
		if(optionalTimeProject.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimeProjectIdNotFound, "/timeproject");
		}
		
		TimeProject timeProject = optionalTimeProject.get();
		
		timeProjectService.delete(timeProject);
		return GlobalFunctions.createOkResponseFromObject(timeProject);
	}
	
	@GetMapping("/average")
	@ApiOperation(value = "Returns the average amount of hours worked on a project per day in the X weeks before the given date.", notes = "404 if project id cannot be found, 400 if the project was not started by the end of the first week, or if the project ended before the beginning of the last week")
	public ResponseEntity<?> getAverageTimeWorked(@ApiParam(value = "Id of the project to calculate the averages for.", required = true)@RequestParam(value="id") int id, 
													@ApiParam(value = "Date to end the calculation", required = true) @RequestParam(value = "date") Date date, 
													@ApiParam(value = "Amount of weeks to go back from the sent date.", required = true) @RequestParam(value="weeks") int weeks) {
		
		Optional<Project> optionalProject = projectService.getById(id);
		
		if(optionalProject.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.ProjectIdNotFound, "/average");
		}
		
		Project project = optionalProject.get();
		
		Date latestSunday = GlobalFunctions.findLatestSunday(date);
		Date firstSunday = new Date(0L);
		Date firstSaturday = new Date(0L);
		
		firstSunday.setTime(latestSunday.getTime() - 7 * (weeks - 1) * GlobalVars.MillisecondsPerDay);
		firstSaturday.setTime(firstSunday.getTime() + 6 * GlobalVars.MillisecondsPerDay);
		
		if(project.getStartDate().getTime() > firstSaturday.getTime()) {
			return GlobalFunctions.createBadRequest(GlobalMessages.AverageDateParameterTooEarly, "/average");
		}
		
		if(project.getEndDate().getTime() < latestSunday.getTime()) {
			return GlobalFunctions.createBadRequest(GlobalMessages.AverageDateParameterTooLate, "/average");
		}
		
		Float averageHoursPerDay = timeProjectService.calculateAverageTimeWorked(id, firstSunday, weeks);
		
		return GlobalFunctions.createOkResponseFromObject(averageHoursPerDay);
	}
}
