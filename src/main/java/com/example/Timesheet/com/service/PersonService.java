package com.example.Timesheet.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Timesheet.com.dao.IPersonDAO;
import com.example.Timesheet.com.model.Person;

@Service	
public class PersonService {
	@Autowired
	private IPersonDAO person;

	public void savePerson(Person person) {

		this.person.save(person);
		
	}
	
	public List<Person> getPerson(){
		
		return (List<Person>) this.person.findAll();
		
	}
	
}
