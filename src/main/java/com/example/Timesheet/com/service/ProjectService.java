package com.example.Timesheet.com.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.Timesheet.com.dao.IProjectDao;
import com.example.Timesheet.com.model.Project;

@Service
public class ProjectService implements Serializable {

	private static final long serialVersionUID = -1550927581025107028L;
	private static final Logger log = LogManager.getLogger(ProjectService.class);
	
	@Autowired
	private IProjectDao projectDAO;
	
	EntityManager entityManager;
	
	public List<Project> getAll() {
		log.debug("Entering getAll");
		return (List<Project>) projectDAO.findAll();
	}
	
	public Optional<Project> getById(int id) {
		log.debug("Entering getById with id parameter of " + id);
		return this.projectDAO.findById(id);
	}
	
	public Project save(Project project) {
		Assert.notNull(project, "Parameter project must not be null");
		log.debug("Entering save");
		
		//entityManager.lock(project, LockModeType.OPTIMISTIC);
		
		if(projectDAO.existsById(project.getId()) == false) {
			project.setVersion(0);
		}
		
		project.compensateTimezoneOnDates();
		
		return projectDAO.save(project);
	}
	
	public void delete(Project project) {
		Assert.notNull(project, "Parameter project must not be null");
		log.debug("Entering delete");
		
		projectDAO.delete(project);
	}
	
	public List<Project> getByProjectManagerId(int id) {
		log.debug("Entering getByProjectManagerId with id parameter of " + id);
		return projectDAO.findAllByProjectManagerId(id);
	}
	
	public List<Project> getByClientId(int id) {
		log.debug("Entering getByClientId with id parameter of " + id);
		return projectDAO.findAllByClientId(id);
	}
	
	public boolean projectExists(int id) {
		log.debug("Entering projectExists with id parameter of " + id);
		return projectDAO.existsById(id);
	}
}
