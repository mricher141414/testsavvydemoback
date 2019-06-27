package com.example.Timesheet.com.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import com.example.Timesheet.com.model.QueueItem;

public interface IQueueDao extends  CrudRepository<QueueItem, Integer> {

	@Transactional
	@Modifying
	void deleteByTimesheetId(Integer timesheetId);
}
