package com.example.Timesheet.com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Timesheet.com.dao.ITimeProjectDao;
import com.example.Timesheet.com.model.TimeProject;

@Service
public class TimeProjectService {

	@Autowired
	private ITimeProjectDao timeProjectDao;
	
	public List<TimeProject> getAll() {
		return (List<TimeProject>) timeProjectDao.findAll();
	}
	
	public void save(TimeProject timeProject) {
		timeProjectDao.save(timeProject);
	}
	
	public void delete(TimeProject timeProject) {
		timeProjectDao.delete(timeProject);
	}
	
	public Optional<TimeProject> getById(int id) {
		return timeProjectDao.findById(id);
	}
	
	public List<TimeProject> getByTimesheetRowId(int id) {
		return timeProjectDao.findByTimesheetRowId(id);
	}
	
	public List<TimeProject> getByProjectId(int id) {
		return timeProjectDao.findByProjectId(id);
	}
}
