package com.example.Timesheet.com.controller;

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

import com.example.Timesheet.com.GlobalFunctions;
import com.example.Timesheet.com.GlobalMessages;
import com.example.Timesheet.com.dto.DepartmentDto;
import com.example.Timesheet.com.mapper.DepartementMapper;
import com.example.Timesheet.com.model.Department;
import com.example.Timesheet.com.service.DepartementService;
import com.example.Timesheet.com.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "DepartmentController")
public class DepartmentController {
	
	@Autowired
	DepartementService departementService = new DepartementService();
	
	@Autowired
	DepartementMapper departementMapper = new DepartementMapper();
	
	@Autowired
	EmployeeService personService = new EmployeeService();
	
	@GetMapping("/departement")
	@ApiOperation("Returns a list of all departments in the system.")
	public List<Department> getAll(){
		return departementService.getAll();
	}
	
	@PostMapping("/departement")
	@ApiOperation("Create a new department in the system.")
	public ResponseEntity<String> create(@ApiParam(value = "Department information for the new department to be created.", required = true)@RequestBody DepartmentDto departementDto) {
		Department department = departementMapper.DTOtoDepartement(departementDto, 0);
		
		departementService.save(department);
		
		return GlobalFunctions.createOkResponseFromObject(department);
	}
	
	@PutMapping("/departement")
	@ApiOperation(value = "Updates a department in the system by their identifier.", notes = "404 if the department's identifier is not found")
	public ResponseEntity<?> edit(@ApiParam("department information to be modified")@RequestBody DepartmentDto departementDTO,
										@ApiParam(value = "Id of the department to be modified. Cannot be null", required = true)@RequestParam(value="id") int id){
		
		if(this.departementService.getById(id).isPresent()) {
		
				Department department = departementMapper.DTOtoDepartement(departementDTO, id);
				departementService.save(department);
				return GlobalFunctions.createOkResponseFromObject(department);
		}
		else {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.DepartementIdNotFound, "/departement");
		}
		
	}
	
	@DeleteMapping("/departement")
	@ApiOperation(value = "Deletes a department from the system.", notes = "404 if the department's identifier cannot be found")
	public ResponseEntity<?> delete(@ApiParam(value = "Id of the department to be deleted. Cannot be null.", required = true)@RequestParam(value="id") int id){
		
		Optional<Department> optionalDepartment = this.departementService.getById(id);
		
		if(optionalDepartment.isPresent()) {
		
			Department department = optionalDepartment.get();
			
			if(this.personService.getAllByDepartementId(id).size() > 0) {
				return GlobalFunctions.createBadRequest(GlobalMessages.EmployeeUsesDepartementCannotDelete, "/departement");
			}
			
			this.departementService.delete(department);
			return GlobalFunctions.createOkResponseFromObject(department);
		}
		else {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.DepartementIdNotFound, "/departement");
		}
	}

}
