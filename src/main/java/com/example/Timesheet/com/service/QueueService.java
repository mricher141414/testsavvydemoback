package com.example.Timesheet.com.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.Timesheet.com.dao.IQueueDao;
import com.example.Timesheet.com.model.QueueItem;

@Service
public class QueueService implements Serializable {

	private static final long serialVersionUID = 8045844993535704535L;
	private static final Logger log = LogManager.getLogger(QueueService.class);
	
	@Autowired
	private IQueueDao queueDao;
	
	public List<QueueItem> getAll() {
		log.debug("Entering getAll");
		return (List<QueueItem>) queueDao.findAll();
	}
	
	public QueueItem save(QueueItem queueItem) {
		Assert.notNull(queueItem, "Parameter queueItem must not be null");
		log.debug("Entering save");
		
		return queueDao.save(queueItem);
	}
	
	public Optional<QueueItem> getById(int id) {
		log.debug("Entering getById with id parameter of " + id);
		return queueDao.findById(id);
	}
	
	public void delete(QueueItem queueItem) {
		Assert.notNull(queueItem, "Parameter queueItem must not be null");
		log.debug("Entering save");
		
		queueDao.delete(queueItem);
	}
	
	public void deleteByTimesheetId(int timesheetId) {
		log.debug("Entering deleteByTimesheetId with timesheetId parameter of " + timesheetId);
		queueDao.deleteByTimesheetId(timesheetId);
	}
	
}
