package com.example.Timesheet.com.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Timesheet.com.dao.ITimesheetDAO;
import com.example.Timesheet.com.model.Timesheet;

@Service
public class TimesheetService {

	@Autowired
	private ITimesheetDAO timesheetDAO;

	public void save(Timesheet timesheet) {
		
		timesheetDAO.save(timesheet);

	}

	public List<Timesheet> getAll() {
		return (List<Timesheet>) this.timesheetDAO.findAll();
	}

	public Optional<Timesheet> getById(int id) {

		return this.timesheetDAO.findById(id);

	}
	
	public List<Timesheet> getTimesheetByEmployeeId(int id) {

		return this.timesheetDAO.findByEmployeeId(id);

	}

	public List<Timesheet> getTimesheetByEmployeeIdAndStartDate(int id, Date startDate) {

		return this.timesheetDAO.findByEmployeeIdAndStartDate(id, startDate);

	}


}
