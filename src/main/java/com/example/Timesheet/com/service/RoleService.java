package com.example.Timesheet.com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Timesheet.com.dao.IRoleDAO;
import com.example.Timesheet.com.model.Role;

@Service

public class RoleService {

	@Autowired
	private IRoleDAO role;


	public List<Role> getAll() {
		return (List<Role>) this.role.findAll();
	}

	public void save(Role role) {
		this.role.save(role);

	}

	public void delete(Role role) {
		this.role.delete(role);
	}
	
	
	public Optional<Role> getById(int id) {
		return this.role.findById(id);
	}
}
