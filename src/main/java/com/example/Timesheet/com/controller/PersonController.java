package com.example.Timesheet.com.controller;

import java.sql.SQLException;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Timesheet.com.GlobalVars;
import com.example.Timesheet.com.dto.PersonDTO;
import com.example.Timesheet.com.mapper.PersonMapper;
import com.example.Timesheet.com.model.Person;
import com.example.Timesheet.com.service.PersonService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PersonController {
	
	 @Autowired
	 PersonService personService = new PersonService();
	 
	 @Autowired
	 PersonMapper personMapper = new PersonMapper();
	 
	  @GetMapping("/person")
	  public List<Person> getPerson() throws SQLException {		
		return personService.getPerson();
		  
	  }
	 
	  @PutMapping("/person")
	  public ResponseEntity<String> putPerson(@RequestBody PersonDTO persondto, @RequestParam(value="id") int id) throws SQLException {
		  
		Person person = personMapper.DTOtoPerson(persondto, id);
		personService.savePerson(person);
		
		return new ResponseEntity<String>(GlobalVars.PersonPutSuccessful, HttpStatus.OK);
	  }
}
