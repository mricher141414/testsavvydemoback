package com.example.Timesheet.com.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Timesheet.com.dto.RoleDTO;
import com.example.Timesheet.com.mapper.RoleMapper;
import com.example.Timesheet.com.model.Role;
import com.example.Timesheet.com.service.RoleService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RoleController {
	@Autowired
	RoleService roleService = new RoleService();

	RoleMapper roleMapper = new RoleMapper();

	@GetMapping("/roles")
	public List<Role> findAllRoles(){

		return roleService.getRoles();

	}

	@PutMapping("/roles")
	public void saveRole(@RequestBody RoleDTO roleDto){

		if(!roleDto.getName().equals("")) {
			Role role = roleMapper.DTOtoRole(roleDto);
			roleService.saveRole(role);
		}else {
			throw new NullPointerException("name is null"); 
		}



	}

	@DeleteMapping("/roles")
	public void deleteRole(@RequestBody RoleDTO roleDto){

		if(!roleDto.getName().equals("")) {
			Role role = roleMapper.DTOtoRole(roleDto);
			roleService.deleteRole(role);

		}else {
			throw new NullPointerException("name is null"); 
		}


	}

}
