package com.example.Timesheet.com.controller;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Timesheet.com.GlobalFunctions;
import com.example.Timesheet.com.GlobalMessages;
import com.example.Timesheet.com.Paths;
import com.example.Timesheet.com.dto.EmployeeComplex;
import com.example.Timesheet.com.dto.EmployeeComplexWithManager;
import com.example.Timesheet.com.dto.TimesheetComplexWithEmployee;
import com.example.Timesheet.com.dto.TimesheetDto;
import com.example.Timesheet.com.dto.TimesheetRowWithProject;
import com.example.Timesheet.com.mapper.EmployeeMapper;
import com.example.Timesheet.com.mapper.TimesheetMapper;
import com.example.Timesheet.com.mapper.TimesheetRowMapper;
import com.example.Timesheet.com.model.Employee;
import com.example.Timesheet.com.model.Project;
import com.example.Timesheet.com.model.Timesheet;
import com.example.Timesheet.com.model.TimesheetRow;
import com.example.Timesheet.com.model.TimesheetRowProject;
import com.example.Timesheet.com.service.EmployeeService;
import com.example.Timesheet.com.service.TimesheetRowProjectService;
import com.example.Timesheet.com.service.TimesheetRowService;
import com.example.Timesheet.com.service.TimesheetService;
import com.example.Timesheet.com.service.TimesheetStatusService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "TimesheetController")
public class TimesheetController implements Serializable {

	private static final long serialVersionUID = -4231651567869373747L;
	private static final Logger log = LogManager.getLogger(TimesheetController.class);

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
	private TimesheetRowProjectService timesheetRowProjectService;
	
	@GetMapping(Paths.TimesheetBasicPath)
	@ApiOperation(value = "Returns a list of all timesheets in the system.",
					response = Timesheet.class, responseContainer = "List")
	public List<Timesheet> getAll(){
		log.debug("Entering getAll");
		return timesheetService.getAll();
	}

