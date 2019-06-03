package com.example.Timesheet.com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Timesheet.com.dao.ITimesheetStatusDao;
import com.example.Timesheet.com.model.TimesheetStatus;

@Service
public class TimesheetStatusService {
	
	@Autowired 
	private ITimesheetStatusDao timesheetStatusDao;
	
	public TimesheetStatus save(TimesheetStatus timesheetStatus) {
		
		if(timesheetStatusDao.existsById(timesheetStatus.getId()) == false) {
			timesheetStatus.setVersion(0);
		}
		
		return timesheetStatusDao.save(timesheetStatus);
	}
	
	public List<TimesheetStatus> getAll(){
		return (List<TimesheetStatus>) this.timesheetStatusDao.findAll();
	}
	
	public Optional<TimesheetStatus> getById(int id) {
		 
		return this.timesheetStatusDao.findById(id);
	}

	public void delete(TimesheetStatus timesheetStatus) {
		timesheetStatusDao.delete(timesheetStatus);
	}

}
