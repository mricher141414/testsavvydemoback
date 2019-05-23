package com.example.Timesheet.com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.Timesheet.com.model.Employee;

public interface IEmployeeDao extends CrudRepository<Employee, Integer>{
	
	List<Employee> findAllByManagerId(int id);
	
	List<Employee> findAllByRoleId(int id);
	
	List<Employee> findAllByDepartmentId(int id);
	
	@Query(value="SELECT employee_id FROM Project_Employee WHERE project_id = ?1", nativeQuery = true)
	List<Integer> findAllEmployeeIdByProjectId(int id);
}
