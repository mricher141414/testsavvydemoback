package com.example.Timesheet.com.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.Timesheet.com.model.Project;

@Repository
public interface IProjectDAO extends CrudRepository<Project, Integer> {
	
	List<Project> findAllByProjectManagerId(int id);
	List<Project> findAllByClientId(int id);
}
