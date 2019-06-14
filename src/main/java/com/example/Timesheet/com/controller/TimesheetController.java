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
import com.example.Timesheet.com.dto.TimesheetComplexWithEmployee;
import com.example.Timesheet.com.dto.EmployeeComplex;
import com.example.Timesheet.com.GlobalFunctions;
import com.example.Timesheet.com.GlobalMessages;
import com.example.Timesheet.com.Paths;
import com.example.Timesheet.com.dto.TimesheetDto;
import com.example.Timesheet.com.dto.TimesheetRowWithProject;
import com.example.Timesheet.com.mapper.EmployeeMapper;
import com.example.Timesheet.com.mapper.TimesheetMapper;
import com.example.Timesheet.com.mapper.TimesheetRowMapper;
import com.example.Timesheet.com.model.Employee;
import com.example.Timesheet.com.model.Project;
import com.example.Timesheet.com.model.TimesheetRowProject;
import com.example.Timesheet.com.model.Timesheet;
import com.example.Timesheet.com.model.TimesheetRow;
import com.example.Timesheet.com.service.EmployeeService;
import com.example.Timesheet.com.service.TimesheetRowProjectService;
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
	private TimesheetRowProjectService timesheetRowProjectService;
	
	@GetMapping(Paths.TimesheetBasicPath)
	@ApiOperation("Returns a list of all timesheets in the system.")
	public List<Timesheet> getAll(){

		List<Timesheet> timesheets = timesheetService.getAll();
		
		return timesheets;

	}

	@GetMapping(Paths.TimesheetGetOne)
	@ApiOperation(value = "Returns the timesheet with the specified identifier.", 
					notes = "404 if the timesheet's identifier cannot be found.")
	public ResponseEntity<?> getOne(@ApiParam(value = "Id of the timesheet to retrieve", required = true) @RequestParam(value="id") int id) throws Exception{

		Optional<Timesheet> optionalTimesheet = timesheetService.getById(id);
		
		if(optionalTimesheet.isPresent()) {
			return GlobalFunctions.createOkResponseFromObject(optionalTimesheet.get());
		}
		else {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetIdNotFound, Paths.TimesheetGetOne);
		}
	}

	@PostMapping(Paths.TimesheetBasicPath)
	@ApiOperation(value = "Creates a new timesheet in the system.", 
					notes = "404 if the manager id or timesheet status id in the body cannot be found.")
	public ResponseEntity<String> addNew(@ApiParam(value = "Timesheet information for the new timesheet to be created.", required = true) @RequestBody TimesheetDto timesheetDto) {
		
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
		
		Timesheet timesheet = this.timesheetMapper.DTOtoTimesheet(timesheetDto, 0);

		timesheet = timesheetService.save(timesheet);

		return GlobalFunctions.createOkResponseFromObject(timesheet);

	}

	@PutMapping(Paths.TimesheetBasicPath)
	@ApiOperation(value = "Updates a timesheet in the system by their identifier.", 
					notes = "404 if the timesheet's identifier or any of the manager id and timesheet status id in the body cannot be found.")
	public ResponseEntity<String> edit(@ApiParam("Timesheet information to be modified. There is no need to keep values that will not be modified.")@RequestBody TimesheetDto timesheetDto,
										@ApiParam(value = "Id of the timesheet to be modified.", required = true) @RequestParam(value="id") int id) {
		
		if(this.timesheetService.getById(id).isPresent()) {
			
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
			
			Timesheet timesheet = this.timesheetMapper.DTOtoTimesheet(timesheetDto, id);
	
			timesheet = timesheetService.save(timesheet);
	
			return GlobalFunctions.createOkResponseFromObject(timesheet);
		}
		else {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetIdNotFound, Paths.TimesheetBasicPath);
		}
	}
	
	@DeleteMapping(Paths.TimesheetBasicPath)
	@ApiOperation(value = "Deletes a timesheet in the system by their identifier.", notes = "404 if the timeseet's identifier cannot be found. 400 if it is still referenced by a timesheetRow")
	public ResponseEntity<String> delete(@ApiParam(value = "Id of the timesheet to be deleted", required = true) @RequestParam(value = "id") int id) {
		
		Optional<Timesheet> optionalTimesheet = timesheetService.getById(id);
		
		if (optionalTimesheet.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetIdNotFound, Paths.TimesheetBasicPath);
		}
		
		Timesheet timesheet = optionalTimesheet.get();
		
		if(timesheetRowService.getByTimesheetId(id).size() > 0) {
			return GlobalFunctions.createBadRequest(GlobalMessages.TimesheetRowUsesTimesheetCannotDelete, Paths.TimesheetBasicPath);
		}
		
		timesheetService.delete(timesheet);
		return GlobalFunctions.createOkResponseFromObject(timesheet);
	}
	
	@GetMapping(Paths.TimesheetGetByEmployeeAndStartDate)
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
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, Paths.TimesheetGetByEmployeeAndStartDate);
		}
	}

	@GetMapping(Paths.TimesheetGetByEmployeeManagerAndStartDate)
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
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, Paths.TimesheetGetByEmployeeManagerAndStartDate);
		}
	}
	
	//functions that are used only by the frontend
	
	@GetMapping(Paths.TimesheetGetAllFromEmployee)
	@ApiOperation(value = "Returns a list of all the timesheets of an employee.",  
					notes = "404 if the employee's identifier cannot be found")
	public ResponseEntity<?> getAllTimesheetFromEmployee (@ApiParam(value = "Id of the employee to list all timesheets from", required = true) @RequestParam(value = "id") int id) {
		
		if (employeeService.getById(id).isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, Paths.TimesheetGetAllFromEmployee);
		}
		
		return GlobalFunctions.createOkResponseFromObject(timesheetService.getTimesheetByEmployeeId(id));
	}
	
	@GetMapping(Paths.TimesheetGetAllComplexFromEmployee)
	@ApiOperation(value = "Returns a list of all the timesheets of an employee.",  
					notes = "404 if the employee's identifier cannot be found")
	public ResponseEntity<?> getAllTimesheetComplexFromEmployee (@ApiParam(value = "Id of the employee to list all timesheets from", required = true) @RequestParam(value = "id") int id) {
		
		if (employeeService.getById(id).isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, Paths.TimesheetGetAllComplexFromEmployee);
		}
		
		List<Timesheet> employeeTimesheets = timesheetService.getTimesheetByEmployeeId(id);
		List<TimesheetComplexWithEmployee> timesheets = new ArrayList<TimesheetComplexWithEmployee>();
		
		for(Timesheet timesheet : employeeTimesheets) {
			TimesheetComplexWithEmployee timesheetWithEmployee = new TimesheetComplexWithEmployee(); 
			timesheetMapper.fromTimesheetToComplex(timesheet, timesheetWithEmployee);
			
			TimesheetComplexWithEmployee timesheetComplexWithEmployee = (TimesheetComplexWithEmployee) timesheetWithEmployee;
			timesheetComplexWithEmployee = timesheetMapper.addEmployeeToTimesheetComplexWiithManager(timesheetComplexWithEmployee, timesheet);
			
			timesheets.add(timesheetComplexWithEmployee);
		}
		
		return GlobalFunctions.createOkResponseFromObject(timesheets);
	}
	
	@GetMapping(Paths.TimesheetGetAllToVerify)
	@ApiOperation(value = "Returns a list of all the timesheets of the employees managed by someone that are waiting for approuval.",  
					notes = "404 if the employee's identifier cannot be found")
	public ResponseEntity<String> getAllTimesheetToApprouve(@ApiParam(value = "Id of the manager to list the timesheets from", required = true) @RequestParam(value = "id") int id) {
		
		if(employeeService.employeeExists(id) == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, Paths.TimesheetGetAllToVerify);
		}
		
		List<Employee> managedEmployees = employeeService.getAllByManagerId(id);
		
		List<TimesheetComplexWithEmployee> timesheetToVerify = new ArrayList<TimesheetComplexWithEmployee>();
		
		for (Employee employee : managedEmployees) {
		
			List<Timesheet> employeeTimesheets = timesheetService.getAwaitingApprovalByEmployeeId(employee.getId());
			
			for(Timesheet timesheet : employeeTimesheets) {
				TimesheetComplexWithEmployee timesheetWithEmployee = new TimesheetComplexWithEmployee(); 
				timesheetMapper.fromTimesheetToComplex(timesheet, timesheetWithEmployee);
				
				TimesheetComplexWithEmployee timesheetComplexWithEmployee = (TimesheetComplexWithEmployee) timesheetWithEmployee;
				timesheetComplexWithEmployee = timesheetMapper.addEmployeeToTimesheetComplexWiithManager(timesheetComplexWithEmployee, timesheet);
				
				timesheetToVerify.add(timesheetComplexWithEmployee);
			}
		}
		
		return GlobalFunctions.createOkResponseFromObject(timesheetToVerify);
	}
	
	@GetMapping(Paths.TimesheetGetAllProjects)
	@ApiOperation(value = "Returns a list of all the projects in the timesheetRowProjects in the timesheet",
					notes = "404 if the timesheet's identifier cannot be found.")
	public ResponseEntity<String> getTimesheetProjects (@ApiParam(value = "Id of the timesheet to list the projects from", required = true) @RequestParam(value = "id") int id) {
		
		if(timesheetService.timesheetExists(id) == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetIdNotFound, Paths.TimesheetGetAllProjects);
		}
		
		List<Project> projects = timesheetService.getAllProjectsOnTimesheet(id);
		
		return GlobalFunctions.createOkResponseFromObject(projects);
	}
	
	@PostMapping(Paths.TimesheetCreateWithRows)
	@ApiOperation(value = "Creates a new timesheet in the system with 7 timesheet rows (one for each day).", 
	notes = "404 if the id of the employee passed in the body cannot be found.")
	public ResponseEntity<?> createTimesheetWithRows (@ApiParam(value = "Employee to who the timesheet belongs", required = true) @RequestBody Employee employee, 
														@ApiParam(value = "Date that the timesheet is using. Can be any day of the week", required = true) @RequestParam(value = "date") Date date) {
		
		Integer id = employee.getId();
		
		if (id == null || employeeService.getById(id).isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.EmployeeIdNotFound, Paths.TimesheetCreateWithRows);
		}
		
		Timesheet timesheet = timesheetService.createTimesheetFromDateAndEmployeeId(id, date);
		timesheet.compensateTimezoneOnDates();
		
		timesheet = timesheetService.save(timesheet);
		
		timesheetRowService.createWeekFromTimesheet(timesheet);
		
		return GlobalFunctions.createOkResponseFromObject(employee);
	}
	
	@PutMapping(Paths.TimesheetEditWithTimesheetComplex)
	@ApiOperation(value = "Updates a timesheet in the system by their identifier and its timesheet rows.", 
					notes = "404 if the timesheet's identifier cannot be found. <br>"
							+ "400 if a timeProject does not have an id parameter.")
	public ResponseEntity<String> editTimesheetWithTimesheetComplex(@ApiParam(value = "Object containing the timesheet info and its timesheet rows", required = true) @RequestBody TimesheetComplexWithEmployee timesheetComplex) {
		
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
			
			TimesheetRow row = timesheetRowMapper.timesheetRowTimeProjectToTimesheetRow(rowTimeProject);
			row.setTimesheetId(id);
			
			row.compensateTimezoneOnDates();
			
			Optional<TimesheetRow> optionalRow = timesheetRowService.getById(row.getId());
			
			if(optionalRow.isPresent()) {
				row.setVersion(optionalRow.get().fetchVersion());
			}
			
			row = timesheetRowService.save(row);
			
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
					
					timesheetRowProject.setVersion(optionalTimesheetRowProject.get().fetchVersion());
				}
				
				if(timesheetRowProject.getValue() != null && timesheetRowProject.getValue() > 0) {
					timesheetRowProject.setTimesheetRowId(row.getId());
					timesheetRowProjectService.saveIncomplete(timesheetRowProject);
					
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
		
		Timesheet timesheet = timesheetMapper.fromComplexToTimesheet(timesheetComplex, dbTimesheet.getEmployeeId());
		
		timesheet.compensateTimezoneOnDates();
		timesheet.setVersion(dbTimesheet.fetchVersion());
		
		timesheetService.save(timesheet);
		
		timesheetComplex = (TimesheetComplexWithEmployee) timesheetMapper.fromTimesheetToComplex(timesheet, timesheetComplex);
		timesheetComplex = timesheetMapper.addEmployeeToTimesheetComplexWiithManager(timesheetComplex, timesheet);
		
		return GlobalFunctions.createOkResponseFromObject(timesheetComplex);
	}
	
	@DeleteMapping(Paths.TimesheetDeleteWithRows)
	@ApiOperation(value = "Deletes a timesheet in the system by their identifier along with all its timesheet rows", 
	notes = "404 if the timeseet's identifier cannot be found.")
	public ResponseEntity<?> deleteTimesheetWithRows(@ApiParam(value = "Id of the timesheet to be deleted", required = true) @RequestParam(value = "id") int id) {
		
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
