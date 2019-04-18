package com.example.Timesheet.com.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.Timesheet.com.model.TimesheetRow;

public interface ITimesheetRowDAO extends CrudRepository<TimesheetRow, Integer>{
	
	TimesheetRow findById(int id);
}
