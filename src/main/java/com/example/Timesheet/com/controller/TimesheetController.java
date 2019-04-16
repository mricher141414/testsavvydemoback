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
import org.springframework.web.bind.annotation.RestController;

import com.example.Timesheet.com.dto.TimesheetComplex;
import com.example.Timesheet.com.dto.TimesheetDTO;
import com.example.Timesheet.com.mapper.TimesheetMapper;
import com.example.Timesheet.com.model.Person;
import com.example.Timesheet.com.model.Timesheet;
import com.example.Timesheet.com.service.PersonService;
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
	private TimesheetStatusService timesheetStatusService = new TimesheetStatusService();

	@GetMapping("/timesheet")
	public List<Timesheet> findAllRoles(){

		return timesheetService.getTimesheets();

	}

	@PostMapping("/timesheet")
	public int post(@RequestBody TimesheetDTO timesheetDto) {

		Timesheet timesheet = this.timesheetMapper.DTOtoTimesheet(timesheetDto);

		this.timesheetService.postTimesheet(timesheet);

		return timesheet.getId();

	}

	@PutMapping("/timesheet")
	public void put(@RequestBody TimesheetDTO timesheetDto) {

		Timesheet timesheet = this.timesheetMapper.DTOtoTimesheet(timesheetDto);

		this.timesheetService.postTimesheet(timesheet);

	}

	@GetMapping("/timesheet/employee")
	public TimesheetComplex findbyId(@RequestParam(value="id") int id){
	
		Person person = this.personService.getPersonById(id);

		return this.mapToTimesheetComplex(person);

	}

	@GetMapping("/timesheet/manager")
	public List<TimesheetComplex> findbyManagerId(@RequestParam(value="id") int id){

		List<Person> employeesOfManager = this.personService.findAllByManagerId(id);
		List<TimesheetComplex> listOfTimesheets = new ArrayList<TimesheetComplex>();
		for(Person person : employeesOfManager) {
			TimesheetComplex timesheetComplex = this.mapToTimesheetComplex(person);
			listOfTimesheets.add(timesheetComplex);
		}

		return listOfTimesheets;

	}
	
	private TimesheetComplex mapToTimesheetComplex(Person person) {

		TimesheetComplex timesheetComplex = new TimesheetComplex();
		Timesheet timesheet = timesheetService.getTimesheetByEmployeeId(person.getId());


		timesheetComplex.setEndDate(timesheet.getEndDate());
		timesheetComplex.setNotes(timesheet.getNotes());
		timesheetComplex.setStartDate(timesheet.getStartDate());
		timesheetComplex.setEmployee(person);

		if(timesheet.getTimesheetStatusId()!= null) {
			timesheetComplex.setTimesheetStatus(timesheetStatusService.getById(timesheet.getTimesheetStatusId()));

		}

		return timesheetComplex;

	}
}
