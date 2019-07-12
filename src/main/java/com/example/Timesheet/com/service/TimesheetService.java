package com.example.Timesheet.com.service;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.Timesheet.com.GlobalFunctions;
import com.example.Timesheet.com.GlobalVars;
import com.example.Timesheet.com.dao.ITimesheetDao;
import com.example.Timesheet.com.model.Project;
import com.example.Timesheet.com.model.Timesheet;
import com.example.Timesheet.com.model.TimesheetRow;
import com.example.Timesheet.com.model.TimesheetRowProject;

@Service
public class TimesheetService implements Serializable {

	private static final long serialVersionUID = 315770201263719197L;
	private static final Logger log = LogManager.getLogger(TimesheetService.class);

	@Autowired
	private ITimesheetDao timesheetDao;
	
	@Autowired
	private QueueService queueService;
	
	@Autowired
	private TimesheetRowService timesheetRowService;
	
	@Autowired
	private TimesheetRowProjectService timesheetRowProjectService;
	
	@Autowired
	private ProjectService projectService;

	public Timesheet save(Timesheet timesheet) {
		Assert.notNull(timesheet, "Parameter timesheet must not be null");
		log.debug("Entering save");
		
		if(timesheetDao.existsById(timesheet.getId()) == false) {
			timesheet.setVersion(0);
		}
		
		return timesheetDao.save(timesheet);
	}

	public List<Timesheet> getAll() {
		log.debug("Entering getAll");
		return (List<Timesheet>) this.timesheetDao.findAll();
	}

	public Optional<Timesheet> getById(int id) {
		log.debug("Entering getById with id parameter of " + id);
		return this.timesheetDao.findById(id);
	}
	
	public List<Timesheet> getAwaitingApprovalByEmployeeId(int employeeId) {
		log.debug("Entering getAwaitingApprovalByEmployeeId with employeeId parameter of " + employeeId);
		return timesheetDao.findByEmployeeIdAndTimesheetStatusId(employeeId, GlobalVars.TimesheetStatusIdForWaitingApproval);
	}
	
	public void delete(Timesheet timesheet) {
		Assert.notNull(timesheet, "Parameter timesheet must not be null");
		log.debug("Entering delete");
		
		queueService.deleteByTimesheetId(timesheet.getId());
		timesheetDao.delete(timesheet);
	}
	
	public List<Timesheet> getByEmployeeId(int id) {
		log.debug("Entering getByEmployeeId with id parameter of " + id);
		return this.timesheetDao.findByEmployeeId(id);

	}

	public List<Timesheet> getByEmployeeIdAndStartDate(int id, Date startDate) {
		Assert.notNull(startDate, "Parameter startDate must not be null");
		log.debug("Entering getByEmployeeIdAndStartDate");
		
		return this.timesheetDao.findByEmployeeIdAndStartDate(id, startDate);

	}

	public List<Timesheet> getByTimesheetStatusId(int id) {
		log.debug("Entering getByTimesheetStatusId with id parameter of " + id);
		return timesheetDao.findByTimesheetStatusId(id);
	}
	
	public boolean timesheetExists(int id) {
		log.debug("Entering timesheetExists with id parameter of " + id);
		return timesheetDao.existsById(id);
	}
	
	public List<Project> getProjectsOnTimesheet(int id) {
		log.debug("Entering getProjectOnTimesheet with id parameter of " + id);
		
		List<TimesheetRow> rows = timesheetRowService.getByTimesheetId(id);
		List<Integer> projectIds = new ArrayList<Integer>();
		List<Project> projects = new ArrayList<Project>();
		
		for (TimesheetRow row : rows) {
			
			List<TimesheetRowProject> rowProjects = new ArrayList<TimesheetRowProject>();
			
			rowProjects = timesheetRowProjectService.getByTimesheetRowId(row.getId());
			
			for(TimesheetRowProject rowProject : rowProjects) {
				
				if(projectIds.contains(rowProject.getProjectId()) == false) {
					projectIds.add(rowProject.getProjectId());
				}
			}
		}
		
		for(Integer projectId : projectIds) {
			projects.add(projectService.getById(projectId).get());
		}
		
		return projects;
	}
	
	public List<Timesheet> getLastWeekTimesheets (Date date) {
		Assert.notNull(date, "parameter date must not be null");
		log.debug("Entering getLastWeekTimesheets with parameter date of " + date);
		
		
		Date latestSunday = GlobalFunctions.findLatestSunday(date);
		
		date.setTime(latestSunday.getTime() - GlobalVars.MillisecondsPerDay);
		
		return timesheetDao.findByEndDate(date);
	}
	
	public Timesheet createFromDateAndEmployeeId(int employeeId, Date userDate) {
		Assert.notNull(userDate, "Parameter userDate must not be null");
		log.debug("Entering createFromDateAndEmployeeId");
		
		Timesheet timesheet = new Timesheet();
		Date startDate = new Date(0L);
		Date endDate = new Date(0L);
		
		timesheet.setNotes("");
		timesheet.setTotal(0F);
		timesheet.setEmployeeId(employeeId);
		timesheet.setTimesheetStatusId(1);
		
		startDate = GlobalFunctions.findLatestSunday(userDate);
		
		endDate.setTime(startDate.getTime() + 6 * GlobalVars.MillisecondsPerDay);
		timesheet.setStartDate(startDate);
		timesheet.setEndDate(endDate);
		
		return timesheet;
	}
}
