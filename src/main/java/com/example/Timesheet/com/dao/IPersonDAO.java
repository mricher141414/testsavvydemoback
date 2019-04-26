package com.example.Timesheet.com.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.Timesheet.com.model.Person;

public interface IPersonDAO extends CrudRepository<Person, Integer>{
	
	List<Person> findAllByManagerId(int id);

	List<Person> findAllByRoleId(int id);
	
	List<Person> findAllByDepartementId(int id);
}
