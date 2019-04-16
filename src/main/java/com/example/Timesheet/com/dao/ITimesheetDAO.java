package com.example.Timesheet.com.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.Timesheet.com.model.Timesheet;

public interface ITimesheetDAO extends CrudRepository<Timesheet, Integer>{

	Timesheet findById(int id);
	Timesheet findByEmployeeId(int id);

}
