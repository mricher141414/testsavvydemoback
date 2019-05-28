package com.example.Timesheet.com.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Timesheet.com.GlobalVars;
import com.example.Timesheet.com.dao.ITimesheetRowDao;
import com.example.Timesheet.com.model.TimeProject;
import com.example.Timesheet.com.model.Timesheet;
import com.example.Timesheet.com.model.TimesheetRow;

@Service
public class TimesheetRowService {
	
	@Autowired
	private ITimesheetRowDao timesheetRowDao;
	
	@Autowired
	private TimeProjectService timeProjectService;

	public TimesheetRow save(TimesheetRow timesheetRow) {
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
			
			TimeProject timeProject = new TimeProject();
			timeProject.setTimesheetRowId(timesheetRow.getId());
			timeProject.setValue(0F);
			
			timeProjectService.save(timeProject);
		}
	}

}
