package com.example.Timesheet.com.controller;

import java.sql.SQLException;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Timesheet.com.dto.PersonDTO;
import com.example.Timesheet.com.mapper.PersonMapper;
import com.example.Timesheet.com.model.Person;
import com.example.Timesheet.com.service.PersonService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PersonController {
	
//TODO : refaire cette classe
	 @Autowired
	 PersonService personService = new PersonService();
	 
	 PersonMapper personMapper = new PersonMapper();
	 
	  @GetMapping("/person")
	  public List<Person> getPerson() throws SQLException {
		  
		
		return personService.getPerson();
		  
	  }
	 
	  @PutMapping("/person")
	  public void putPerson(@RequestBody PersonDTO persondto) throws SQLException {
		  
		Person person = personMapper.DTOtoPerson(persondto);
		personService.savePerson(person);
		  
	  }
}
