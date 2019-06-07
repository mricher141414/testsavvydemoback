package com.example.Timesheet.com.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.Timesheet.com.model.Timesheet;
@Repository
public interface ITimesheetDao extends CrudRepository<Timesheet, Integer>{

	List<Timesheet> findByEmployeeId(int id);
	List<Timesheet> findByEmployeeIdAndStartDate(int id, Date startDate);
	List<Timesheet> findByTimesheetStatusId(int timesheetStatusId);
	List<Timesheet> findByEmployeeIdAndTimesheetStatusId(int employeeId, int timesheetStatusId);
}