	@GetMapping(Paths.TimesheetGetOne)
	@ApiOperation(value = "Returns the timesheet with the specified identifier.", notes = "404 if the timesheet's identifier cannot be found.", 
					response = Timesheet.class)
	public ResponseEntity<?> getOne(@ApiParam(value = "Id of the timesheet to retrieve", required = true) @RequestParam(value="id") int id) throws Exception{
		log.debug("Entering getOne with id parameter of " + id);
		
		Optional<Timesheet> optionalTimesheet = timesheetService.getById(id);
		
		if(optionalTimesheet.isPresent()) {
			return GlobalFunctions.createOkResponseFromObject(optionalTimesheet.get());
		}
		else {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetIdNotFound, Paths.TimesheetGetOne);
		}
	}

	@PostMapping(Paths.TimesheetBasicPath)
	@ApiOperation(value = "Creates a new timesheet in the system.", notes = "404 if the manager id or timesheet status id in the body cannot be found.",
					response = Timesheet.class)
	public ResponseEntity<String> create(@ApiParam(value = "Timesheet information for the new timesheet to be created.", required = true) @RequestBody TimesheetDto timesheetDto) {
		log.debug("Entering create");
		
		if(timesheetDto.getEmployeeId() != null) {
			if(employeeService.getById(timesheetDto.getEmployeeId()).isPresent() == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdParameterNotFound, Paths.TimesheetBasicPath);
			}
		}
		
		if(timesheetDto.getTimesheetStatusId() != null) {
			if(timesheetStatusService.getById(timesheetDto.getTimesheetStatusId()).isPresent() == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetStatusIdNotFound, Paths.TimesheetBasicPath);
			}
		}
		
		Timesheet timesheet = this.timesheetMapper.dtoToTimesheet(timesheetDto, 0);

		timesheet = timesheetService.save(timesheet);

		return GlobalFunctions.createOkResponseFromObject(timesheet);
	}

	@PutMapping(Paths.TimesheetBasicPath)
	@ApiOperation(value = "Updates a timesheet in the system by their identifier.", notes = "404 if the timesheet's identifier or any of the manager id and timesheet status id in the body cannot be found.",
					response = Timesheet.class)
	public ResponseEntity<String> edit(@ApiParam("Timesheet information to be modified. There is no need to keep values that will not be modified.")@RequestBody TimesheetDto timesheetDto,
										@ApiParam(value = "Id of the timesheet to be modified.", required = true) @RequestParam(value="id") int id) {
		log.debug("Entering edit with id parameter of " + id);
		
		if(this.timesheetService.timesheetExists(id) == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetIdNotFound, Paths.TimesheetBasicPath);
		}
			
		if(timesheetDto.getEmployeeId() != null) {
			if (employeeService.getById(timesheetDto.getEmployeeId()).isPresent() == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdParameterNotFound, Paths.TimesheetBasicPath);
			}
		}
		
		if(timesheetDto.getTimesheetStatusId() != null) {
			if (timesheetStatusService.getById(timesheetDto.getTimesheetStatusId()).isPresent() == false) {
				return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetStatusIdNotFound, Paths.TimesheetBasicPath);
			}
		}
		
		try {
			Timesheet timesheet = this.timesheetMapper.dtoToTimesheet(timesheetDto, id);

			timesheet = timesheetService.save(timesheet);

			return GlobalFunctions.createOkResponseFromObject(timesheet);
		}
		catch (OptimisticLockException e) {
			return GlobalFunctions.createConflictResponse(GlobalMessages.TimesheetNotUpToDate, Paths.TimesheetBasicPath);
		}		
	}
	
	@DeleteMapping(Paths.TimesheetBasicPath)
	@ApiOperation(value = "Deletes a timesheet in the system by their identifier.", notes = "404 if the timeseet's identifier cannot be found. 400 if it is still referenced by a timesheetRow",
					response = Timesheet.class)
	public ResponseEntity<String> delete(@ApiParam(value = "Id of the timesheet to be deleted", required = true) @RequestParam(value = "id") int id) {
		log.debug("Entering delete with id parameter of " + id);
		
		Optional<Timesheet> optionalTimesheet = timesheetService.getById(id);
		
		if(optionalTimesheet.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetIdNotFound, Paths.TimesheetBasicPath);
		}
		
		Timesheet timesheet = optionalTimesheet.get();
		
		if(timesheetRowService.getByTimesheetId(id).size() > 0) {
			return GlobalFunctions.createConflictResponse(GlobalMessages.TimesheetRowUsesTimesheetCannotDelete, Paths.TimesheetBasicPath);
		}
		
		timesheetService.delete(timesheet);
		return GlobalFunctions.createOkResponseFromObject(timesheet);
	}
	
	@GetMapping(Paths.TimesheetGetByEmployeeAndStartDate)
	@ApiOperation(value = "Returns a detailed list of all the person's information with all information of all timesheets with the specified startDate.", notes = "404 if the employee's identifier cannot be found",
					response = EmployeeComplexWithManager.class)
	public ResponseEntity<?> findByEmployeeId(@ApiParam(value = "Id of the employee to get the information from", required = true) @RequestParam(value="id") int id, 
												@ApiParam(value =  "Start date of the timesheets to get along with the person.", required = true) @RequestParam(value="startDate") Date startDate){
		log.debug("Entering findByEmployeeId with id parameter of " + id + " and startDate parameter of " + startDate);
		
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
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, Paths.TimesheetGetByEmployeeAndStartDate);
		}
	}

	@GetMapping(Paths.TimesheetGetByEmployeeManagerAndStartDate)
	@ApiOperation(value = "Returns a detailed list of all the persons managed by the person along with all the information of all timesheets with the specified startDate.", notes = "404 if the manager's identifier cannot be found among the persons",
					response = EmployeeComplexWithManager.class)
	public ResponseEntity<?> findByManagerId(@ApiParam(value = "Id of the employee to get the information from.", required = true) @RequestParam(value="id") int id, 
												@ApiParam(value = "Start date of the timesheets to get along with the person's managed persons.", required = true) @RequestParam(value="startDate") Date startDate) {
		log.debug("Entering findByManager with id parameter of " + id + " and startDate parameter of " + startDate);
		
		if(this.employeeService.employeeExists(id)) {
			List<Employee> employeesOfManager = this.employeeService.getByManagerId(id);
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
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, Paths.TimesheetGetByEmployeeManagerAndStartDate);
		}
	}
	
	//functions that are used only by the frontend
	
	@GetMapping(Paths.TimesheetGetAllFromEmployee)
	@ApiOperation(value = "Returns a list of all the timesheets of an employee.", notes = "404 if the employee's identifier cannot be found",
					response = Timesheet.class, responseContainer = "List")
	public ResponseEntity<?> getAllTimesheetFromEmployee (@ApiParam(value = "Id of the employee to list all timesheets from", required = true) @RequestParam(value = "id") int id) {
		log.debug("Entering getAllTimesheetFromEmployee with id parameter of " + id);
		
		if (employeeService.employeeExists(id) == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, Paths.TimesheetGetAllFromEmployee);
		}
		
		return GlobalFunctions.createOkResponseFromObject(timesheetService.getByEmployeeId(id));
	}
	
	@GetMapping(Paths.TimesheetGetAllComplexFromEmployee)
	@ApiOperation(value = "Returns a list of all the timesheets of an employee.", notes = "404 if the employee's identifier cannot be found",
					response = TimesheetComplexWithEmployee.class, responseContainer = "List")
	public ResponseEntity<?> getAllTimesheetComplexFromEmployee (@ApiParam(value = "Id of the employee to list all timesheets from", required = true) @RequestParam(value = "id") int id) {
		log.debug("Entering getAllTimesheetComplexFromEmployee with id parameter of " + id);
		
		if (employeeService.getById(id).isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, Paths.TimesheetGetAllComplexFromEmployee);
		}
		
		List<Timesheet> employeeTimesheets = timesheetService.getByEmployeeId(id);
		List<TimesheetComplexWithEmployee> timesheets = new ArrayList<TimesheetComplexWithEmployee>();
		
		for(Timesheet timesheet : employeeTimesheets) {
			TimesheetComplexWithEmployee timesheetWithEmployee = new TimesheetComplexWithEmployee(); 
			timesheetMapper.fromTimesheetToComplex(timesheet, timesheetWithEmployee);
			
			TimesheetComplexWithEmployee timesheetComplexWithEmployee = (TimesheetComplexWithEmployee) timesheetWithEmployee;
			timesheetComplexWithEmployee = timesheetMapper.addEmployeeToTimesheetComplex(timesheetComplexWithEmployee, timesheet);
			
			timesheets.add(timesheetComplexWithEmployee);
		}
		
		return GlobalFunctions.createOkResponseFromObject(timesheets);
	}
	
	@GetMapping(Paths.TimesheetGetAllToVerify)
	@ApiOperation(value = "Returns a list of all the timesheets of the employees managed by someone that are waiting for approuval.", notes = "404 if the employee's identifier cannot be found",
					response = Timesheet.class, responseContainer = "List")
	public ResponseEntity<String> getAllTimesheetsToApprove(@ApiParam(value = "Id of the manager to list the timesheets from", required = true) @RequestParam(value = "id") int id) {
		log.debug("Entering getAllTimesheetsToApprove with id parameter of " + id);
		
		if(employeeService.employeeExists(id) == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, Paths.TimesheetGetAllToVerify);
		}
		
		List<Employee> managedEmployees = employeeService.getByManagerId(id);
		
		List<TimesheetComplexWithEmployee> timesheetToVerify = new ArrayList<TimesheetComplexWithEmployee>();
		
		for (Employee employee : managedEmployees) {
		
			List<Timesheet> employeeTimesheets = timesheetService.getAwaitingApprovalByEmployeeId(employee.getId());
			
			for(Timesheet timesheet : employeeTimesheets) {
				TimesheetComplexWithEmployee timesheetWithEmployee = new TimesheetComplexWithEmployee(); 
				timesheetMapper.fromTimesheetToComplex(timesheet, timesheetWithEmployee);
				
				TimesheetComplexWithEmployee timesheetComplexWithEmployee = (TimesheetComplexWithEmployee) timesheetWithEmployee;
				timesheetComplexWithEmployee = timesheetMapper.addEmployeeToTimesheetComplex(timesheetComplexWithEmployee, timesheet);
				
				timesheetToVerify.add(timesheetComplexWithEmployee);
			}
		}
		
		return GlobalFunctions.createOkResponseFromObject(timesheetToVerify);
	}
	
	@GetMapping(Paths.TimesheetGetAllProjects)
	@ApiOperation(value = "Returns a list of all the projects in the timesheetRowProjects in the timesheet", notes = "404 if the timesheet's identifier cannot be found.",
					response = Project.class, responseContainer = "List")
	public ResponseEntity<String> getTimesheetProjects (@ApiParam(value = "Id of the timesheet to list the projects from", required = true) @RequestParam(value = "id") int id) {
		log.debug("Entering getTimesheetProjects with id parameter of " + id);
		
		if(timesheetService.timesheetExists(id) == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetIdNotFound, Paths.TimesheetGetAllProjects);
		}
		
		List<Project> projects = timesheetService.getProjectsOnTimesheet(id);
		
		return GlobalFunctions.createOkResponseFromObject(projects);
	}
	
	@GetMapping(Paths.TimesheetGetLastWeekComplex)
	@ApiOperation(value = "Returns all the timesheets in complex form that ended last Sunday",
					response = TimesheetComplexWithEmployee.class, responseContainer = "List")
	public ResponseEntity<String> getLastWeekTimesheet (@ApiParam(value = "Date for reference", required = true) @RequestParam(value = "date") Date date) {
		log.debug("Entering getLastWeekTimesheet with date parameter of " + date.toString());
		
		List<TimesheetComplexWithEmployee> timesheets = new ArrayList<TimesheetComplexWithEmployee>();
		
		List<Timesheet> lastWeekTimesheets = timesheetService.getLastWeekTimesheets(date);
		
		for (Timesheet timesheet: lastWeekTimesheets) {
			TimesheetComplexWithEmployee timesheetWithEmployee = new TimesheetComplexWithEmployee(); 
			timesheetMapper.fromTimesheetToComplex(timesheet, timesheetWithEmployee);
			
			TimesheetComplexWithEmployee timesheetComplexWithEmployee = (TimesheetComplexWithEmployee) timesheetWithEmployee;
			timesheetComplexWithEmployee = timesheetMapper.addEmployeeToTimesheetComplex(timesheetComplexWithEmployee, timesheet);
			
			timesheets.add(timesheetComplexWithEmployee);
		}
		
		return GlobalFunctions.createOkResponseFromObject(timesheets);
	}
	
	@PostMapping(Paths.TimesheetCreateWithRows)
	@ApiOperation(value = "Creates a new timesheet in the system with 7 timesheet rows (one for each day).", notes = "404 if the id of the employee passed in the body cannot be found.",
					response = Timesheet.class)
	public ResponseEntity<?> createTimesheetWithRows (@ApiParam(value = "Employee to who the timesheet belongs", required = true) @RequestBody Employee employee, 
														@ApiParam(value = "Date that the timesheet is using. Can be any day of the week", required = true) @RequestParam(value = "date") Date date) {
		Assert.notNull(date, "parameter date must not be null");
		log.debug("Entering createTimesheetWithRows with date parameter of " + date);
		
		Integer id = employee.getId();
		
		if (id == null || employeeService.getById(id).isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, Paths.TimesheetCreateWithRows);
		}
		
		Timesheet timesheet = timesheetService.createFromDateAndEmployeeId(id, date);
		timesheet.compensateTimezoneOnDates();
		
		timesheet = timesheetService.save(timesheet);
		
		timesheetRowService.createWeekFromTimesheet(timesheet);
		
		return GlobalFunctions.createOkResponseFromObject(timesheet);
	}
	
	@PutMapping(Paths.TimesheetEditWithTimesheetComplex)
	@ApiOperation(value = "Updates a timesheet in the system by their identifier and its timesheet rows.", notes = "404 if the timesheet's identifier cannot be found. <br>400 if a timesheetRowProject does not have an id parameter.",
					response = TimesheetComplexWithEmployee.class)
	public ResponseEntity<String> editTimesheetWithTimesheetComplex(@ApiParam(value = "Object containing the timesheet info and its timesheet rows", required = true) @RequestBody TimesheetComplexWithEmployee timesheetComplex) {
		log.debug("Entering editTimesheetWithTimesheetComplex");
		
		Integer id = 0;
		float totalHours = 0;
		
		if(timesheetComplex.getId() != null) {
			id = timesheetComplex.getId();
		}
		
		Optional<Timesheet> optionalTimesheet = timesheetService.getById(id);
		
		if (optionalTimesheet.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetIdNotFound, Paths.TimesheetEditWithTimesheetComplex);
		}
		
		Timesheet dbTimesheet = optionalTimesheet.get();
		
		List<TimesheetRowWithProject> rowsTimeProject = timesheetComplex.getTimesheetRows();
		
		for(TimesheetRowWithProject rowTimeProject : rowsTimeProject) {
			
			TimesheetRow row = new TimesheetRow();
			
			try {
				row = timesheetRowMapper.timesheetRowWithProjectToTimesheetRow(rowTimeProject);
				row.setTimesheetId(id);
				
				row.compensateTimezoneOnDates();
				
				row = timesheetRowService.save(row);
			}
			catch (OptimisticLockException e) {
				return GlobalFunctions.createConflictResponse(GlobalMessages.TimesheetRowNotUpToDateInTimesheet, Paths.TimesheetEditWithTimesheetComplex);
			}
			
			List<TimesheetRowProject> timesheetRowProjects = rowTimeProject.getTimesheetRowProjects();
			
			for(TimesheetRowProject timesheetRowProject : timesheetRowProjects) {
				
				if(timesheetRowProject.getId() == null) {
					return GlobalFunctions.createBadRequest(GlobalMessages.TimesheetRowProjectIdCannotBeNull, Paths.TimesheetEditWithTimesheetComplex);
				}
				
				if(timesheetRowProject.getId() != 0) {
					Optional<TimesheetRowProject> optionalTimesheetRowProject = timesheetRowProjectService.getById(timesheetRowProject.getId());
					
					if(optionalTimesheetRowProject.isPresent() == false) {
						return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetRowProjectIdNotFound, Paths.TimesheetEditWithTimesheetComplex);
					}
					
					timesheetRowProject.setVersion(optionalTimesheetRowProject.get().getVersion());
				}
				
				if(timesheetRowProject.getValue() != null && timesheetRowProject.getValue() > 0) {
					timesheetRowProject.setTimesheetRowId(row.getId());
					
					try {
						timesheetRowProjectService.saveIncomplete(timesheetRowProject);
					}
					catch (OptimisticLockException e) {
						return GlobalFunctions.createConflictResponse(GlobalMessages.TimesheetRowProjectNotUpToDateInTimesheet, Paths.TimesheetEditWithTimesheetComplex);
					}
					
					
					totalHours = totalHours + timesheetRowProject.getValue();
				}
				else {
					if(timesheetRowProject.getId() != 0) {
						timesheetRowProjectService.delete(timesheetRowProject);
					}
				}
			}
		}
		
		timesheetComplex.setTotal(totalHours);
		
		Timesheet timesheet = new Timesheet();
		
		try {
			timesheet = timesheetMapper.fromComplexToTimesheet(timesheetComplex, dbTimesheet.getEmployeeId());
			timesheet.compensateTimezoneOnDates();
			
			timesheetService.save(timesheet);
		}
		catch (OptimisticLockException e) {
			return GlobalFunctions.createConflictResponse(GlobalMessages.TimesheetNotUpToDate, Paths.TimesheetEditWithTimesheetComplex);
		}
				
		timesheetComplex = (TimesheetComplexWithEmployee) timesheetMapper.fromTimesheetToComplex(timesheet, timesheetComplex);
		timesheetComplex = timesheetMapper.addEmployeeToTimesheetComplex(timesheetComplex, timesheet);
		
		return GlobalFunctions.createOkResponseFromObject(timesheetComplex);
	}
	
	@DeleteMapping(Paths.TimesheetDeleteWithRows)
	@ApiOperation(value = "Deletes a timesheet in the system by their identifier along with all its timesheet rows", notes = "404 if the timeseet's identifier cannot be found.",
					response = Timesheet.class)
	public ResponseEntity<?> deleteTimesheetWithRows(@ApiParam(value = "Id of the timesheet to be deleted", required = true) @RequestParam(value = "id") int id) {
		log.debug("Entering deleteTimesheetWithRows with id parameter of " + id);
		
		Optional<Timesheet> optionalTimesheet = timesheetService.getById(id);
		
		if(optionalTimesheet.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetIdNotFound, Paths.TimesheetDeleteWithRows);
		}
		
		List<TimesheetRow> timesheetRows = timesheetRowService.getByTimesheetId(id);
		
		for(TimesheetRow row : timesheetRows) {
			
			List<TimesheetRowProject> timeProjects = timesheetRowProjectService.getByTimesheetRowId(row.getId());
			
			for(TimesheetRowProject timeProject : timeProjects) {
				timesheetRowProjectService.delete(timeProject);
			}
			
			timesheetRowService.deleteTimesheetRow(row);
		}
		
		Timesheet timesheet = optionalTimesheet.get();
		
		timesheetService.delete(timesheet);
		return GlobalFunctions.createOkResponseFromObject(timesheet);
	}
}
