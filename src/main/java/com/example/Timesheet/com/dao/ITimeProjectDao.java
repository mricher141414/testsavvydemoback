package com.example.Timesheet.com.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.Timesheet.com.model.TimeProject;

public interface ITimeProjectDao extends  CrudRepository<TimeProject, Integer> {

	List<TimeProject> findByTimesheetRowId(int id);
	List<TimeProject> findByProjectId(int id);
}
