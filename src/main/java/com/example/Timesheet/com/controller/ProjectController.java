package com.example.Timesheet.com.controller;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
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
import com.example.Timesheet.com.Paths;
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
import com.example.Timesheet.com.service.TimesheetRowProjectService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "ProjectController")
public class ProjectController implements Serializable {
	
	private static final long serialVersionUID = 8440772259456508103L;
	private static final Logger log = LogManager.getLogger(ProjectController.class);

	@Autowired
	private ProjectService projectService = new ProjectService();
	
	@Autowired
	private ProjectMapper projectMapper = new ProjectMapper();
	
	@Autowired
	private ClientService clientService = new ClientService();
	
	@Autowired
	private EmployeeService employeeService = new EmployeeService();
	
	@Autowired
	private TimesheetRowProjectService timesheetRowProjectService;
	
	@Autowired
	private ProjectEmployeeService projectEmployeeService = new ProjectEmployeeService();
	
	@GetMapping(Paths.ProjectBasicPath)
	@ApiOperation(value = "Returns a list of all projects in the system.", 
					response = Project.class, responseContainer = "List")
	public List<Project> getAll() {
		log.debug("Entering getAll");
		return projectService.getAll();
	}
	
	@PostMapping(Paths.ProjectBasicPath)
	@ApiOperation(value = "Creates a new project in the system.",
					response = Project.class)
	public ResponseEntity<String> create(@ApiParam(value = "Project information for the new project to be created.", required = true)@RequestBody ProjectDto projectDto) {
		log.debug("Entering create");
		  
		if (projectDto.getClientId() != null) {
			if(clientService.getById(projectDto.getClientId()).isPresent() == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.ClientIdParameterNotFound, Paths.ProjectBasicPath);
			}
		}
		
