package com.example.Timesheet.com.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.Timesheet.com.model.Timesheet;

public interface ITimesheetDAO extends CrudRepository<Timesheet, Integer>{

}
