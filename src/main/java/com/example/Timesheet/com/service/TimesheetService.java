package com.example.Timesheet.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Timesheet.com.dao.ITimesheetDAO;
import com.example.Timesheet.com.model.Timesheet;

@Service
public class TimesheetService {

	@Autowired
	private ITimesheetDAO timesheetDAO;

	public void postTimesheet(Timesheet timesheet) {
		
		timesheetDAO.save(timesheet);

	}
	
	public List<Timesheet> getTimesheets() {
		return (List<Timesheet>) this.timesheetDAO.findAll();
	}
}
