package com.example.Timesheet.com.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Timesheet.com.dto.EmployeeComplexWithManager;
import com.example.Timesheet.com.dto.TimesheetComplex;
import com.example.Timesheet.com.dto.EmployeeComplex;
import com.example.Timesheet.com.GlobalFunctions;
import com.example.Timesheet.com.GlobalMessages;
import com.example.Timesheet.com.dto.TimesheetDto;
import com.example.Timesheet.com.dto.TimesheetRowTimeProject;
import com.example.Timesheet.com.mapper.EmployeeMapper;
import com.example.Timesheet.com.mapper.TimesheetMapper;
import com.example.Timesheet.com.mapper.TimesheetRowMapper;
import com.example.Timesheet.com.model.Employee;
import com.example.Timesheet.com.model.TimeProject;
import com.example.Timesheet.com.model.Timesheet;
import com.example.Timesheet.com.model.TimesheetRow;
import com.example.Timesheet.com.service.EmployeeService;
import com.example.Timesheet.com.service.TimeProjectService;
import com.example.Timesheet.com.service.TimesheetService;
import com.example.Timesheet.com.service.TimesheetRowService;
import com.example.Timesheet.com.service.TimesheetStatusService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

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
	private EmployeeService employeeService = new EmployeeService();

	@Autowired
	private TimesheetStatusService timesheetStatusService = new TimesheetStatusService();

	@Autowired
	private EmployeeMapper personMapper = new EmployeeMapper();
	
	@Autowired
	private TimesheetRowService timesheetRowService = new TimesheetRowService();
	
	@Autowired
	private TimesheetRowMapper timesheetRowMapper;
	
	@Autowired
	private TimeProjectService timeProjectService;
	
	@GetMapping("/timesheet/all")
	@ApiOperation("Returns a list of all timesheets in the system.")
	public List<Timesheet> getAll(){

		List<Timesheet> timesheets = timesheetService.getAll();
		
		return timesheets;

	}

	@GetMapping("/timesheet")
	@ApiOperation(value = "Returns the timesheet with the specified identifier.", 
					notes = "404 if the timesheet's identifier cannot be found.")
	public ResponseEntity<?> getOne(@ApiParam(value = "Id of the timesheet to retrieve", required = true) @RequestParam(value="id") int id) throws Exception{

		Optional<Timesheet> optionalTimesheet = timesheetService.getById(id);
		
		if(optionalTimesheet.isPresent()) {
			return GlobalFunctions.createOkResponseFromObject(optionalTimesheet.get());
		}
		else {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetIdNotFound, "/timesheet");
		}
	}

	@PostMapping("/timesheet")
	@ApiOperation(value = "Creates a new timesheet in the system.", 
					notes = "404 if the manager id or timesheet status id in the body cannot be found.")
	public ResponseEntity<String> addNew(@ApiParam(value = "Timesheet information for the new timesheet to be created.", required = true) @RequestBody TimesheetDto timesheetDto) {
		
		if(timesheetDto.getEmployeeId() != null) {
			if(employeeService.getById(timesheetDto.getEmployeeId()).isPresent() == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdParameterNotFound, "/timesheet");
			}
		}
		
		if(timesheetDto.getTimesheetStatusId() != null) {
			if(timesheetStatusService.getById(timesheetDto.getTimesheetStatusId()).isPresent() == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetStatusIdNotFound, "/timesheet");
			}
		}
		
		Timesheet timesheet = this.timesheetMapper.DTOtoTimesheet(timesheetDto, 0);

		this.timesheetService.save(timesheet);

		return GlobalFunctions.createOkResponseFromObject(timesheet);

	}

	@PutMapping("/timesheet")
	@ApiOperation(value = "Updates a timesheet in the system by their identifier.", 
					notes = "404 if the timesheet's identifier or any of the manager id and timesheet status id in the body cannot be found.")
	public ResponseEntity<String> edit(@ApiParam("Timesheet information to be modified. There is no need to keep values that will not be modified.")@RequestBody TimesheetDto timesheetDto,
										@ApiParam(value = "Id of the timesheet to be modified.", required = true) @RequestParam(value="id") int id) {
		
		if(this.timesheetService.getById(id).isPresent()) {
			
			if(timesheetDto.getEmployeeId() != null) {
				if (employeeService.getById(timesheetDto.getEmployeeId()).isPresent() == false) {
					return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdParameterNotFound, "/timesheet");
				}
			}
			
			if(timesheetDto.getTimesheetStatusId() != null) {
				if (timesheetStatusService.getById(timesheetDto.getTimesheetStatusId()).isPresent() == false) {
					return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetStatusIdNotFound, "/timesheet");
				}
			}
			
			Timesheet timesheet = this.timesheetMapper.DTOtoTimesheet(timesheetDto, id);
	
			this.timesheetService.save(timesheet);
	
			return GlobalFunctions.createOkResponseFromObject(timesheet);
		}
		else {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetIdNotFound, "/timesheet");
		}
	}
	
	@DeleteMapping("/timesheet")
	@ApiOperation(value = "Deletes a timesheet in the system by their identifier.", notes = "404 if the timeseet's identifier cannot be found. 400 if it is still referenced by a timesheetRow")
	public ResponseEntity<String> delete(@ApiParam(value = "Id of the timesheet to be deleted", required = true) @RequestParam(value = "id") int id) {
		
		Optional<Timesheet> optionalTimesheet = timesheetService.getById(id);
		
		if (optionalTimesheet.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetIdNotFound, "/timesheet");
		}
		
		Timesheet timesheet = optionalTimesheet.get();
		
		if(timesheetRowService.getByTimesheetId(id).size() > 0) {
			return GlobalFunctions.createBadRequest(GlobalMessages.TimesheetRowUsesTimesheetCannotDelete, "/timesheet");
		}
		
		timesheetService.delete(timesheet);
		return GlobalFunctions.createOkResponseFromObject(timesheet);
	}
	
	@GetMapping("/timesheet/employee")
	@ApiOperation(value = "Returns a detailed list of all the person's information with all information of all timesheets with the specified startDate.", 
					response = EmployeeComplexWithManager.class, 
					notes = "404 if the employee's identifier cannot be found")
	public ResponseEntity<?> findByEmployeeId(@ApiParam(value = "Id of the employee to get the information from", required = true) @RequestParam(value="id") int id, 
												@ApiParam(value =  "Start date of the timesheets to get along with the person.", required = true) @RequestParam(value="startDate") Date startDate){
		
		Optional<Employee> optionalEmployee = this.employeeService.getById(id);
		
		if(optionalEmployee.isPresent()) {
			EmployeeComplex personWithManager = new EmployeeComplexWithManager(); 
		    personMapper.fromEmployeeToComplex(optionalEmployee.get(), personWithManager);
			personMapper.addTimesheetsToEmployeeComplexByStartDate(personWithManager, startDate);
			EmployeeComplexWithManager personCompWithManager = (EmployeeComplexWithManager) personWithManager;
			personCompWithManager = personMapper.addManagerToEmployeeComplexWithManager(personCompWithManager, optionalEmployee.get());
			
			return GlobalFunctions.createOkResponseFromObject(personCompWithManager);
		}
		
		else {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, "/timesheet/employee");
		}
	}

	@GetMapping("/timesheet/manager")
	@ApiOperation(value = "Returns a detailed list of all the persons managed by the person along with all the information of all timesheets with the specified startDate.", 
					response = EmployeeComplex.class, 
					notes = "404 if the manager's identifier cannot be found among the persons")
	public ResponseEntity<?> findByManagerId(@ApiParam(value = "Id of the employee to get the information from.", required = true) @RequestParam(value="id") int id, 
												@ApiParam(value = "Start date of the timesheets to get along with the person's managed persons.", required = true) @RequestParam(value="startDate") Date startDate) {

		if(this.employeeService.getById(id).isPresent()) {
			List<Employee> employeesOfManager = this.employeeService.getAllByManagerId(id);
			List<EmployeeComplex> listOfPeople = new ArrayList<EmployeeComplex>();
	
			for(Employee person : employeesOfManager) {
				
				EmployeeComplex personWithTimesheets = new EmployeeComplex();
				
				personMapper.fromEmployeeToComplex(person, personWithTimesheets);
				
				personMapper.addTimesheetsToEmployeeComplexByStartDate(personWithTimesheets, startDate);
	
				listOfPeople.add(personWithTimesheets);
	
			}
			return GlobalFunctions.createOkResponseFromObject(listOfPeople);
		}
		else {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, "/timesheet/manager");
		}
	}
	
	//functions that are used only by the frontend
	
	@GetMapping("/employeetimesheets")
	@ApiOperation(value = "Returns a list of all the timesheets of an employee.",  
					notes = "404 if the employee's identifier cannot be found")
	public ResponseEntity<?> getAllTimesheetFromEmployee (@ApiParam(value = "Id of the employee to list all timesheets from", required = true) @RequestParam(value = "id") int id) {
		
		if (employeeService.getById(id).isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, "/employeetimesheets");
		}
		
		return GlobalFunctions.createOkResponseFromObject(timesheetService.getTimesheetByEmployeeId(id));
	}
	
	
	@PostMapping("/createtimesheet")
	@ApiOperation(value = "Creates a new timesheet in the system with 7 timesheet rows (one for each day).", 
	notes = "404 if the id of the employee passed in the body cannot be found.")
	public ResponseEntity<?> createTimesheetWithRows (@ApiParam(value = "Employee to who the timesheet belongs", required = true) @RequestBody Employee employee, 
														@ApiParam(value = "Date that the timesheet is using. Can be any day of the week", required = true) @RequestParam(value = "date") Date date) {
		
		Integer id = employee.getId();
		
		if (id == null || employeeService.getById(id).isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, "/createTimesheet");
		}
		
		Timesheet timesheet = timesheetService.createTimesheetFromDateAndEmployeeId(id, date);
		timesheet.compensateTimezoneOnDates();
		
		timesheetService.save(timesheet);
		
		timesheetRowService.createWeekFromTimesheet(timesheet);
		
		return GlobalFunctions.createOkResponseFromObject(employee);
	}
	
	@PutMapping("/edittimesheet")
	@ApiOperation(value = "Updates a timesheet in the system by their identifier and its timesheet rows.", 
					notes = "404 if the timesheet's identifier cannot be found. <br>"
							+ "400 if a timeProject does not have an id parameter.")
	public ResponseEntity<String> editTimesheetWithRows(@ApiParam(value = "Object containing the timesheet info and its timesheet rows", required = true) @RequestBody TimesheetComplex timesheetComplex) {
		
		Integer id = 0;
		
		if(timesheetComplex.getId() != null) {
			id = timesheetComplex.getId();
		}
		
		Optional<Timesheet> optionalTimesheet = timesheetService.getById(id);
		
		if (optionalTimesheet.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetIdNotFound, "/edittimesheet");
		}
		
		Timesheet dbTimesheet = optionalTimesheet.get();
		
		List<TimesheetRowTimeProject> rowsTimeProject = timesheetComplex.getTimesheetRows();
		
		for(TimesheetRowTimeProject rowTimeProject : rowsTimeProject) {
			
			TimesheetRow row = timesheetRowMapper.timesheetRowTimeProjectToTimesheetRow(rowTimeProject);
			row.setTimesheetId(id);
			
			row.compensateTimezoneOnDates();
			
			row = timesheetRowService.save(row);
			
			List<TimeProject> timeProjects = rowTimeProject.getTimeProjects();
			
			for(TimeProject timeProject : timeProjects) {
				
				if(timeProject.getId() == null) {
					return GlobalFunctions.createBadRequest(GlobalMessages.TimeProjectIdCannotBeNull, "/edittimesheet");
				}
				
				timeProject.setTimesheetRowId(row.getId());
				
				timeProjectService.saveIncomplete(timeProject);
			}
		}
		
		Timesheet timesheet = timesheetMapper.fromComplexToTimesheet(timesheetComplex, dbTimesheet.getEmployeeId());
		
		timesheet.compensateTimezoneOnDates();
		
		timesheetService.save(timesheet);
		
		return GlobalFunctions.createOkResponseFromObject(timesheetComplex);
	}
	
	@DeleteMapping("/deletetimesheet")
	@ApiOperation(value = "Deletes a timesheet in the system by their identifier along with all its timesheet rows", 
	notes = "404 if the timeseet's identifier cannot be found.")
	public ResponseEntity<?> deleteTimesheetWithRows(@ApiParam(value = "Id of the timesheet to be deleted", required = true) @RequestParam(value = "id") int id) {
		
		Optional<Timesheet> optionalTimesheet = timesheetService.getById(id);
		
		if(optionalTimesheet.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetIdNotFound, "/deletetimesheet");
		}
		
		List<TimesheetRow> timesheetRows = timesheetRowService.getByTimesheetId(id);
		
		for(TimesheetRow row : timesheetRows) {
			
			List<TimeProject> timeProjects = timeProjectService.getByTimesheetRowId(row.getId());
			
			for(TimeProject timeProject : timeProjects) {
				timeProjectService.delete(timeProject);
			}
			
			timesheetRowService.deleteTimesheetRow(row);
		}
		
		Timesheet timesheet = optionalTimesheet.get();
		
		timesheetService.delete(timesheet);
		return GlobalFunctions.createOkResponseFromObject(timesheet);
	}
}
