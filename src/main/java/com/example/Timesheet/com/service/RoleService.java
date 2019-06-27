package com.example.Timesheet.com.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.Timesheet.com.dao.IRoleDao;
import com.example.Timesheet.com.model.Role;

@Service

public class RoleService implements Serializable {

	private static final long serialVersionUID = 2975494638153589286L;
	private static final Logger log = LogManager.getLogger(RoleService.class);
	
	@Autowired
	private IRoleDao roleDao;


	public List<Role> getAll() {
		log.debug("Entering getAll");
		return (List<Role>) this.roleDao.findAll();
	}

	public Role save(Role role) {
		Assert.notNull(role, "Parameter role must not be null");
		log.debug("Entering save");
		
		if(roleDao.existsById(role.getId()) == false) {
			role.setVersion(0);
		}
		
		return roleDao.save(role);

	}

	public void delete(Role role) {
		Assert.notNull(role, "Parameter client must not be null");
		log.debug("Entering delete");
		
		this.roleDao.delete(role);
	}
	
	
	public Optional<Role> getById(int id) {
		log.debug("Entering getById with id parameter of " + id);
		return this.roleDao.findById(id);
	}
	
	public boolean roleExists(int id) {
		log.debug("Entering roleExists with id parameter of " + id);
		return roleDao.existsById(id);
	}
}
