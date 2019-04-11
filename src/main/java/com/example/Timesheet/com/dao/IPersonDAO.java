package com.example.Timesheet.com.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.Timesheet.com.model.Person;

public interface IPersonDAO extends CrudRepository<Person, Integer>{


}
