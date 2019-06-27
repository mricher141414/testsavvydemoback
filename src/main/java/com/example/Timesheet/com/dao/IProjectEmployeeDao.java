package com.example.Timesheet.com.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.Timesheet.com.model.ProjectEmployee;

public interface IProjectEmployeeDao extends CrudRepository<ProjectEmployee, Integer> {

	List<ProjectEmployee> findAllByProjectId(int id);
	List<ProjectEmployee> findAllByEmployeeId(int id);
	boolean existsByEmployeeIdAndProjectId(int employeeId, int projectId);
}
