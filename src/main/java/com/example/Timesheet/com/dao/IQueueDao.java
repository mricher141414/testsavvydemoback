package com.example.Timesheet.com.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.Timesheet.com.model.QueueItem;

public interface IQueueDao extends  CrudRepository<QueueItem, Integer> {

}
