package com.example.Timesheet.com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Timesheet.com.dao.IQueueDao;
import com.example.Timesheet.com.model.QueueItem;

@Service
public class QueueService {

	@Autowired
	private IQueueDao queueDao;
	
	public List<QueueItem> getAll() {
		return (List<QueueItem>) queueDao.findAll();
	}
	
	public QueueItem save(QueueItem queueItem) {
		return queueDao.save(queueItem);
	}
	
	public Optional<QueueItem> getById(int id) {
		return queueDao.findById(id);
	}
	
	public void delete(QueueItem queueItem) {
		queueDao.delete(queueItem);
	}
	
	public void deleteByTimesheetId(int timesheetId) {
		queueDao.deleteByTimesheetId(timesheetId);
	}
	
}
