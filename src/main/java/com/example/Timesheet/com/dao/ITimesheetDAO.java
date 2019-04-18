package com.example.Timesheet.com.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.Timesheet.com.model.Timesheet;
@Repository
public interface ITimesheetDAO extends CrudRepository<Timesheet, Integer>{

	Timesheet findById(int id);
	List<Timesheet> findByEmployeeId(int id);

}
