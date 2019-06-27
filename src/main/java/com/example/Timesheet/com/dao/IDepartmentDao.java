package com.example.Timesheet.com.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.Timesheet.com.model.Department;

public interface IDepartmentDao extends CrudRepository<Department, Integer>{
	
	
}
