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

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RoleController {
	@Autowired
	RoleService roleService = new RoleService();
	RoleMapper roleMapper = new RoleMapper();

	@Autowired
	PersonService personService = new PersonService();
	
	@GetMapping("/roles")
	public List<Role> findAllRoles(){

		return roleService.getRoles();

	}

	@PutMapping("/roles")
	public ResponseEntity<String> saveRole(@RequestBody RoleDTO roleDto, int id){

		if(this.roleService.getById(id).isPresent()) {
		
			if(!roleDto.getName().equals("")) {
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
	public ResponseEntity<String> deleteRole(@RequestParam(value="id") int id) {
		
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
