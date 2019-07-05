package com.example.Timesheet.com.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.Timesheet.com.dao.IProjectEmployeeDao;
import com.example.Timesheet.com.model.ProjectEmployee;

@Service
public class ProjectEmployeeService implements Serializable {

	private static final long serialVersionUID = 7769905313870844832L;
	private static final Logger log = LogManager.getLogger(ProjectEmployeeService.class);
	
	@Autowired
	private IProjectEmployeeDao projectEmployeeDao;
	
	public List<ProjectEmployee> getAll() {
		log.debug("Entering getAll");
		return (List<ProjectEmployee>) projectEmployeeDao.findAll(); 
	}
	
	public Optional<ProjectEmployee> getById(int id) {
		log.debug("Entering getById with id parameter of " + id);
		return projectEmployeeDao.findById(id);
	}
	
	public ProjectEmployee save(ProjectEmployee projectEmployee) {
		Assert.notNull(projectEmployee, "Parameter projectEmployee must not be null");
		log.debug("Entering save");
		
		if(projectEmployeeDao.existsByEmployeeIdAndProjectId(projectEmployee.getEmployeeId(), projectEmployee.getProjectId()) == false) {
			return projectEmployeeDao.save(projectEmployee);
		}
		else {
			return projectEmployee;
		}
	}
	
	public void delete(ProjectEmployee projectEmployee) {
		Assert.notNull(projectEmployee, "Parameter projectEmployee must not be null");
		log.debug("Entering delete");
		
		projectEmployeeDao.delete(projectEmployee);
	}
	
	public void deleteByEmployeeAndProjectIds(int employeeId, int projectId) {
		log.debug("Entering deleteByEmployeeAndProject");
		Optional<ProjectEmployee> optionalAssignation = projectEmployeeDao.findByEmployeeIdAndProjectId(employeeId, projectId);
		
		if(optionalAssignation.isPresent()) {
			projectEmployeeDao.delete(optionalAssignation.get());
		}
	}
	
	public List<ProjectEmployee> getByProjectId(int id) {
		log.debug("Entering getByProjectId with id parameter of " + id);
		return projectEmployeeDao.findAllByProjectId(id);
	}
	
	public List<ProjectEmployee> getByEmployeeId(int id) {
		log.debug("Entering getByEmployeeId with id parameter of " + id);
		return projectEmployeeDao.findAllByEmployeeId(id);
	}
	
	public boolean projectEmployeeExists(int id) {
		log.debug("Entering projectEmployeeExists with id parameter of " + id);
		return projectEmployeeDao.existsById(id);
	}
}
