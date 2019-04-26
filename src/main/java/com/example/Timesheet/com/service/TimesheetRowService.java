package com.example.Timesheet.com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Timesheet.com.dao.ITimesheetRowDAO;
import com.example.Timesheet.com.model.TimesheetRow;

@Service
public class TimesheetRowService {
	
	@Autowired
	private ITimesheetRowDAO timesheetRowDAO;

	public void postTimesheetRow(TimesheetRow timesheet) {
		
		timesheetRowDAO.save(timesheet);

	}
	
	public List<TimesheetRow> getTimesheetRows() {
		return (List<TimesheetRow>) this.timesheetRowDAO.findAll();
	}
	
	public void deleteTimesheetRow(TimesheetRow timesheetRow) {
		
		this.timesheetRowDAO.delete(timesheetRow);
		
	}
	
	public Optional<TimesheetRow> getById(int id) {
		
		return this.timesheetRowDAO.findById(id);
		
	}
	
	public List<TimesheetRow> getByTimesheetId(int id) {
		return this.timesheetRowDAO.findByTimesheetId(id);
	}

}
