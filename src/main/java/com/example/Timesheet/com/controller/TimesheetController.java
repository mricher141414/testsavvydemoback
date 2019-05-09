package com.example.Timesheet.com.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Timesheet.com.dto.PersonComplexWithManager;
import com.example.Timesheet.com.dto.PersonComplex;
import com.example.Timesheet.com.dto.TimesheetComplex;
import com.example.Timesheet.com.GlobalFunctions;
import com.example.Timesheet.com.GlobalVars;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "TimesheetController")
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
	@ApiOperation("Returns a list of all timesheets in the system.")
	public List<Timesheet> findAllTimesheets(){

		List<Timesheet> timesheets = timesheetService.getTimesheets();
		
		return timesheets;

	}

	@GetMapping("/timesheet")
	@ApiOperation(value = "Returns the timesheet with the specified identifier.", 
					notes = "404 if the timesheet's identifier cannot be found.")
	public ResponseEntity<?> findTimesheetById(@ApiParam(value = "Id of the timesheet to retrieve", required = true) @RequestParam(value="id") int id) throws Exception{

		Optional<Timesheet> optionalTimesheet = timesheetService.getTimesheetById(id);
		
		if(optionalTimesheet.isPresent()) {
			return new ResponseEntity<>(optionalTimesheet.get(),HttpStatus.OK);
		}
		else {
			return GlobalFunctions.createNotFoundResponse(GlobalVars.TimesheetIdNotFound, "/timesheet");
		}
	}

	@PostMapping("/timesheet")
	@ApiOperation(value = "Creates a new timesheet in the system.", 
					notes = "404 if the manager id or timesheet status id in the body cannot be found.")
	public ResponseEntity<String> post(@ApiParam(value = "Timesheet information for the new timesheet to be created.", required = true) @RequestBody TimesheetDTO timesheetDto) {
		
		if(timesheetDto.getEmployeeId() != null) {
			if(personService.findById(timesheetDto.getEmployeeId()).isPresent() == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalVars.EmployeeIdNotFound, "/timesheet");
			}
		}
		
		if(timesheetDto.getTimesheetStatusId() != null) {
			if(timesheetStatusService.getById(timesheetDto.getTimesheetStatusId()).isPresent() == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalVars.TimesheetStatusIdNotFound, "/timesheet");
			}
		}
		
		Timesheet timesheet = this.timesheetMapper.DTOtoTimesheet(timesheetDto, 0);

		this.timesheetService.postTimesheet(timesheet);

		return new ResponseEntity<String>("{\"id\": "+timesheet.getId()+"}", HttpStatus.OK);

	}

	@PutMapping("/timesheet")
	@ApiOperation(value = "Updates a timesheet in the system by their identifier.", 
					notes = "404 if the timesheet's identifier or any of the manager id and timesheet status id in the body cannot be found.")
	public ResponseEntity<String> put(@ApiParam("Timesheet information to be modified. There is no need to keep values that will not be modified.")@RequestBody TimesheetDTO timesheetDto,
										@ApiParam(value = "Id of the timesheet to be modified.", required = true) @RequestParam(value="id") int id) {
		
		if(this.timesheetService.getTimesheetById(id).isPresent()) {
			
			if(timesheetDto.getEmployeeId() != null) {
				if (personService.findById(timesheetDto.getEmployeeId()).isPresent() == false) {
					return GlobalFunctions.createNotFoundResponse(GlobalVars.EmployeeIdNotFound, "/timesheet");
				}
			}
			
			if(timesheetDto.getTimesheetStatusId() != null) {
				if (timesheetStatusService.getById(timesheetDto.getTimesheetStatusId()).isPresent() == false) {
					return GlobalFunctions.createNotFoundResponse(GlobalVars.TimesheetStatusIdNotFound, "/timesheet");
				}
			}
			
			Timesheet timesheet = this.timesheetMapper.DTOtoTimesheet(timesheetDto, id);
	
			this.timesheetService.postTimesheet(timesheet);
	
			return new ResponseEntity<String>(GlobalVars.TimesheetPutSuccessful, HttpStatus.OK);
		}
		else {
			return GlobalFunctions.createNotFoundResponse(GlobalVars.TimesheetIdNotFound, "/timesheet");
		}
	}
	
	@GetMapping("/timesheet/employee")
	@ApiOperation(value = "Returns a detailed list of all the person's information with all information of all timesheets with the specified startDate.", 
					response = PersonComplexWithManager.class, 
					notes = "404 if the person's identifier cannot be found")
	public ResponseEntity<?> findbyEmployeeId(@ApiParam(value = "Id of the employee to get the information from", required = true) @RequestParam(value="id") int id, 
												@ApiParam(value =  "Start date of the timesheets to get along with the person.", required = true) @RequestParam(value="startDate") Date startDate){
		
		Optional<Person> optionalEmployee = this.personService.findById(id);
		
		if(optionalEmployee.isPresent()) {
			PersonComplex personWithManager = new PersonComplexWithManager(); 
		    this.fromPersonToComplex(optionalEmployee.get(), personWithManager);
			this.addTimesheetsToPersonComplexByStartDate(personWithManager, startDate);
			PersonComplexWithManager personCompWithManager = (PersonComplexWithManager) personWithManager;
			personCompWithManager = this.addManagerToPersonComplexWithManager(personCompWithManager, optionalEmployee.get());
			
			return new ResponseEntity<PersonComplex>(personCompWithManager, HttpStatus.OK);
		}
		
		else {
			return GlobalFunctions.createNotFoundResponse(GlobalVars.PersonIdNotFound, "/timesheet/employee");
		}
	}

	@GetMapping("/timesheet/manager")
	@ApiOperation(value = "Returns a detailed list of all the persons managed by the person along with all the information of all timesheets with the specified startDate.", 
					response = PersonComplex.class, 
					notes = "404 if the manager's identifier cannot be found among the persons")
	public ResponseEntity<?> findbyManagerId(@ApiParam(value = "Id of the employee to get the information from.", required = true) @RequestParam(value="id") int id, 
												@ApiParam(value = "Start date of the timesheets to get along with the person's managed persons.", required = true) @RequestParam(value="startDate") Date startDate) {

		if(this.personService.findById(id).isPresent()) {
			List<Person> employeesOfManager = this.personService.findAllByManagerId(id);
			PersonComplex personWithTimesheets = new PersonComplex();
			List<PersonComplex> listOfPeople = new ArrayList<PersonComplex>();
	
			for(Person person : employeesOfManager) {
	
				this.fromPersonToComplex(person, personWithTimesheets);
				
				this.addTimesheetsToPersonComplexByStartDate(personWithTimesheets, startDate);
	
				listOfPeople.add(personWithTimesheets);
	
			}
			return new ResponseEntity<List<PersonComplex>>(listOfPeople, HttpStatus.OK);
		}
		else {
			return GlobalFunctions.createNotFoundResponse(GlobalVars.PersonIdNotFound, "/timesheet/manager");
		}
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
			timesheetComplex.setTimesheetStatus(timesheetStatusService.getById(timesheet.getTimesheetStatusId()).get());

		}
		return timesheetComplex;
	}
	
	private void fromPersonToComplex(Person person, PersonComplex personComplex) {
		personComplex.setId(person.getId());
		personComplex.setLastName(person.getLastName());
		personComplex.setFirstName(person.getFirstName());
		personComplex.setAddress(person.getAddress());
		personComplex.setEmailAddress(person.getEmail());
		personComplex.setPassword(person.getPassword());
		personComplex.setDateOfBirth(person.getDateOfBirth());

		if(person.getRoleId()!= null) {
			personComplex.setRole(this.roleService.getById(person.getRoleId()).get());
		}

		if(person.getDepartementId() != null) {
			personComplex.setDepartement(this.departementService.getById(person.getDepartementId()).get());
		}

		//return personComplex;

	}
	
	private void addTimesheetsToPersonComplexByStartDate(PersonComplex person, Date startDate) {
		List<Timesheet> timesheetsPerson = timesheetService.getTimesheetByEmployeeIdAndStartDate(person.getId(), startDate);
		
		for (Timesheet timesheet : timesheetsPerson) {
			TimesheetComplex timesheetComplex = this.fromTimesheetToComplex(timesheet);
			person.addToTimesheets(timesheetComplex);
		}
		//return person;
	}
	
	public PersonComplexWithManager addManagerToPersonComplexWithManager(PersonComplexWithManager personComplex, Person person) {
		personComplex.setManager(this.personService.findById(person.getManagerId()).get());
		return personComplex;

	}
}
