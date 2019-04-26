package com.example.Timesheet.com.controller;

import java.sql.Date;
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

import com.example.Timesheet.com.dto.PersonComplexWithTimesheets;
import com.example.Timesheet.com.dto.TimesheetComplex;
import com.example.Timesheet.com.dto.PersonComplex;
import com.example.Timesheet.com.dto.TimesheetComplexWithEmployee;
import com.example.Timesheet.com.dto.TimesheetDTO;
import com.example.Timesheet.com.mapper.TimesheetMapper;
import com.example.Timesheet.com.model.Person;
import com.example.Timesheet.com.model.Timesheet;
import com.example.Timesheet.com.service.DepartementService;
import com.example.Timesheet.com.service.PersonService;
import com.example.Timesheet.com.service.RoleService;
import com.example.Timesheet.com.service.TimesheetRowService;
import com.example.Timesheet.com.service.TimesheetService;
import com.example.Timesheet.com.service.TimesheetStatusService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TimesheetController {

	@Autowired
	private TimesheetService timesheetService = new TimesheetService();
	
	@Autowired
	private TimesheetMapper timesheetMapper = new TimesheetMapper();

	@Autowired
	private PersonService personService = new PersonService();

	@Autowired
	private RoleService roleService = new RoleService();

	@Autowired
	private TimesheetStatusService timesheetStatusService = new TimesheetStatusService();

	@Autowired
	private DepartementService departementService = new DepartementService();
	
	@Autowired
	private TimesheetRowService timesheetRowService = new TimesheetRowService();

	@GetMapping("/timesheet/all")
	public List<Timesheet> findAllTimesheets(){

		return timesheetService.getTimesheets();

	}

	@GetMapping("/timesheet")
	public List<Timesheet> findAllTimesheets(@RequestParam(value="id") int id){

		return timesheetService.getTimesheetByEmployeeId(id);

	}

	@PostMapping("/timesheet")
	@ResponseBody
	public String post(@RequestBody TimesheetDTO timesheetDto) {

		Timesheet timesheet = this.timesheetMapper.DTOtoTimesheet(timesheetDto, timesheetDto.getId());

		this.timesheetService.postTimesheet(timesheet);

		return "{\"id\": "+timesheet.getId()+"}";

	}

	@PutMapping("/timesheet")
	public void put(@RequestBody TimesheetDTO timesheetDto, @RequestParam(value="id") int id) {

		Timesheet timesheet = this.timesheetMapper.DTOtoTimesheet(timesheetDto, id);

		this.timesheetService.postTimesheet(timesheet);

	}

	@GetMapping("/timesheet/employee")
	public TimesheetComplexWithEmployee findbyId(@RequestParam(value="id") int id){
		TimesheetComplexWithEmployee timesheetEmployee = (TimesheetComplexWithEmployee) this.fromTimesheetToComplexWithEmployee(this.timesheetService.getTimesheetById(id).get());
		timesheetEmployee = this.addEmployeeToTimesheetComplexWithEmployee(timesheetEmployee);
		return timesheetEmployee;
	}

	@GetMapping("/timesheet/manager")
	public List<PersonComplexWithTimesheets> findbyManagerId(@RequestParam(value="id") int id, @RequestParam(value="startDate") Date startDate){

		List<Person> employeesOfManager = this.personService.findAllByManagerId(id);
		PersonComplexWithTimesheets personWithTimesheets;
		List<PersonComplexWithTimesheets> listOfPeople = new ArrayList<PersonComplexWithTimesheets>();

		for(Person person : employeesOfManager) {

			personWithTimesheets = this.fromPersonToComplexWithTimesheets(person);
			
			personWithTimesheets = this.addTimesheetsToPersonComplexByStartDate(personWithTimesheets, startDate);

			listOfPeople.add(personWithTimesheets);

		}
		return listOfPeople;
	}

	private TimesheetComplex fromTimesheetToComplex(Timesheet timesheet) {

		TimesheetComplex timesheetComplex = new TimesheetComplex();


		timesheetComplex.setId(timesheet.getId());
		timesheetComplex.setTotal(timesheet.getTotal());
		timesheetComplex.setEndDate(timesheet.getEndDate());
		timesheetComplex.setNotes(timesheet.getNotes());
		timesheetComplex.setStartDate(timesheet.getStartDate());
		timesheetComplex.setTimesheetRows(this.timesheetRowService.getByTimesheetId(timesheet.getId()));

		if(timesheet.getTimesheetStatusId()!= null) {
			timesheetComplex.setTimesheetStatus(timesheetStatusService.getById(timesheet.getTimesheetStatusId()));

		}
		return timesheetComplex;
	}
	
	private TimesheetComplexWithEmployee fromTimesheetToComplexWithEmployee(Timesheet timesheet) {

		TimesheetComplexWithEmployee timesheetComplex = new TimesheetComplexWithEmployee();


		timesheetComplex.setId(timesheet.getId());
		timesheetComplex.setTotal(timesheet.getTotal());
		timesheetComplex.setEndDate(timesheet.getEndDate());
		timesheetComplex.setNotes(timesheet.getNotes());
		timesheetComplex.setStartDate(timesheet.getStartDate());
		timesheetComplex.setTimesheetRows(this.timesheetRowService.getByTimesheetId(timesheet.getId()));

		if(timesheet.getTimesheetStatusId()!= null) {
			timesheetComplex.setTimesheetStatus(timesheetStatusService.getById(timesheet.getTimesheetStatusId()));

		}
		return timesheetComplex;
	}
	
	private TimesheetComplexWithEmployee addEmployeeToTimesheetComplexWithEmployee(TimesheetComplexWithEmployee timesheetEmployee) {
		Person person = this.personService.findById(this.timesheetService.getTimesheetById(timesheetEmployee.getId()).get().getEmployeeId()).get();
		PersonComplex personComplex = new PersonComplex();
		personComplex = this.fromPersonToComplex(person);
		
		timesheetEmployee.setEmployee(personComplex);
		
		return timesheetEmployee;
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
		personComplex.setManager(this.personService.findById(person.getManagerId()).get());

		if(person.getRoleId()!= null) {
			personComplex.setRole(this.roleService.getById(person.getRoleId()).get());
		}

		if(person.getDepartementId() != null) {
			personComplex.setDepartement(this.departementService.getById(person.getDepartementId()).get());
		}

		return personComplex;

	}
	
	private PersonComplexWithTimesheets fromPersonToComplexWithTimesheets(Person person) {
		PersonComplexWithTimesheets personComplex = new PersonComplexWithTimesheets();
		personComplex.setId(person.getId());
		personComplex.setLastName(person.getLastName());
		personComplex.setFirstName(person.getFirstName());
		personComplex.setAddress(person.getAddress());
		personComplex.setEmailAddress(person.getEmail());
		personComplex.setPassword(person.getPassword());
		personComplex.setDateOfBirth(person.getDateOfBirth());
		personComplex.setManager(this.personService.findById(person.getManagerId()).get());

		if(person.getRoleId()!= null) {
			personComplex.setRole(this.roleService.getById(person.getRoleId()).get());
		}

		if(person.getDepartementId() != null) {
			personComplex.setDepartement(this.departementService.getById(person.getDepartementId()).get());
		}

		return personComplex;

	}
	
	private PersonComplexWithTimesheets addTimesheetsToPersonComplexByStartDate(PersonComplexWithTimesheets person, Date startDate) {
		List<Timesheet> timesheetsPerson = timesheetService.getTimesheetByEmployeeIdAndStartDate(person.getId(), startDate);
		
		for (Timesheet timesheet : timesheetsPerson) {
			TimesheetComplex timesheetComplex = this.fromTimesheetToComplex(timesheet);
			person.addToTimesheets(timesheetComplex);
		}
		return person;
	}
}
