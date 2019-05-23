package com.example.Timesheet.com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Timesheet.com.dao.IProjectEmployeeDao;
import com.example.Timesheet.com.model.ProjectEmployee;

@Service
public class ProjectEmployeeService {

	@Autowired
	private IProjectEmployeeDao projectEmployeeDao;
	
	public List<ProjectEmployee> getAll() {
		return (List<ProjectEmployee>) projectEmployeeDao.findAll(); 
	}
	
	public Optional<ProjectEmployee> getById(int id) {
		return projectEmployeeDao.findById(id);
	}
	
	public ProjectEmployee save(ProjectEmployee projectEmployee) {
		return projectEmployeeDao.save(projectEmployee);
	}
	
	public void delete(ProjectEmployee projectEmployee) {
		projectEmployeeDao.delete(projectEmployee);
	}
	
	public List<ProjectEmployee> getByProjectId(int id) {
		return projectEmployeeDao.findAllByProjectId(id);
	}
	
	public List<ProjectEmployee> getByEmployeeId(int id) {
		return projectEmployeeDao.findAllByEmployeeId(id);
	}
	
	public boolean projectEmployeeExists(int id) {
		return projectEmployeeDao.existsById(id);
	}
}
