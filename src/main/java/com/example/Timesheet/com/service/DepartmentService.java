package com.example.Timesheet.com.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.Timesheet.com.dao.IDepartmentDao;
import com.example.Timesheet.com.model.Department;

@Service
public class DepartmentService implements Serializable {

	private static final long serialVersionUID = 8752076469016491661L;
	private static final Logger log = LogManager.getLogger(DepartmentService.class);
	
	@Autowired
	private IDepartmentDao departmentDao;
	
	public List<Department> getAll(){
		log.debug("Entering getAll");
		return (List<Department>) this.departmentDao.findAll();
	}
	
	public Department save(Department department) {
		Assert.notNull(department, "Parameter department must not be null");
		log.debug("Entering save");
		
		if(departmentDao.existsById(department.getId()) == false) {
			department.setVersion(0);
		}
		
		return departmentDao.save(department);	
	}
	
	public void delete(Department department) {
		Assert.notNull(department, "Parameter department must not be null");
		log.debug("Entering delete");
		
		this.departmentDao.delete(department);	
	}
	
	public Optional<Department> getById(int id) {
		log.debug("Entering getById with id parameter of " + id);
		
		return this.departmentDao.findById(id);
	}

}
