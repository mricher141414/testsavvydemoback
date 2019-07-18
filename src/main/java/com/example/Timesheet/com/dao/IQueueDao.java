package com.example.Timesheet.com.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import com.example.Timesheet.com.model.QueueItem;

public interface IQueueDao extends  CrudRepository<QueueItem, Integer> {

	List<QueueItem> findByTimesheetId(Integer timesheetId);
	
	@Transactional
	@Modifying
	void deleteByTimesheetId(Integer timesheetId);
}
