package com.example.Timesheet.com.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.example.Timesheet.com.dto.ProjectDto;
import com.example.Timesheet.com.dto.ProjectStatsEmployee;
import com.example.Timesheet.com.mapper.ProjectMapper;
import com.example.Timesheet.com.model.Employee;
import com.example.Timesheet.com.model.Project;
import com.example.Timesheet.com.model.ProjectEmployee;
import com.example.Timesheet.com.service.ClientService;
import com.example.Timesheet.com.service.EmployeeService;
import com.example.Timesheet.com.service.ProjectEmployeeService;
import com.example.Timesheet.com.service.ProjectService;
import com.example.Timesheet.com.service.TimeProjectService;
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
	private ClientService clientService = new ClientService();
	
	@Autowired
	private EmployeeService employeeService = new EmployeeService();
	
	@Autowired
	private TimeProjectService timeProjectService = new TimeProjectService();
	
	@Autowired
	private ProjectEmployeeService projectEmployeeService = new ProjectEmployeeService();
	
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
				  return GlobalFunctions.createNotFoundResponse(GlobalMessages.ManagerIdNotFound, "/project");
			  }
		  }
		  
		  if (projectDto.getClientId() != null) {
			  if(clientService.getById(projectDto.getClientId()).isPresent() == false) {
				  return GlobalFunctions.createNotFoundResponse(GlobalMessages.ClientIdParameterNotFound, "/project");
				  }
			  }
		 
		 Project project = projectMapper.dtoToProject(projectDto, 0);
		 
		 project = projectService.save(project);
		 
		 return GlobalFunctions.createOkResponseFromObject(project);
	}
	
	@PutMapping("/project")
	@ApiOperation(value = "Updates a project in the system by their identifier.", notes = "404 if any of the project's identifier specified in the address, project manager id or client id specified in the body is not found.")
	public ResponseEntity<String> edit(@ApiParam("Project information to be modified. There is no need to keep values that will not be modified.")@RequestBody ProjectDto projectDto, 
										@ApiParam(value = "Id of the project to be modified. Cannot be null.", required = true)@RequestParam int id) {
		
		if(projectService.getById(id).isPresent() == false) {
			  return GlobalFunctions.createNotFoundResponse(GlobalMessages.ProjectIdNotFound, "/project");
		}
		
		if (projectDto.getProjectManagerId() != null) {
			  if(employeeService.getById(projectDto.getProjectManagerId()).isPresent() == false) {
				  return GlobalFunctions.createNotFoundResponse(GlobalMessages.ManagerIdNotFound, "/project");
			  }
		  }
		  
		  if (projectDto.getClientId() != null) {
			  if(clientService.getById(projectDto.getClientId()).isPresent() == false) {
				  return GlobalFunctions.createNotFoundResponse(GlobalMessages.RoleIdNotFound, "/project");
				  }
			  }
		 
		 Project project = projectMapper.dtoToProject(projectDto, id);
		 
		 project = projectService.save(project);
		 
		 return GlobalFunctions.createOkResponseFromObject(project);
}
	
	@DeleteMapping("/project")
	@ApiOperation(value = "Deletes a project in the system by their identifier.", notes = "404 if the project's identifier cannot be found.<br> 400 if the project is still assigned to some employees or is still referenced by a timeProject.")
	public ResponseEntity<String> delete(@ApiParam(value = "Id of the project to be deleted. Cannot be null.", required = true)@RequestParam int id) {
		
		Optional<Project> optionalProject = projectService.getById(id);
		
		if(optionalProject.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.ProjectIdNotFound, "/project");
		}
		
		Project project = optionalProject.get();
		
		if(projectEmployeeService.getByProjectId(id).size() > 0) {
			return GlobalFunctions.createBadRequest(GlobalMessages.ProjectAssignedCannotDelete, "/project");
		}
		
		if(timeProjectService.getByProjectId(id).size() > 0) {
			return GlobalFunctions.createBadRequest(GlobalMessages.TimeProjectUsesProjectCannotDelete, "/project");
		}
		
		projectService.delete(project);
		return GlobalFunctions.createOkResponseFromObject(project);
	}
	
	@GetMapping("/assignationproject")
	@ApiOperation(value = "Returns all employees who are currently working on the project", notes = "404 if the project's identifier cannot be found.")
	public ResponseEntity<String> getAllAssignationsOnProject(@ApiParam(value = "Id of the project to get the assignations from. Cannot be null.", required = true)@RequestParam int id) {
		
		if(projectService.projectExists(id) == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.ProjectIdNotFound, "/assignationproject");
		}
		
		List<ProjectEmployee> projectEmployees = projectEmployeeService.getByProjectId(id);
		List<Employee> assignedEmployees = new ArrayList<Employee>();
		
		for (ProjectEmployee assignation : projectEmployees) {
			assignedEmployees.add(employeeService.getById(assignation.getEmployeeId()).get());
		}
		
		return GlobalFunctions.createOkResponseFromObject(assignedEmployees);
	}
	
	@PostMapping("/projectassignationadd")
	@ApiOperation(value = "Create assignations to all the employees the project is not already assigned to, in those sent in the body.", notes = "404 if the project's identifier or if any of the employees' identifier cannot be found.")
	public ResponseEntity<String> addEmployeeAssignations(@ApiParam(value = "List of employees")@RequestBody List<Employee> employees,
															@ApiParam(value = "Id of the project")@RequestParam int id) {
		
		if(projectService.projectExists(id) == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.ProjectIdNotFound, "/projectassignationadd");
		}
		
		for (Employee employee : employees) {
			
			if(employeeService.employeeExists(employee.getId()) == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, "/projectassginationadd");
			}
			
			ProjectEmployee projectEmployee = new ProjectEmployee();
			
			projectEmployee.setProjectId(id);
			projectEmployee.setEmployeeId(employee.getId());
			
			projectEmployeeService.save(projectEmployee);
		}
		
		return GlobalFunctions.createOkResponseFromObject(employees);
	}
	
	@GetMapping("/project/stats/employee")
	@ApiOperation(value = "Gets the amount of employees working on the project and their average salary.", notes = "404 if the project's identifier cannot be found.", response = ProjectStatsEmployee.class)
	public ResponseEntity<String> getProjectStats(@ApiParam(value = "Id of the project")@RequestParam(value = "id") int id) {
		
		if(projectService.projectExists(id) == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.ProjectIdNotFound, "/projectstatsemployee");
		}
		
		List<ProjectEmployee> assignations = projectEmployeeService.getByProjectId(id);
		
		List<Employee> employees = new ArrayList<Employee>();
		
		for (ProjectEmployee assignation : assignations) {
			employees.add(employeeService.getById(assignation.getEmployeeId()).get());
		}
		
		ProjectStatsEmployee projectStats = new ProjectStatsEmployee();

		projectStats.setName(projectService.getById(id).get().getName());
		projectStats.setNbEmployeeOnProject(employees.size());
		projectStats.setAverageSalary(employeeService.calculateAverageSalary(employees));
		
		return GlobalFunctions.createOkResponseFromObject(projectStats);
	}
}
