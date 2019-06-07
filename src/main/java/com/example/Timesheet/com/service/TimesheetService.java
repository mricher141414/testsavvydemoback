package com.example.Timesheet.com.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Timesheet.com.GlobalFunctions;
import com.example.Timesheet.com.GlobalVars;
import com.example.Timesheet.com.dao.ITimesheetDao;
import com.example.Timesheet.com.model.Project;
import com.example.Timesheet.com.model.Timesheet;
import com.example.Timesheet.com.model.TimesheetRow;
import com.example.Timesheet.com.model.TimesheetRowProject;

@Service
public class TimesheetService {

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
		
		if(timesheetDao.existsById(timesheet.getId()) == false) {
			timesheet.setVersion(0);
		}
		
		return timesheetDao.save(timesheet);
	}

	public List<Timesheet> getAll() {
		return (List<Timesheet>) this.timesheetDao.findAll();
	}

	public Optional<Timesheet> getById(int id) {
		return this.timesheetDao.findById(id);
	}
	
	public List<Timesheet> getAwaitingApprovalByEmployeeId(int employeeId) {
		
		return timesheetDao.findByEmployeeIdAndTimesheetStatusId(employeeId, GlobalVars.TimesheetStatusIdForWaitingApproval);
	}
	
	public void delete(Timesheet timesheet) {
		
		queueService.deleteByTimesheetId(timesheet.getId());
		timesheetDao.delete(timesheet);
	}
	
	public List<Timesheet> getTimesheetByEmployeeId(int id) {

		return this.timesheetDao.findByEmployeeId(id);

	}

	public List<Timesheet> getTimesheetByEmployeeIdAndStartDate(int id, Date startDate) {

		return this.timesheetDao.findByEmployeeIdAndStartDate(id, startDate);

	}

	public List<Timesheet> getByTimesheetStatusId(int id) {
		return timesheetDao.findByTimesheetStatusId(id);
	}
	
	public boolean timesheetExists(int id) {
		return timesheetDao.existsById(id);
	}
	
	public List<Project> getAllProjectsOnTimesheet(int id) {
		
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
	
	public Timesheet createTimesheetFromDateAndEmployeeId(int employeeId, Date userDate) {
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
