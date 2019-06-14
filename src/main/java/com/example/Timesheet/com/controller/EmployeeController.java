package com.example.Timesheet.com.controller;

import java.sql.SQLException;
import java.util.ArrayList;
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
import com.example.Timesheet.com.Paths;
import com.example.Timesheet.com.dto.EmployeeDto;
import com.example.Timesheet.com.mapper.EmployeeMapper;
import com.example.Timesheet.com.model.Employee;
import com.example.Timesheet.com.model.Project;
import com.example.Timesheet.com.model.ProjectEmployee;
import com.example.Timesheet.com.service.EmployeeService;
import com.example.Timesheet.com.service.ProjectEmployeeService;
import com.example.Timesheet.com.service.ProjectService;
import com.example.Timesheet.com.service.RoleService;
import com.example.Timesheet.com.service.TimesheetService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.example.Timesheet.com.service.DepartementService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "EmployeeController")
public class EmployeeController {
	
	 @Autowired
	 private EmployeeService employeeService = new EmployeeService();
	 
	 @Autowired
	 private DepartementService departementService = new DepartementService();
	 
	 @Autowired
	 private RoleService roleService = new RoleService();
	 
	 @Autowired
	 private EmployeeMapper employeeMapper = new EmployeeMapper();
	 
	 @Autowired
	 private TimesheetService timesheetService = new TimesheetService();
	 
	 @Autowired
	 private ProjectService projectService = new ProjectService();
	 
	 @Autowired
	 private ProjectEmployeeService projectEmployeeService = new ProjectEmployeeService();
	 
	 @GetMapping(Paths.EmployeeBasicPath)
	 @ApiOperation("Returns a list of all employees in the system.")
	 public List<Employee> getAll() throws SQLException {		
		 return employeeService.getAll();
	 }
	 
	 @GetMapping(Paths.EmployeeGetOne)
	 @ApiOperation(value = "Returns a single employee by their identifier.", notes = "404 if the employee's identifier cannot be found.")
	 public ResponseEntity<String> getOne(@ApiParam(value = "Id of the employee to retrieve", required = true)@RequestParam(value = "id") int id) {
		 
		 Optional<Employee> optionalEmployee = employeeService.getById(id);
		 
		 if(optionalEmployee.isPresent() == false) {
			 return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, Paths.EmployeeGetOne);
		 }
		 
