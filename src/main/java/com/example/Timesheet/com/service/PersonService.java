package com.example.Timesheet.com.service;

import java.util.List;
import java.util.Optional;

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
	
	public Person getPersonById(int id ) {
		
		return this.person.findById(id);
		
	}
	
	public List<Person> findAllByManagerId(int id){
		
		return this.person.findAllByManagerId(id);
		
	}
	
}
