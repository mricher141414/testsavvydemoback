package com.example.Timesheet.com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Timesheet.com.dao.IProjectDAO;
import com.example.Timesheet.com.model.Project;

@Service
public class ProjectService {

	@Autowired
	private IProjectDAO projectDAO;
	
	public List<Project> getAll() {
		return (List<Project>) projectDAO.findAll();
	}
	
	public Optional<Project> getById(int id) {
		return this.projectDAO.findById(id);
	}
	
	public void save(Project project) {
		projectDAO.save(project);
	}
	
	public void delete(Project project) {
		projectDAO.delete(project);
	}
	
	public List<Project> getByProjectManagerId(int id) {
		return projectDAO.findAllByProjectManagerId(id);
	}
	
	public List<Project> getByClientId(int id) {
		return projectDAO.findAllByClientId(id);
	}
}
