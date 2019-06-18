package com.example.Timesheet.com.service;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.Timesheet.com.GlobalVars;
import com.example.Timesheet.com.dao.ITimesheetRowDao;
import com.example.Timesheet.com.model.Timesheet;
import com.example.Timesheet.com.model.TimesheetRow;

@Service
public class TimesheetRowService implements Serializable {
	
	private static final long serialVersionUID = 6805948893359559706L;
	private static final Logger log = LogManager.getLogger(TimesheetRowService.class);
	
	@Autowired
	private ITimesheetRowDao timesheetRowDao;
	
	public TimesheetRow save(TimesheetRow timesheetRow) {
		Assert.notNull(timesheetRow, "Parameter timesheetRow must not be null");
		log.debug("Entering save");
		
		if(timesheetRowDao.existsById(timesheetRow.getId()) == false) {
			timesheetRow.setVersion(0);
		}
		
		timesheetRow.compensateTimezoneOnDates();
		return timesheetRowDao.save(timesheetRow);
	}
	
	public List<TimesheetRow> getTimesheetRows() {
		log.debug("Entering getAll");
		return (List<TimesheetRow>) this.timesheetRowDao.findAll();
	}
	
	public void deleteTimesheetRow(TimesheetRow timesheetRow) {
		Assert.notNull(timesheetRow, "Parameter timesheetRow must not be null");
		log.debug("Entering delete");
		
		this.timesheetRowDao.delete(timesheetRow);
		
	}
	
	public Optional<TimesheetRow> getById(int id) {
		log.debug("Entering getById with id parameter of " + id);
		return this.timesheetRowDao.findById(id);
	}
	
	public List<TimesheetRow> getByTimesheetId(int id) {
		log.debug("Entering getByTimesheetId with id parameter of " + id);
		return this.timesheetRowDao.findByTimesheetId(id);
	}
	
	public List<TimesheetRow> getByDate(Date date) {
		Assert.notNull(date, "Parameter date must not be null");
		log.debug("Entering getByDate");
		
		return this.timesheetRowDao.findByDate(date);
	}
	
	public void createWeekFromTimesheet(Timesheet timesheet) {
		Assert.notNull(timesheet, "Parameter timesheet must not be null");
		log.debug("Entering createWeekFromTimesheet");
		
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
