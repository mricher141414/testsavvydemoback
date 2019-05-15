package com.example.Timesheet.com.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Timesheet.com.GlobalVars;
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
	
	public void delete(Timesheet timesheet) {
		timesheetDAO.delete(timesheet);
	}
	
	public List<Timesheet> getTimesheetByEmployeeId(int id) {

		return this.timesheetDAO.findByEmployeeId(id);

	}

	public List<Timesheet> getTimesheetByEmployeeIdAndStartDate(int id, Date startDate) {

		return this.timesheetDAO.findByEmployeeIdAndStartDate(id, startDate);

	}

	public List<Timesheet> getByTimesheetStatusId(int id) {
		return timesheetDAO.findByTimesheetStatusId(id);
	}
	
	public Timesheet createTimesheetFromDateAndEmployeeId(int employeeId, Date userDate) {
		Timesheet timesheet = new Timesheet();
		Date startDate = new Date(0L);
		Date endDate = new Date(0L);
		
		timesheet.setNotes("");
		timesheet.setTotal(0F);
		timesheet.setEmployeeId(employeeId);
		timesheet.setTimesheetStatusId(1);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(userDate);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		
		if(dayOfWeek != Calendar.SUNDAY) {
			int timeDifference = (dayOfWeek - Calendar.SUNDAY) * GlobalVars.MillisecondsPerDay;
			startDate.setTime(userDate.getTime() - timeDifference);
		}
		else {
			startDate.setTime(userDate.getTime());
		}
		
		endDate.setTime(startDate.getTime() + 6 * GlobalVars.MillisecondsPerDay);
		timesheet.setStartDate(startDate);
		timesheet.setEndDate(endDate);
		
		return timesheet;
	}
}
