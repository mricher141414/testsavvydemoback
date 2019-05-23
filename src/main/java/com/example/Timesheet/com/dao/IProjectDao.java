package com.example.Timesheet.com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.Timesheet.com.model.Employee;
import com.example.Timesheet.com.model.Project;

@Repository
public interface IProjectDao extends CrudRepository<Project, Integer> {
	
	List<Project> findAllByProjectManagerId(int id);
	List<Project> findAllByClientId(int id);
	
	
	@Query(value="SELECT project_id FROM Project_Employee WHERE employee_id = ?1", nativeQuery = true)
	List<Integer> findAllProjectIdByEmployeeId(int id);
}
