package com.example.Timesheet.com.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.Timesheet.com.model.Project;

@Repository
public interface IProjectDAO extends CrudRepository<Project, Integer> {

}
