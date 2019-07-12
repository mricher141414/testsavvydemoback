package com.example.Timesheet.com.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.example.Timesheet.com.dto.DepartmentDto;
import com.example.Timesheet.com.mapper.DepartmentMapper;
import com.example.Timesheet.com.model.Department;
import com.example.Timesheet.com.service.DepartmentService;
import com.example.Timesheet.com.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "DepartmentController")
public class DepartmentController implements Serializable {
	
	private static final long serialVersionUID = -5630346546935719604L;
	private static final Logger log = LogManager.getLogger(DepartmentController.class);

	@Autowired
	DepartmentService departmentService = new DepartmentService();
	
	@Autowired
	DepartmentMapper departementMapper = new DepartmentMapper();
	
	@Autowired
	EmployeeService personService = new EmployeeService();
	
	@GetMapping(Paths.DepartmentBasicPath)
	@ApiOperation(value = "Returns a list of all departments in the system.",
					response = Department.class, responseContainer = "List")
	public List<Department> getAll(){
		log.debug("Entering getAll");
		return departmentService.getAll();
	}
	
	@GetMapping(Paths.DepartmentGetOne)
	@ApiOperation(value = "Returns the department with the specified identifier.", notes = "404 if the department's identifier cannot be found.",
					response = Department.class)
	public ResponseEntity<?> getOne(@ApiParam(value = "Id of the department to be found.", required = true) @RequestParam(value="id") int id) {
		log.debug("Entering getOne with id parameter of " + id);
		
		Optional<Department> optionalDepartment = departmentService.getById(id);
		
		if(optionalDepartment.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.DepartmentIdNotFound, Paths.DepartmentGetOne);
		}
		
		return GlobalFunctions.createOkResponseFromObject(optionalDepartment.get());
	}
	
	@PostMapping(Paths.DepartmentBasicPath)
	@ApiOperation(value = "Create a new department in the system.",
					response = Department.class)
	public ResponseEntity<String> create(@ApiParam(value = "Department information for the new department to be created.", required = true)@RequestBody DepartmentDto departementDto) {
		log.debug("Entering create");
		
		Department department = departementMapper.dtoToDepartment(departementDto, 0);
		
		department = departmentService.save(department);
		
		return GlobalFunctions.createOkResponseFromObject(department);
	}
	
	@PutMapping(Paths.DepartmentBasicPath)
	@ApiOperation(value = "Updates a department in the system by their identifier.", notes = "404 if the department's identifier is not found", 
					response = Department.class)
	public ResponseEntity<?> edit(@ApiParam("department information to be modified")@RequestBody DepartmentDto departementDTO,
										@ApiParam(value = "Id of the department to be modified. Cannot be null", required = true)@RequestParam(value="id") int id){
		log.debug("Entering edit with id parameter of " + id);
		
		if(departmentService.getById(id).isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.DepartmentIdNotFound, Paths.DepartmentBasicPath);
		}
		
		try {
			Department department = departementMapper.dtoToDepartment(departementDTO, id);
			departmentService.save(department);
			return GlobalFunctions.createOkResponseFromObject(department);
		}
		catch (OptimisticLockException e) {
			return GlobalFunctions.createConflictResponse(GlobalMessages.DepartmentNotUpToDate, Paths.DepartmentBasicPath);
		}
		
	}
	
	@DeleteMapping(Paths.DepartmentBasicPath)
	@ApiOperation(value = "Deletes a department from the system.", notes = "404 if the department's identifier cannot be found",
					response = Department.class)
	public ResponseEntity<?> delete(@ApiParam(value = "Id of the department to be deleted. Cannot be null.", required = true)@RequestParam(value="id") int id){
		log.debug("Entering delete with id parameter of " + id);
		
		Optional<Department> optionalDepartment = this.departmentService.getById(id);
		
		if(optionalDepartment.isPresent()) {
		
			Department department = optionalDepartment.get();
			
			if(this.personService.getByDepartementId(id).size() > 0) {
				return GlobalFunctions.createConflictResponse(GlobalMessages.EmployeeUsesDepartementCannotDelete, Paths.DepartmentBasicPath);
			}
			
			this.departmentService.delete(department);
			return GlobalFunctions.createOkResponseFromObject(department);
		}
		else {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.DepartmentIdNotFound, Paths.DepartmentBasicPath);
		}
	}

}
