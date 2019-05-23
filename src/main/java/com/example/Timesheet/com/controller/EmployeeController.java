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
	 
	 @GetMapping("/employee")
	 @ApiOperation("Returns a list of all employees in the system.")
	 public List<Employee> getAll() throws SQLException {		
		 return employeeService.getAll();
	 }
	 
	 @PostMapping("/employee")
	 @ApiOperation("Creates a new employee in the system.")
	 public ResponseEntity<String> create(@ApiParam(value = "Employee information for the new employee to be created.", required = true) @RequestBody EmployeeDto employeeDto) {
		 
		 if (employeeDto.getDepartmentId() != null) {
			  if(departementService.getById(employeeDto.getDepartmentId()).isPresent() == false) {
				  return GlobalFunctions.createNotFoundResponse(GlobalMessages.DepartementIdNotFound, "/employee");
			  }
		  }
		  
		  if (employeeDto.getManagerId() != null) {
			  if(employeeService.getById(employeeDto.getManagerId()).isPresent() == false) {
				  return GlobalFunctions.createNotFoundResponse(GlobalMessages.ManagerIdNotFound, "/employee");
			  }
		  }
		  
		  if (employeeDto.getRoleId() != null) {
			  if(roleService.getById(employeeDto.getRoleId()).isPresent() == false) {
				  return GlobalFunctions.createNotFoundResponse(GlobalMessages.RoleIdNotFound, "/employee");
				  }
			  }
		 
		 Employee employee = employeeMapper.dtoToEmployee(employeeDto, 0);
		 
		 employeeService.saveEmployee(employee);
		 
		 return GlobalFunctions.createOkResponseFromObject(employee);
	 }
	 
	 @PutMapping("/employee")
	 @ApiOperation(value = "Updates an employee in the system by their identifier.", notes = "404 if any of the employee's identifier specified in the address, department id, role id or manager id specified in the body is not found")
	 public ResponseEntity<String> edit(@ApiParam("Employee information to be modified. There is no need to keep values that will not be modified.")@RequestBody EmployeeDto employeeDto,
	  											@ApiParam(value = "Id of the employee to be modified. Cannot be null.", required = true)@RequestParam(value="id") int id) throws SQLException {

		  if (employeeService.getById(id).isPresent() == false) {
			  return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, "/employee");
		  }
		  
		  if (employeeDto.getDepartmentId() != null) {
			  if(departementService.getById(employeeDto.getDepartmentId()).isPresent() == false) {
				  return GlobalFunctions.createNotFoundResponse(GlobalMessages.DepartementIdNotFound, "/employee");
			  }
		  }
		  
		  if (employeeDto.getManagerId() != null) {
			  if(employeeService.getById(employeeDto.getManagerId()).isPresent() == false) {
				  return GlobalFunctions.createNotFoundResponse(GlobalMessages.ManagerIdNotFound, "/employee");
			  }
		  }
		  
		  if (employeeDto.getRoleId() != null) {
			  if(roleService.getById(employeeDto.getRoleId()).isPresent() == false) {
				  return GlobalFunctions.createNotFoundResponse(GlobalMessages.RoleIdNotFound, "/employee");
			  }
		  }
			  
		  Employee employee = employeeMapper.dtoToEmployee(employeeDto, id);
		  employeeService.saveEmployee(employee);
		
		  return GlobalFunctions.createOkResponseFromObject(employee);
	 }
	 
	 @DeleteMapping("/employee")
	 @ApiOperation(value = "Deletes an employee in the system by their identifier.", notes = "404 if the employee's identifier cannot be found.<br> 400 if the employee is still referenced by a timesheet, project, another employee or is still assigned to a project.")
	 public ResponseEntity<String> delete(@ApiParam(value = "Id of the employee to be deleted. Cannot be null", required = true) @RequestParam int id) {
		 
		 Optional<Employee> optionalEmployee = this.employeeService.getById(id);
		 
		 if(optionalEmployee.isPresent() == false) {
			 return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, "/employee");
		 }
		 
		 Employee employee = optionalEmployee.get();
		 
		 if(projectEmployeeService.getByEmployeeId(id).size() > 0) {
			 return GlobalFunctions.createBadRequest(GlobalMessages.EmployeeAssignedCannotDelete, "/employee");
		 }
		 
		 if(this.employeeService.getAllByManagerId(id).size() > 0) {
			 return GlobalFunctions.createBadRequest(GlobalMessages.EmployeeUsesManagerCannotDelete, "/employee");
		 }
		 
		 if(this.timesheetService.getTimesheetByEmployeeId(id).size() > 0) {
			 return GlobalFunctions.createBadRequest(GlobalMessages.TimesheetUsesEmployeeCannotDelete, "/employee");
		 }
		 
		 if(projectService.getByProjectManagerId(id).size() > 0) {
			 return GlobalFunctions.createBadRequest(GlobalMessages.ProjectUsesEmployeeCannotDelete, "/employee");
		 }
		 
		 this.employeeService.delete(employee);
		 return GlobalFunctions.createOkResponseFromObject(employee);
	 }

	@GetMapping("/assignationemployee")
	@ApiOperation(value = "Returns all projects who are currently assigned to the employee", notes = "404 if the employee's identifier cannot be found.")
	public ResponseEntity<String> getAllAssignationsOnEmployee(@ApiParam(value = "Id of the employee to get the assignations from. Cannot be null.", required = true)@RequestParam int id) {
		
		if(employeeService.employeeExists(id) == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, "/assignationemployee");
		}
		
		List<ProjectEmployee> projectEmployees = projectEmployeeService.getByEmployeeId(id);
		List<Project> assignedProjects = new ArrayList<Project>();
		
		for (ProjectEmployee assignation : projectEmployees) {
			assignedProjects.add(projectService.getById(assignation.getProjectId()).get());
		}
		
		return GlobalFunctions.createOkResponseFromObject(assignedProjects);
	}
}
