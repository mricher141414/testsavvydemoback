package com.example.Timesheet.com.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.Timesheet.com.model.TimesheetRowProject;

public interface ITimesheetRowProjectDao extends  CrudRepository<TimesheetRowProject, Integer> {

	List<TimesheetRowProject> findByTimesheetRowId(int id);
	List<TimesheetRowProject> findByProjectId(int id);
	
	List<TimesheetRowProject> findByTimesheetRowIdAndProjectId(int timesheetRowId, int projectId);
}
