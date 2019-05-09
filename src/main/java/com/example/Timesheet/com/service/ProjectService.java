package com.example.Timesheet.com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Timesheet.com.dao.IProjectDAO;
import com.example.Timesheet.com.model.Project;

@Service
public class ProjectService {

	@Autowired
	private IProjectDAO projectDAO;
	
	public Optional<Project> getById(int id) {
		return this.projectDAO.findById(id);
	}
}
