package com.example.Timesheet.com.controller;

import java.sql.SQLException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Timesheet.com.GlobalFunctions;
import com.example.Timesheet.com.GlobalVars;
import com.example.Timesheet.com.dto.PersonDTO;
import com.example.Timesheet.com.mapper.PersonMapper;
import com.example.Timesheet.com.model.Person;
import com.example.Timesheet.com.service.PersonService;
import com.example.Timesheet.com.service.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.example.Timesheet.com.service.DepartementService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "PersonController")
public class PersonController {
	
	 @Autowired
	 PersonService personService = new PersonService();
	 
	 @Autowired
	 DepartementService departementService = new DepartementService();
	 
	 @Autowired
	 RoleService roleService = new RoleService();
	 
	 @Autowired
	 PersonMapper personMapper = new PersonMapper();
	 
	 @GetMapping("/person")
	 @ApiOperation("Returns a list of all persons in the system.")
	 public List<Person> findAllPersons() throws SQLException {		
		 return personService.findAll();
	 }
	 
	  @PutMapping("/person")
	  @ApiOperation(value = "Updates a person in the system by their identifier.", notes = "404 if any of the person's identifier specified in the address, department id, role id or manager id specified in the body is not found")
	  public ResponseEntity<String> modifyPerson(@ApiParam("person information to be modified. There is no need to keep values that will not be modified.")@RequestBody PersonDTO persondto,
		  											@ApiParam(value = "Id of the person to be modified. Cannot be null.", required = true)@RequestParam(value="id") int id) throws SQLException {
	
	  if (persondto.getDepartementId() != null) {
		  if(departementService.getById(persondto.getDepartementId()).isPresent() == false) {
			  return GlobalFunctions.createNotFoundResponse(GlobalVars.DepartementIdNotFound, "/person");
		  }
	  }
	  
	  if (persondto.getManagerId() != null) {
		  if(personService.findById(persondto.getManagerId()).isPresent() == false) {
			  return GlobalFunctions.createNotFoundResponse(GlobalVars.ManagerIdNotFound, "/person");
		  }
	  }
	  
	  if (persondto.getRoleId() != null) {
		  if(roleService.getById(persondto.getRoleId()).isPresent() == false) {
			  return GlobalFunctions.createNotFoundResponse(GlobalVars.RoleIdNotFound, "/person");
			  }
		  }
		  
		  Person person = personMapper.DTOtoPerson(persondto, id);
		  personService.savePerson(person);
		
		  return new ResponseEntity<String>(GlobalVars.PersonPutSuccessful, HttpStatus.OK);
	  }
}
