package com.example.Timesheet.com.controller;



import java.util.List;
import java.util.Optional;
import com.example.Timesheet.com.GlobalVars;

import org.springframework.beans.factory.annotation.Autowired;
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
	public void saveRole(@RequestBody RoleDTO roleDto, int id){

		if(!roleDto.getName().equals("")) {
			Role role = roleMapper.DTOtoRole(roleDto, id);
			roleService.saveRole(role);
		}else {
			throw new NullPointerException("name is null"); 
		}



	}

	@DeleteMapping("/roles")
	public String deleteRole(@RequestParam(value="id") int id) {
		
		Optional<Role> optionalRole = this.roleService.getById(id);
		
		if(optionalRole.isPresent() == false) {
			return GlobalVars.RoleIdNotFound;
		}
		
		Role role = optionalRole.get();
		
		if(this.personService.findAllByRoleId(id).size() > 0) {
			return GlobalVars.EmployeeUsesRoleCannotDelete;
		}
		
		this.roleService.deleteRole(role);

		return GlobalVars.RoleDeleteSuccessful;
	}

}
