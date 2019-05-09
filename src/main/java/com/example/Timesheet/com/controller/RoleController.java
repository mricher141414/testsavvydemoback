package com.example.Timesheet.com.controller;



import java.util.List;
import java.util.Optional;

import com.example.Timesheet.com.GlobalFunctions;
import com.example.Timesheet.com.GlobalVars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Timesheet.com.dto.RoleDTO;
import com.example.Timesheet.com.mapper.RoleMapper;
import com.example.Timesheet.com.model.Role;
import com.example.Timesheet.com.service.PersonService;
import com.example.Timesheet.com.service.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "RoleController")
public class RoleController {
	
	@Autowired
	RoleService roleService = new RoleService();
	
	@Autowired
	RoleMapper roleMapper = new RoleMapper();

	@Autowired
	PersonService personService = new PersonService();
	
	@GetMapping("/roles")
	@ApiOperation("Returns a list of all roles in the system")
	public List<Role> findAllRoles(){

		return roleService.getRoles();

	}

	@PutMapping("/roles")
	@ApiOperation(value = "Updates a role in the system by their identifier.", notes = "404 if the role's identifier cannot be found. \n"
																						+ "400 if name is null or empty")
	public ResponseEntity<String> saveRole(@ApiParam("Role information to be modified.")@RequestBody RoleDTO roleDto,
											@ApiParam(value = "Id of the role to be modified. Cannot be null.", required = true) @RequestParam int id){

		if(this.roleService.getById(id).isPresent()) {
		
			if(roleDto.getName() != null || !roleDto.getName().equals("")) {
				Role role = roleMapper.DTOtoRole(roleDto, id);
				roleService.saveRole(role);
				
				return new ResponseEntity<String>(GlobalVars.RolePutSuccessful, HttpStatus.OK);
			}else {
				return GlobalFunctions.createBadRequest(GlobalVars.NameIsEmpty, "/roles");
			}
		}
		else {
			return GlobalFunctions.createNotFoundResponse(GlobalVars.RoleIdNotFound, "/roles");
		}
	}

	@DeleteMapping("/roles")
	@ApiOperation(value = "Deletes a role in the system by their identifier.", notes = "404 if the role's identifier cannot be found.")
	public ResponseEntity<String> deleteRole(@ApiParam(value = "Id of the role to be deleted.", required = true) @RequestParam(value="id") int id) {
		
		Optional<Role> optionalRole = this.roleService.getById(id);
		
		if(optionalRole.isPresent()) {
			Role role = optionalRole.get();
			
			if(this.personService.findAllByRoleId(id).size() > 0) {
				return GlobalFunctions.createBadRequest(GlobalVars.EmployeeUsesRoleCannotDelete, "/roles");
			}
			
			this.roleService.deleteRole(role);
	
			return new ResponseEntity<String>(GlobalVars.RoleDeleteSuccessful, HttpStatus.OK);
		}
		else {
			return GlobalFunctions.createNotFoundResponse(GlobalVars.RoleIdNotFound, "/roles");
		}
	}

}
