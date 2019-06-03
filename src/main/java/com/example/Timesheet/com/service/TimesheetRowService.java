package com.example.Timesheet.com.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Timesheet.com.GlobalVars;
import com.example.Timesheet.com.dao.ITimesheetRowDao;
import com.example.Timesheet.com.model.TimesheetRowProject;
import com.example.Timesheet.com.model.Timesheet;
import com.example.Timesheet.com.model.TimesheetRow;

@Service
public class TimesheetRowService {
	
	@Autowired
	private ITimesheetRowDao timesheetRowDao;
	
	public TimesheetRow save(TimesheetRow timesheetRow) {
		
		if(timesheetRowDao.existsById(timesheetRow.getId()) == false) {
			timesheetRow.setVersion(0);
		}
		
		timesheetRow.compensateTimezoneOnDates();
		return timesheetRowDao.save(timesheetRow);
	}
	
	public List<TimesheetRow> getTimesheetRows() {
		return (List<TimesheetRow>) this.timesheetRowDao.findAll();
	}
	
	public void deleteTimesheetRow(TimesheetRow timesheetRow) {
		
		this.timesheetRowDao.delete(timesheetRow);
		
	}
	
	public Optional<TimesheetRow> getById(int id) {
		
		return this.timesheetRowDao.findById(id);
		
	}
	
	public List<TimesheetRow> getByTimesheetId(int id) {
		return this.timesheetRowDao.findByTimesheetId(id);
	}
	
	public List<TimesheetRow> getByDate(Date date) {
		return this.timesheetRowDao.findByDate(date);
	}
	
	public void createWeekFromTimesheet(Timesheet timesheet) {
		
		for (int i = 0; i < 7; i++) {
			Date date = new Date(0L);
			TimesheetRow timesheetRow = new TimesheetRow();
			
			date.setTime(timesheet.getStartDate().getTime() + i * GlobalVars.MillisecondsPerDay);
			
			timesheetRow.setDate(date);
			timesheetRow.setTimesheetId(timesheet.getId());
			
			timesheetRow = this.save(timesheetRow);
		}
	}

}
