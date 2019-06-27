package com.example.Timesheet.com.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.Timesheet.com.model.TimesheetStatus;

public interface ITimesheetStatusDao extends CrudRepository<TimesheetStatus, Integer>{

}
