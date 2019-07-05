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
import com.example.Timesheet.com.dto.RoleDto;
import com.example.Timesheet.com.mapper.RoleMapper;
import com.example.Timesheet.com.model.Employee;
import com.example.Timesheet.com.model.Role;
import com.example.Timesheet.com.service.EmployeeService;
import com.example.Timesheet.com.service.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "RoleController")
public class RoleController implements Serializable {
	
	private static final long serialVersionUID = -4628711753006059091L;
	private static final Logger log = LogManager.getLogger(RoleController.class);

	@Autowired
	RoleService roleService = new RoleService();
	
	@Autowired
	RoleMapper roleMapper = new RoleMapper();

	@Autowired
	EmployeeService employeeService = new EmployeeService();
	
	@GetMapping("/role")
	@ApiOperation(value = "Returns a list of all roles in the system", 
					response = Role.class, responseContainer = "List")
	public List<Role> getAll(){
		log.debug("Entering getAll");
		return roleService.getAll();
	}
	
	@GetMapping("/role/one")
	@ApiOperation(value = "Returns the role with the specified identifier.", notes = "404 if the role's identifier cannot be found.", 
					response = Role.class)
	public ResponseEntity<?> getOne(@ApiParam(value = "Id of the role to be found.", required = true) @RequestParam(value="id") int id) {
		log.debug("Entering getOne with id parameter of " + id);
		
		Optional<Role> optionalRole = roleService.getById(id);
		
		if(optionalRole.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.RoleIdNotFound, "/role/one");
		}
		
		return GlobalFunctions.createOkResponseFromObject(optionalRole.get());
	}
	
	@PostMapping("/role")
	@ApiOperation(value = "Creates a new role in the system.", 
					response = Role.class)
	public ResponseEntity<String> create(@ApiParam(value = "Role information for the new status to be created.", required = true)@RequestBody RoleDto roleDto) {
		log.debug("Entering create");
		
		Role role = this.roleMapper.dtoToRole(roleDto, 0);
		
		role = roleService.save(role);
		
		return GlobalFunctions.createOkResponseFromObject(role);
	}

	@PutMapping("/role")
	@ApiOperation(value = "Updates a role in the system by their identifier.", notes = "404 if the role's identifier cannot be found.<br> 400 if name is null or empty",
					response = Role.class)
	public ResponseEntity<String> edit(@ApiParam("Role information to be modified.")@RequestBody RoleDto roleDto,
											@ApiParam(value = "Id of the role to be modified. Cannot be null.", required = true) @RequestParam int id){
		log.debug("Entering edit with id parameter of " + id);
		
		if(this.roleService.getById(id).isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.RoleIdNotFound, "/role");
		}
		
		try {
			Role role = roleMapper.dtoToRole(roleDto, id);
			role = roleService.save(role);
			
			return GlobalFunctions.createOkResponseFromObject(role);
		}
		catch (OptimisticLockException e) {
			return GlobalFunctions.createConflictResponse(GlobalMessages.RoleNotUpToDate, Paths.RoleBasicPath);
		}				
	}

	@DeleteMapping("/role")
	@ApiOperation(value = "Deletes a role in the system by their identifier.", notes = "404 if the role's identifier cannot be found.",
					response = Role.class)
	public ResponseEntity<String> delete(@ApiParam(value = "Id of the role to be deleted.", required = true) @RequestParam(value="id") int id) {
		log.debug("Entering delete with id parameter of " + id);
		
		Optional<Role> optionalRole = this.roleService.getById(id);
		
		if(optionalRole.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.RoleIdNotFound, "/role");
		}
		
		Role role = optionalRole.get();
		
		if(this.employeeService.getByRoleId(id).size() > 0) {
			return GlobalFunctions.createBadRequest(GlobalMessages.EmployeeUsesRoleCannotDelete, "/role");
		}
		
		this.roleService.delete(role);

		return GlobalFunctions.createOkResponseFromObject(role);
		
	}

	@GetMapping("/role/employee")
	@ApiOperation(value = "Returns a list of all employees in the system that have the role", notes = "404 if the role's identifier cannot be found.",
					response = Employee.class, responseContainer = "List")
	public ResponseEntity<String> getEmployeeRole(@ApiParam(value = "Id of the role to get the employees for.", required = true) @RequestParam(value="id") int id) {
		log.debug("Entering getEmployeeRole with id parameter of " + id);
		
		if(roleService.roleExists(id) == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.RoleIdNotFound, "/role/employee");
		}
		
		List<Employee> employees = employeeService.getByRoleId(id);
		
		return GlobalFunctions.createOkResponseFromObject(employees);
	}
	
}
