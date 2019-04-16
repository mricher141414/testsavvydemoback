package com.example.Timesheet.com.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.Timesheet.com.model.Person;

public interface IPersonDAO extends CrudRepository<Person, Integer>{
	
	Person findById(int id);
	
	List<Person> findAllByManagerId(int id);


}
