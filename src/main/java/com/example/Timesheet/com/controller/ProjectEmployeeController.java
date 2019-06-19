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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Timesheet.com.GlobalFunctions;
import com.example.Timesheet.com.GlobalMessages;
import com.example.Timesheet.com.Paths;
import com.example.Timesheet.com.dto.ProjectEmployeeDto;
import com.example.Timesheet.com.mapper.ProjectEmployeeMapper;
import com.example.Timesheet.com.model.ProjectEmployee;
import com.example.Timesheet.com.service.EmployeeService;
import com.example.Timesheet.com.service.ProjectEmployeeService;
import com.example.Timesheet.com.service.ProjectService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "ProjectEmployeeController")
public class ProjectEmployeeController implements Serializable {

	private static final long serialVersionUID = 3218580205270170295L;
	private static final Logger log = LogManager.getLogger(ProjectEmployeeController.class);

	@Autowired
	private ProjectEmployeeMapper projectEmployeeMapper;
	
	@Autowired
	private ProjectEmployeeService projectEmployeeService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping(Paths.ProjectEmployeeBasicPath)
	@ApiOperation(value = "Returns a list of all employee-project assignations in the system.", 
					response = ProjectEmployee.class, responseContainer = "List")
	public List<ProjectEmployee> getAll() {
		log.debug("Entering getAll");
		return projectEmployeeService.getAll();
	}
	
	@PostMapping(Paths.ProjectEmployeeBasicPath)
	@ApiOperation(value = "Create a new employee-project assignation in the system.", notes = "404 if either the project's or the employee's identifier cannot be found",
					response = ProjectEmployee.class)
	public ResponseEntity<String> create(@ApiParam(value = "information about the assignation for it to be created", required = true)@RequestBody ProjectEmployeeDto projectEmployeeDto) {
		log.debug("Entering create");
		
		if(projectService.projectExists(projectEmployeeDto.getProjectId()) == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.ProjectIdParameterNotFound, Paths.ProjectEmployeeBasicPath);
		}
		
		if(employeeService.employeeExists(projectEmployeeDto.getEmployeeId()) == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdParameterNotFound, Paths.ProjectEmployeeBasicPath);
		}
		
		ProjectEmployee projectEmployee = projectEmployeeMapper.dtoToProjectEmployee(projectEmployeeDto, 0);
		projectEmployeeService.save(projectEmployee);
		return GlobalFunctions.createOkResponseFromObject(projectEmployee);
	}
	
	@DeleteMapping("/assignation")
	@ApiOperation(value = "Deletes an employee-project assignation from the system.", notes = "404 if the projectEmployee's id cannot be found",
					response = ProjectEmployee.class)
	public ResponseEntity<String> delete(@ApiParam(value = "Id of the employee-project assignation to be deleted", required = true)@RequestParam(value="id")int id) {
		log.debug("Entering delete with id parameter of " + id);
		
		Optional<ProjectEmployee> optionalProjectEmployee = projectEmployeeService.getById(id);
		
		if(optionalProjectEmployee.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.ProjectEmployeeIdNotFound, Paths.ProjectEmployeeBasicPath);
		}
		
		ProjectEmployee projectEmployee = optionalProjectEmployee.get();
		
		projectEmployeeService.delete(projectEmployee);
		return GlobalFunctions.createOkResponseFromObject(projectEmployee);
	}
}
