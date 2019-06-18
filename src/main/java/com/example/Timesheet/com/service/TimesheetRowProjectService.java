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
import com.example.Timesheet.com.dao.ITimesheetRowProjectDao;
import com.example.Timesheet.com.model.TimesheetRow;
import com.example.Timesheet.com.model.TimesheetRowProject;

@Service
public class TimesheetRowProjectService implements Serializable {

	private static final long serialVersionUID = 7369469747959937728L;
	private static final Logger log = LogManager.getLogger(TimesheetRowProjectService.class);

	@Autowired
	private ITimesheetRowProjectDao timesheetRowProjectDao;
	
	@Autowired
	private TimesheetRowService timesheetRowService;
	
	public List<TimesheetRowProject> getAll() {
		log.debug("Entering getAll");
		return (List<TimesheetRowProject>) timesheetRowProjectDao.findAll();
	}
	
	public TimesheetRowProject save(TimesheetRowProject timesheetRowProject) {
		Assert.notNull(timesheetRowProject, "Parameter timesheetRowProject must not be null");
		log.debug("Entering save");
		
		if(timesheetRowProjectDao.existsById(timesheetRowProject.getId()) == false) {
			timesheetRowProject.setVersion(0);
		}
		
		return timesheetRowProjectDao.save(timesheetRowProject);
	}
	
	public TimesheetRowProject saveIncomplete(TimesheetRowProject timesheetRowProject) {
		Assert.notNull(timesheetRowProject, "Parameter timesheetRowProject must not be null");
		log.debug("Entering saveIncomplete");
		
		Optional<TimesheetRowProject> optionalTimesheetRowProject = timesheetRowProjectDao.findById(timesheetRowProject.getId());
		
		if(optionalTimesheetRowProject.isPresent()) {
			TimesheetRowProject dbTimesheetRowProject = optionalTimesheetRowProject.get();
		
			if(timesheetRowProject.getProjectId() == null) {
				timesheetRowProject.setProjectId(dbTimesheetRowProject.getProjectId());
			}
			
			if(timesheetRowProject.getTimesheetRowId() == null) {
				timesheetRowProject.setTimesheetRowId(dbTimesheetRowProject.getTimesheetRowId());
			}
			
			if(timesheetRowProject.getValue() == null) {
				timesheetRowProject.setValue(dbTimesheetRowProject.getValue());
			}
		}
		
		return save(timesheetRowProject);
	}
	
	public void delete(TimesheetRowProject timesheetRowProject) {
		Assert.notNull(timesheetRowProject, "Parameter timesheetRowProject must not be null");
		log.debug("Entering delete");
		
		timesheetRowProjectDao.delete(timesheetRowProject);
	}
	
	public Optional<TimesheetRowProject> getById(int id) {
		log.debug("Entering getById with id parameter of " + id);
		return timesheetRowProjectDao.findById(id);
	}
	
	public List<TimesheetRowProject> getByTimesheetRowId(int id) {
		log.debug("Entering getByTimesheetRowId with id parameter of " + id);
		return timesheetRowProjectDao.findByTimesheetRowId(id);
	}
	
	public List<TimesheetRowProject> getByProjectId(int id) {
		log.debug("Entering getByProjectId with id parameter of " + id);
		return timesheetRowProjectDao.findByProjectId(id);
	}
	
	public Float calculateAverageTimeWorked (int projectId, Date firstSunday, int weeks) {
		log.debug("Entering calculateAverageTimeWorked with projectId parameter of " + projectId + ", firstSunday of " + firstSunday + " and weeks of " + weeks);
		
		Float totalHours = 0F;
		int days = 7 * weeks;
		
		for (int currentWeek = 0; currentWeek < weeks; currentWeek++) {
			for(int currentDay = 0; currentDay < 7; currentDay++) {
				
				float totalHoursToday = 0F;
				List<TimesheetRow> rows = timesheetRowService.getByDate(firstSunday);
				
				for(TimesheetRow row : rows) {
					List<TimesheetRowProject> timesheetRowProjects = timesheetRowProjectDao.findByTimesheetRowIdAndProjectId(row.getId(), projectId);
					
					for(TimesheetRowProject timesheetRowProject : timesheetRowProjects) {
						totalHoursToday = totalHoursToday + timesheetRowProject.getValue();
					}
				}
				
				if(totalHoursToday == 0) {
					days--;
				}
				
				totalHours = totalHours + totalHoursToday;
				
				firstSunday.setTime(firstSunday.getTime() + GlobalVars.MillisecondsPerDay);
			}
		}
		
		Float average = totalHours / days;
		
		return average;
	}
}
