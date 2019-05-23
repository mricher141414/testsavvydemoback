package com.example.Timesheet.com.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Timesheet.com.GlobalVars;
import com.example.Timesheet.com.dao.ITimesheetRowDAO;
import com.example.Timesheet.com.model.Timesheet;
import com.example.Timesheet.com.model.TimesheetRow;

@Service
public class TimesheetRowService {
	
	@Autowired
	private ITimesheetRowDAO timesheetRowDAO;

	public void save(TimesheetRow timesheet) {
		
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
	
	public void createWeekFromTimesheet(Timesheet timesheet) {
		
		for (int i = 0; i < 7; i++) {
			Date date = new Date(0L);
			TimesheetRow timesheetRow = new TimesheetRow();
			
			date.setTime(timesheet.getStartDate().getTime() + i * GlobalVars.MillisecondsPerDay);
			
			timesheetRow.setDate(date);
			timesheetRow.setTimesheetId(timesheet.getId());
			
			this.save(timesheetRow);
		}
	}

}
