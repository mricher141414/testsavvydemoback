package com.example.Timesheet.com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Timesheet.com.dto.PersonComplex;
import com.example.Timesheet.com.dto.TimesheetComplex;
import com.example.Timesheet.com.dto.TimesheetDTO;
import com.example.Timesheet.com.mapper.TimesheetMapper;
import com.example.Timesheet.com.model.Person;
import com.example.Timesheet.com.model.Timesheet;
import com.example.Timesheet.com.service.DepartementService;
import com.example.Timesheet.com.service.PersonService;
import com.example.Timesheet.com.service.RoleService;
import com.example.Timesheet.com.service.TimesheetService;
import com.example.Timesheet.com.service.TimesheetStatusService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TimesheetController {

	@Autowired
	private TimesheetService timesheetService = new TimesheetService();
	private TimesheetMapper timesheetMapper = new TimesheetMapper();


	@Autowired
	private PersonService personService = new PersonService();
	
	@Autowired
	private RoleService roleService = new RoleService();

	@Autowired
	private TimesheetStatusService timesheetStatusService = new TimesheetStatusService();
	
	@Autowired
	private DepartementService departementService = new DepartementService();

	@GetMapping("/timesheet")
	public List<Timesheet> findAllTimesheets(){

		return timesheetService.getTimesheets();

	}
	
	@GetMapping("/timesheet/all")
	public List<Timesheet> findAllTimesheets(@RequestParam(value="id") int id){

		return timesheetService.getTimesheetByEmployeeId(id);

	}

	@PostMapping("/timesheet")
	@ResponseBody
	public String post(@RequestBody TimesheetDTO timesheetDto) {

		Timesheet timesheet = this.timesheetMapper.DTOtoTimesheet(timesheetDto);

		this.timesheetService.postTimesheet(timesheet);

		return "{\"id\": "+timesheet.getId()+"}";

	}

	@PutMapping("/timesheet")
	public void put(@RequestBody TimesheetDTO timesheetDto) {

		Timesheet timesheet = this.timesheetMapper.DTOtoTimesheet(timesheetDto);

		this.timesheetService.postTimesheet(timesheet);

	}

	@GetMapping("/timesheet/employee")
	public TimesheetComplex findbyId(@RequestParam(value="id") int id){

	
		Person person = this.personService.getPersonById(this.timesheetService.getTimesheetById(id).getEmployeeId());
		PersonComplex personComplex = new PersonComplex();
		personComplex = this.fromPersonToComplex(person);

		return this.mapToTimesheetComplex(this.timesheetService.getTimesheetById(id),personComplex);

	}

	@GetMapping("/timesheet/manager")
	public List<PersonComplex> findbyManagerId(@RequestParam(value="id") int id){

		List<Person> employeesOfManager = this.personService.findAllByManagerId(id);
		PersonComplex personComplex = new PersonComplex();
		List<PersonComplex> listOfPeople = new ArrayList<PersonComplex>();
		List<TimesheetComplex> tempList = new ArrayList<TimesheetComplex>();
		
		for(Person person : employeesOfManager) {
						
			personComplex = this.fromPersonToComplex(person);
			
				
			
			for(Timesheet timesheet: this.timesheetService.getTimesheetByEmployeeId(personComplex.getId()) ) {
		
					
			
				TimesheetComplex timesheetComplex = this.mapToTimesheetComplex(timesheet,personComplex);
				tempList.add(timesheetComplex);
			}
			
			personComplex.setTimesheets(tempList);
	
			
			listOfPeople.add(personComplex);
			tempList.clear();

			
		}

		return listOfPeople;

	}
	
	private TimesheetComplex mapToTimesheetComplex(Timesheet timesheet,PersonComplex person) {

		TimesheetComplex timesheetComplex = new TimesheetComplex();

		
		timesheetComplex.setId(timesheet.getId());
		timesheetComplex.setTotal(timesheet.getTotal());
		timesheetComplex.setEndDate(timesheet.getEndDate());
		timesheetComplex.setNotes(timesheet.getNotes());
		timesheetComplex.setStartDate(timesheet.getStartDate());
		timesheetComplex.setEmployee(person);

		if(timesheet.getTimesheetStatusId()!= null) {
			timesheetComplex.setTimesheetStatus(timesheetStatusService.getById(timesheet.getTimesheetStatusId()));

		}

		return timesheetComplex;

	}
	
	private PersonComplex fromPersonToComplex(Person person) {
		PersonComplex personComplex = new PersonComplex();
		personComplex.setId(person.getId());
		personComplex.setLastName(person.getLastName());
		personComplex.setFirstName(person.getFirstName());
		personComplex.setAddress(person.getAddress());
		personComplex.setEmailAddress(person.getEmail());
		personComplex.setPassword(person.getPassword());
		personComplex.setDateOfBirth(person.getDateOfBirth());
		personComplex.setManager(this.personService.getPersonById(person.getManagerId()));
		
		if(person.getRoleId()!= null) {
			personComplex.setRole(this.roleService.getById(person.getRoleId()));
		}
		
		if(person.getDepartementId() != null) {
			personComplex.setDepartement(this.departementService.getById(person.getDepartementId()));
		}
				
		return personComplex;
		
	}
}
