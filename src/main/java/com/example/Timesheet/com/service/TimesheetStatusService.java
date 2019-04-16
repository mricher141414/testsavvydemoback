package com.example.Timesheet.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Timesheet.com.dao.ITimesheetStatusDAO;
import com.example.Timesheet.com.model.TimesheetStatus;

@Service
public class TimesheetStatusService {
	
	@Autowired 
	private ITimesheetStatusDAO timesheetStatusDAO;
	
	public void post(TimesheetStatus timesheetStatus) {
		this.timesheetStatusDAO.save(timesheetStatus);
	
	}
	
	public List<TimesheetStatus> getAll(){
		return (List<TimesheetStatus>) this.timesheetStatusDAO.findAll();
	}
	
	public TimesheetStatus getById(int id) {
		 
		return this.timesheetStatusDAO.findById(id);
	}
	

}
