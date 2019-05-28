package com.example.Timesheet.com.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Timesheet.com.GlobalVars;
import com.example.Timesheet.com.dao.ITimeProjectDao;
import com.example.Timesheet.com.model.TimeProject;
import com.example.Timesheet.com.model.TimesheetRow;

@Service
public class TimeProjectService {

	@Autowired
	private ITimeProjectDao timeProjectDao;
	
	@Autowired
	private TimesheetRowService timesheetRowService;
	
	public List<TimeProject> getAll() {
		return (List<TimeProject>) timeProjectDao.findAll();
	}
	
	public void save(TimeProject timeProject) {
		timeProjectDao.save(timeProject);
	}
	
	public TimeProject saveIncomplete(TimeProject timeProject) {

		Optional<TimeProject> optionalTimeProject = timeProjectDao.findById(timeProject.getId());
		
		if(optionalTimeProject.isPresent()) {
			TimeProject dbTimeProject = optionalTimeProject.get();
		
			if(timeProject.getProjectId() == null) {
				timeProject.setProjectId(dbTimeProject.getProjectId());
			}
			
			if(timeProject.getTimesheetRowId() == null) {
				timeProject.setTimesheetRowId(dbTimeProject.getTimesheetRowId());
			}
			
			if(timeProject.getValue() == null) {
				timeProject.setValue(dbTimeProject.getValue());
			}
		}
		
		return timeProjectDao.save(timeProject);
	}
	
	public void delete(TimeProject timeProject) {
		timeProjectDao.delete(timeProject);
	}
	
	public Optional<TimeProject> getById(int id) {
		return timeProjectDao.findById(id);
	}
	
	public List<TimeProject> getByTimesheetRowId(int id) {
		return timeProjectDao.findByTimesheetRowId(id);
	}
	
	public List<TimeProject> getByProjectId(int id) {
		return timeProjectDao.findByProjectId(id);
	}
	
	public Float calculateAverageTimeWorked (int projectId, Date firstSunday, int weeks) {
		
		Float totalHours = 0F;
		int days = 7 * weeks;
		
		for (int currentWeek = 0; currentWeek < weeks; currentWeek++) {
			for(int currentDay = 0; currentDay < 7; currentDay++) {
				
				float totalHoursToday = 0F;
				List<TimesheetRow> rows = timesheetRowService.getByDate(firstSunday);
				
				for(TimesheetRow row : rows) {
					List<TimeProject> timeProjects = timeProjectDao.findByTimesheetRowIdAndProjectId(row.getId(), projectId);
					
					for(TimeProject timeProject : timeProjects) {
						totalHoursToday = totalHoursToday + timeProject.getValue();
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
