package com.example.Timesheet.com.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.Timesheet.com.model.Employee;

public interface IEmployeeDao extends CrudRepository<Employee, Integer>{
	
	List<Employee> findAllByManagerId(int id);

	List<Employee> findAllByRoleId(int id);
	
	List<Employee> findAllByDepartementId(int id);
}