		 return GlobalFunctions.createOkResponseFromObject(optionalEmployee.get());
	 }
	 
	 @PostMapping(Paths.EmployeeBasicPath)
	 @ApiOperation("Creates a new employee in the system.")
	 public ResponseEntity<String> create(@ApiParam(value = "Employee information for the new employee to be created.", required = true) @RequestBody EmployeeDto employeeDto) {
		 
		 if (employeeDto.getDepartmentId() != null) {
			  if(departementService.getById(employeeDto.getDepartmentId()).isPresent() == false) {
				  return GlobalFunctions.createNotFoundResponse(GlobalMessages.DepartmentIdNotFound, Paths.EmployeeBasicPath);
			  }
		  }
		  
		  if (employeeDto.getManagerId() != null) {
			  if(employeeService.getById(employeeDto.getManagerId()).isPresent() == false) {
				  return GlobalFunctions.createNotFoundResponse(GlobalMessages.ManagerIdNotFound, Paths.EmployeeBasicPath);
			  }
		  }
		  
		  if (employeeDto.getRoleId() != null) {
			  if(roleService.getById(employeeDto.getRoleId()).isPresent() == false) {
				  return GlobalFunctions.createNotFoundResponse(GlobalMessages.RoleIdNotFound, Paths.EmployeeBasicPath);
				  }
			  }
		 
		 Employee employee = employeeMapper.dtoToEmployee(employeeDto, 0);
		 
		 employee = employeeService.saveEmployee(employee);
		 
		 return GlobalFunctions.createOkResponseFromObject(employee);
	 }
	 
	 @PutMapping(Paths.EmployeeBasicPath)
	 @ApiOperation(value = "Updates an employee in the system by their identifier.", notes = "404 if any of the employee's identifier specified in the address, department id, role id or manager id specified in the body is not found")
	 public ResponseEntity<String> edit(@ApiParam("Employee information to be modified. There is no need to keep values that will not be modified.")@RequestBody EmployeeDto employeeDto,
	  											@ApiParam(value = "Id of the employee to be modified. Cannot be null.", required = true)@RequestParam(value="id") int id) throws SQLException {

		  if (employeeService.getById(id).isPresent() == false) {
			  return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, Paths.EmployeeBasicPath);
		  }
		  
		  if (employeeDto.getDepartmentId() != null) {
			  if(departementService.getById(employeeDto.getDepartmentId()).isPresent() == false) {
				  return GlobalFunctions.createNotFoundResponse(GlobalMessages.DepartmentIdNotFound, Paths.EmployeeBasicPath);
			  }
		  }
		  
		  if (employeeDto.getManagerId() != null) {
			  if(employeeService.getById(employeeDto.getManagerId()).isPresent() == false) {
				  return GlobalFunctions.createNotFoundResponse(GlobalMessages.ManagerIdNotFound, Paths.EmployeeBasicPath);
			  }
		  }
		  
		  if (employeeDto.getRoleId() != null) {
			  if(roleService.getById(employeeDto.getRoleId()).isPresent() == false) {
				  return GlobalFunctions.createNotFoundResponse(GlobalMessages.RoleIdNotFound, Paths.EmployeeBasicPath);
			  }
		  }
			  
		  Employee employee = employeeMapper.dtoToEmployee(employeeDto, id);
		  employeeService.saveEmployee(employee);
		
		  return GlobalFunctions.createOkResponseFromObject(employee);
	 }
	 
	 @DeleteMapping(Paths.EmployeeBasicPath)
	 @ApiOperation(value = "Deletes an employee in the system by their identifier.", notes = "404 if the employee's identifier cannot be found.<br> 400 if the employee is still referenced by a timesheet, project, another employee or is still assigned to a project.")
	 public ResponseEntity<String> delete(@ApiParam(value = "Id of the employee to be deleted. Cannot be null", required = true) @RequestParam int id) {
		 
		 Optional<Employee> optionalEmployee = this.employeeService.getById(id);
		 
		 if(optionalEmployee.isPresent() == false) {
			 return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, Paths.EmployeeBasicPath);
		 }
		 
		 Employee employee = optionalEmployee.get();
		 
		 if(projectEmployeeService.getByEmployeeId(id).size() > 0) {
			 return GlobalFunctions.createBadRequest(GlobalMessages.EmployeeAssignedCannotDelete, Paths.EmployeeBasicPath);
		 }
		 
		 if(this.employeeService.getAllByManagerId(id).size() > 0) {
			 return GlobalFunctions.createBadRequest(GlobalMessages.EmployeeUsesManagerCannotDelete, Paths.EmployeeBasicPath);
		 }
		 
		 if(this.timesheetService.getTimesheetByEmployeeId(id).size() > 0) {
			 return GlobalFunctions.createBadRequest(GlobalMessages.TimesheetUsesEmployeeCannotDelete, Paths.EmployeeBasicPath);
		 }
		 
		 if(projectService.getByProjectManagerId(id).size() > 0) {
			 return GlobalFunctions.createBadRequest(GlobalMessages.ProjectUsesEmployeeCannotDelete, Paths.EmployeeBasicPath);
		 }
		 
		 this.employeeService.delete(employee);
		 return GlobalFunctions.createOkResponseFromObject(employee);
	 }

	@GetMapping(Paths.EmployeeAssignations)
	@ApiOperation(value = "Returns all projects who are currently assigned to the employee", notes = "404 if the employee's identifier cannot be found.")
	public ResponseEntity<String> getAllAssignationsOnEmployee(@ApiParam(value = "Id of the employee to get the assignations from. Cannot be null.", required = true)@RequestParam int id) {
		
		if(employeeService.employeeExists(id) == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, Paths.EmployeeAssignations);
		}
		
		List<ProjectEmployee> projectEmployees = projectEmployeeService.getByEmployeeId(id);
		List<Project> assignedProjects = new ArrayList<Project>();
		
		for (ProjectEmployee assignation : projectEmployees) {
			assignedProjects.add(projectService.getById(assignation.getProjectId()).get());
		}
		
		return GlobalFunctions.createOkResponseFromObject(assignedProjects);
	}
	
	@PostMapping(Paths.EmployeeAssignations)
	@ApiOperation(value = "Create assignations to all the projects the employee is not already a part of, in those sent in the body.", notes = "404 if the employee's identifier or if any of the projects' identifier cannot be found.")
	public ResponseEntity<String> addEmployeeAssignations(@ApiParam(value = "List of Projects")@RequestBody List<Project> projects,
															@ApiParam(value = "Id of the employee")@RequestParam int id) {
		
		if(employeeService.employeeExists(id) == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, Paths.EmployeeAssignations);
		}
		
		for (Project project : projects) {
			
			if(projectService.projectExists(project.getId()) == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.ProjectIdNotFound, Paths.EmployeeAssignations);
			}
			
			ProjectEmployee projectEmployee = new ProjectEmployee();
			
			projectEmployee.setEmployeeId(id);
			projectEmployee.setProjectId(project.getId());
			
			projectEmployeeService.save(projectEmployee);
		}
		
		List<ProjectEmployee> projectEmployees = projectEmployeeService.getByEmployeeId(id);
		
		for (ProjectEmployee assignation : projectEmployees) {
			projects.add(projectService.getById(assignation.getProjectId()).get());
		}
		
		return GlobalFunctions.createOkResponseFromObject(projects);
	}
	
	 @GetMapping(Paths.EmployeeGetManaged)
	 @ApiOperation(value = "Returns a list of all employees managed by this employee.", notes = "404 if the employee's identifier cannot be found.")
	 public ResponseEntity<String> getManagedEmployees(@ApiParam(value = "Id of the employee that manages the others", required = true)@RequestParam(value = "id") int id) {
		 
		 if(employeeService.employeeExists(id) == false) {
			 return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, Paths.EmployeeGetManaged);
		 }
		 
		 List<Employee> employeesManaged = employeeService.getAllByManagerId(id);
		 
		 return GlobalFunctions.createOkResponseFromObject(employeesManaged);
	 }
}
