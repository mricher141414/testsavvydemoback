package com.example.Timesheet.com.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.example.Timesheet.com.GlobalVars;
import com.example.Timesheet.com.dto.ProjectDto;
import com.example.Timesheet.com.mapper.ProjectMapper;
import com.example.Timesheet.com.model.Employee;
import com.example.Timesheet.com.model.Project;
import com.example.Timesheet.com.service.ClientService;
import com.example.Timesheet.com.service.EmployeeService;
import com.example.Timesheet.com.service.ProjectService;
import com.example.Timesheet.com.service.TimesheetRowService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "ProjectController")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService = new ProjectService();
	
	@Autowired
	private ProjectMapper projectMapper = new ProjectMapper();
	
	@Autowired
	private TimesheetRowService timesheetRowService = new TimesheetRowService();
	
	@Autowired
	private ClientService clientService = new ClientService();
	
	@Autowired
	private EmployeeService employeeService = new EmployeeService();
	
	@GetMapping("/project")
	@ApiOperation("Returns a list of all projects in the system.")
	public List<Project> getAll() {
		return projectService.getAll();
	}
	
	@PostMapping("/project")
	@ApiOperation("Creates a new project in the system.")
	public ResponseEntity<String> create(@ApiParam(value = "Project information for the new project to be created.", required = true)@RequestBody ProjectDto projectDto) {
		
		if (projectDto.getProjectManagerId() != null) {
			  if(employeeService.getById(projectDto.getProjectManagerId()).isPresent() == false) {
				  return GlobalFunctions.createNotFoundResponse(GlobalVars.ManagerIdNotFound, "/project");
			  }
		  }
		  
		  if (projectDto.getClientId() != null) {
			  if(clientService.getById(projectDto.getClientId()).isPresent() == false) {
				  return GlobalFunctions.createNotFoundResponse(GlobalVars.ClientIdParameterNotFound, "/project");
				  }
			  }
		 
		 Project project = projectMapper.dtoToProject(projectDto, 0);
		 
		 projectService.save(project);
		 
		 return new ResponseEntity<String>("{\"id\": "+project.getId()+"}", HttpStatus.OK);
	}
	
	@PutMapping("/project")
	@ApiOperation(value = "Updates a project in the system by their identifier.", notes = "404 if any of the project's identifier specified in the address, project manager id or client id specified in the body is not found.")
	public ResponseEntity<String> edit(@ApiParam("Project information to be modified. There is no need to keep values that will not be modified.")@RequestBody ProjectDto projectDto, 
										@ApiParam(value = "Id of the project to be modified. Cannot be null.", required = true)@RequestParam int id) {
		
		if(projectService.getById(id).isPresent() == false) {
			  return GlobalFunctions.createNotFoundResponse(GlobalVars.ProjectIdNotFound, "/project");
		}
		
		if (projectDto.getProjectManagerId() != null) {
			  if(employeeService.getById(projectDto.getProjectManagerId()).isPresent() == false) {
				  return GlobalFunctions.createNotFoundResponse(GlobalVars.ManagerIdNotFound, "/project");
			  }
		  }
		  
		  if (projectDto.getClientId() != null) {
			  if(clientService.getById(projectDto.getClientId()).isPresent() == false) {
				  return GlobalFunctions.createNotFoundResponse(GlobalVars.RoleIdNotFound, "/project");
				  }
			  }
		 
		 Project project = projectMapper.dtoToProject(projectDto, id);
		 
		 projectService.save(project);
		 
		 return new ResponseEntity<String>("{\"id\": "+project.getId()+"}", HttpStatus.OK);
	}
	
	@DeleteMapping("/project")
	@ApiOperation(value = "Deletes a project in the system by their identifier.", notes = "404 if the project's identifier cannot be found.<br> 400 if the project is still referenced by a timesheet row.")
	public ResponseEntity<String> delete(@RequestParam int id) {
		return null;
	}
}
