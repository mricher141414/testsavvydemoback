package com.example.Timesheet.com.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.Timesheet.com.model.TimesheetRow;

public interface ITimesheetRowDAO extends CrudRepository<TimesheetRow, Integer>{
	
	
	List<TimesheetRow> findByTimesheetId(int id);
}