		if (projectDto.getProjectManagerId() != null) {
			if(employeeService.getById(projectDto.getProjectManagerId()).isPresent() == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.ManagerIdNotFound, Paths.ProjectBasicPath);
			}
		}
		
		 
		Project project = projectMapper.dtoToProject(projectDto, 0);
		 
		project = projectService.save(project);
		
		if(project.getProjectManagerId() != null) {
			ProjectEmployee assignation = new ProjectEmployee();
			assignation.setEmployeeId(project.getProjectManagerId());
			assignation.setProjectId(project.getId());
			
			projectEmployeeService.save(assignation);
		}
		 
		return GlobalFunctions.createOkResponseFromObject(project);
	}
	
	@PutMapping(Paths.ProjectBasicPath)
	@ApiOperation(value = "Updates a project in the system by their identifier.", notes = "404 if any of the project's identifier specified in the address, project manager id or client id specified in the body is not found.",
					response = Project.class)
	public ResponseEntity<String> edit(@ApiParam("Project information to be modified. There is no need to keep values that will not be modified.")@RequestBody ProjectDto projectDto, 
										@ApiParam(value = "Id of the project to be modified. Cannot be null.", required = true)@RequestParam int id) {
		log.debug("Entering edit with id parameter of " + id);
		
		if(projectService.getById(id).isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.ProjectIdNotFound, Paths.ProjectBasicPath);
		}
		
		if (projectDto.getProjectManagerId() != null) {
			if(employeeService.getById(projectDto.getProjectManagerId()).isPresent() == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.ManagerIdNotFound, Paths.ProjectBasicPath);
			}
			
			projectEmployeeService.save(new ProjectEmployee(id, projectDto.getProjectManagerId()));
		}
		  
		if (projectDto.getClientId() != null) {
			if(clientService.getById(projectDto.getClientId()).isPresent() == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.RoleIdNotFound, Paths.ProjectBasicPath);
			}
  	 	}
	 
		try {
			Project project = projectMapper.dtoToProject(projectDto, id);
			 
			project = projectService.save(project);
			 
			return GlobalFunctions.createOkResponseFromObject(project);
		}
		catch (OptimisticLockException e) {
			return GlobalFunctions.createConflictResponse(GlobalMessages.ProjectNotUpToDate, Paths.ProjectBasicPath);
		}
	}
	
	@DeleteMapping(Paths.ProjectBasicPath)
	@ApiOperation(value = "Deletes a project in the system by their identifier.", notes = "404 if the project's identifier cannot be found.<br> 409 if the project is still assigned to some employees or is still referenced by a timesheetRowProject.",
					response = Project.class)
	public ResponseEntity<String> delete(@ApiParam(value = "Id of the project to be deleted. Cannot be null.", required = true)@RequestParam int id) {
		log.debug("Entering delete with id parameter of " + id);
		
		Optional<Project> optionalProject = projectService.getById(id);
		
		if(optionalProject.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.ProjectIdNotFound, Paths.ProjectBasicPath);
		}
		
		Project project = optionalProject.get();
		
		if(projectEmployeeService.getByProjectId(id).size() > 0) {
			return GlobalFunctions.createConflictResponse(GlobalMessages.ProjectAssignedCannotDelete, Paths.ProjectBasicPath);
		}
		
		if(timesheetRowProjectService.getByProjectId(id).size() > 0) {
			return GlobalFunctions.createConflictResponse(GlobalMessages.TimeProjectUsesProjectCannotDelete, Paths.ProjectBasicPath);
		}
		
		projectService.delete(project);
		return GlobalFunctions.createOkResponseFromObject(project);
	}
	
	@GetMapping(Paths.ProjectAssignations)
	@ApiOperation(value = "Returns all employees who are currently working on the project", notes = "404 if the project's identifier cannot be found.",
					response = Employee.class, responseContainer = "List")
	public ResponseEntity<String> getAllAssignationsOnProject(@ApiParam(value = "Id of the project to get the assignations from. Cannot be null.", required = true)@RequestParam int id) {
		log.debug("Entering getOne with id parameter of " + id);
		
		if(projectService.projectExists(id) == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.ProjectIdNotFound, Paths.ProjectAssignations);
		}
		
		List<ProjectEmployee> projectEmployees = projectEmployeeService.getByProjectId(id);
		List<Employee> assignedEmployees = new ArrayList<Employee>();
		
		for (ProjectEmployee assignation : projectEmployees) {
			assignedEmployees.add(employeeService.getById(assignation.getEmployeeId()).get());
		}
		
		return GlobalFunctions.createOkResponseFromObject(assignedEmployees);
	}
	
	@PostMapping(Paths.ProjectAssignations)
	@ApiOperation(value = "Create assignations to all the employees the project is not already assigned to, in those sent in the body.", notes = "404 if the project's identifier or if any of the employees' identifier cannot be found.",
					response = Employee.class, responseContainer = "List")
	public ResponseEntity<String> addEmployeeAssignations(@ApiParam(value = "List of employees")@RequestBody List<Employee> employees,
															@ApiParam(value = "Id of the project")@RequestParam int id) {
		log.debug("Entering addEmployeeAssignations with id parameter of " + id);
		
		if(projectService.projectExists(id) == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.ProjectIdNotFound, Paths.ProjectAssignations);
		}
		
		for (Employee employee : employees) {
			
			if(employeeService.employeeExists(employee.getId()) == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, Paths.ProjectAssignations);
			}
			
			ProjectEmployee projectEmployee = new ProjectEmployee();
			
			projectEmployee.setProjectId(id);
			projectEmployee.setEmployeeId(employee.getId());
			
			projectEmployeeService.save(projectEmployee);
		}
		
		List<ProjectEmployee> projectEmployees = projectEmployeeService.getByProjectId(id);
		
		for (ProjectEmployee assignation : projectEmployees) {
			employees.add(employeeService.getById(assignation.getEmployeeId()).get());
		}
		
		return GlobalFunctions.createOkResponseFromObject(employees);
	}
	
	@GetMapping(Paths.ProjectGetStatsEmployee)
	@ApiOperation(value = "Gets the amount of employees working on the project and their average salary.", notes = "404 if the project's identifier cannot be found.", 
					response = ProjectStatsEmployee.class)
	public ResponseEntity<String> getProjectStats(@ApiParam(value = "Id of the project")@RequestParam(value = "id") int id) {
		log.debug("Entering getProjectStats with id parameter of " + id);
		
		if(projectService.projectExists(id) == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.ProjectIdNotFound, Paths.ProjectGetStatsEmployee);
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
	
	@GetMapping(Paths.ProjectGetStatsTimePerDay)
	@ApiOperation(value = "Returns the average amount of hours worked on a project per day in the X weeks before the given date.", notes = "404 if project id cannot be found, 400 if the project was not started by the end of the first week, or if the project ended before the beginning of the last week",
					response = Float.class)
	public ResponseEntity<?> getAverageTimeWorked(@ApiParam(value = "Id of the project to calculate the averages for.", required = true)@RequestParam(value="id") int id, 
													@ApiParam(value = "Date to end the calculation", required = true) @RequestParam(value = "date") Date date, 
													@ApiParam(value = "Amount of weeks to go back from the sent date.", required = true) @RequestParam(value="weeks") int weeks) {
		Assert.notNull(date, "parameter date must not be null");
		log.debug("Entering getAverageTimeWorked with id parameter of " + id + ", date parameter of " + date + " and weeks parameter of " + weeks);
		
		if(weeks < 1) {
			return GlobalFunctions.createBadRequest(GlobalMessages.ProjectAveragInvalidWeekNumber, Paths.ProjectGetStatsTimePerDay);
		}
		
		Optional<Project> optionalProject = projectService.getById(id);
		
		if(optionalProject.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.ProjectIdNotFound, Paths.ProjectGetStatsTimePerDay);
		}
		
		Project project = optionalProject.get();
		
		Date latestSunday = GlobalFunctions.findLatestSunday(date);
		Date firstSunday = new Date(0L);
		Date firstSaturday = new Date(0L);
		
		firstSunday.setTime(latestSunday.getTime() - 7 * (weeks - 1) * GlobalVars.MillisecondsPerDay);
		firstSaturday.setTime(firstSunday.getTime() + 6 * GlobalVars.MillisecondsPerDay);
		
		if(project.getStartDate().getTime() > firstSaturday.getTime()) {
			return GlobalFunctions.createBadRequest(GlobalMessages.AverageDateParameterTooEarly, Paths.ProjectGetStatsTimePerDay);
		}
		
		if(project.getEndDate().getTime() < latestSunday.getTime()) {
			return GlobalFunctions.createBadRequest(GlobalMessages.AverageDateParameterTooLate, Paths.ProjectGetStatsTimePerDay);
		}
		
		Float averageHoursPerDay = timesheetRowProjectService.calculateAverageTimeWorked(id, firstSunday, weeks);
		
		return GlobalFunctions.createOkResponseFromObject(averageHoursPerDay);
	}
}
