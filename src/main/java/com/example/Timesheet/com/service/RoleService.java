package com.example.Timesheet.com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Timesheet.com.dao.IRoleDao;
import com.example.Timesheet.com.model.Role;

@Service

public class RoleService {

	@Autowired
	private IRoleDao roleDao;


	public List<Role> getAll() {
		return (List<Role>) this.roleDao.findAll();
	}

	public void save(Role role) {
		if(roleDao.existsById(role.getId()) == false) {
			role.setVersion(0);
		}
		
		this.roleDao.save(role);

	}

	public void delete(Role role) {
		this.roleDao.delete(role);
	}
	
	
	public Optional<Role> getById(int id) {
		return this.roleDao.findById(id);
	}
	
	public boolean roleExists(int id) {
		return roleDao.existsById(id);
	}
}
