package com.example.Timesheet.com.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.Timesheet.com.dao.ITimesheetStatusDao;
import com.example.Timesheet.com.model.TimesheetStatus;

@Service
public class TimesheetStatusService implements Serializable {
	
	private static final long serialVersionUID = -6201820112620836210L;
	private static final Logger log = LogManager.getLogger(TimesheetStatusService.class);
	
	@Autowired 
	private ITimesheetStatusDao timesheetStatusDao;
	
	public TimesheetStatus save(TimesheetStatus timesheetStatus) {
		Assert.notNull(timesheetStatus, "Parameter timesheetStatus must not be null");
		log.debug("Entering save");
		
		if(timesheetStatusDao.existsById(timesheetStatus.getId()) == false) {
			timesheetStatus.setVersion(0);
		}
		
		return timesheetStatusDao.save(timesheetStatus);
	}
	
	public List<TimesheetStatus> getAll() {
		log.debug("Entering getAll");
		return (List<TimesheetStatus>) this.timesheetStatusDao.findAll();
	}
	
	public Optional<TimesheetStatus> getById(int id) {
		log.debug("Entering getById with id parameter of " + id);
		return this.timesheetStatusDao.findById(id);
	}

	public void delete(TimesheetStatus timesheetStatus) {
		Assert.notNull(timesheetStatus, "Parameter timesheetStatus must not be null");
		log.debug("Entering delete");
		
		timesheetStatusDao.delete(timesheetStatus);
	}

}
