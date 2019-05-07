package com.example.Timesheet.com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Timesheet.com.GlobalFunctions;
import com.example.Timesheet.com.GlobalVars;
import com.example.Timesheet.com.dto.DepartementDTO;
import com.example.Timesheet.com.mapper.DepartementMapper;
import com.example.Timesheet.com.model.Departement;
import com.example.Timesheet.com.service.DepartementService;
import com.example.Timesheet.com.service.PersonService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "DepartementController")
public class DepartementController {
	
	@Autowired
	DepartementService departementService = new DepartementService();
	
	@Autowired
	DepartementMapper departementMapper = new DepartementMapper();
	
	@Autowired
	PersonService personService = new PersonService();
	
	@GetMapping("/departement")
	@ApiOperation("Returns a list of all departments in the system.")
	public List<Departement> getAllDepartements(){
		return departementService.getDepartements();
		
	}
	
	@PutMapping("/departement")
	@ApiOperation(value = "Updates a department in the system by their identifier.", notes = "404 if the department's identifier is not found")
	public ResponseEntity<?> modifyDepartement(@ApiParam("department information to be modified")@RequestBody DepartementDTO departementDTO,
										@ApiParam(value = "Id of the department to be modified. Cannot be null", required = true)@RequestParam(value="id") int id){
		
		if(this.departementService.getById(id).isPresent()) {
		
			if(departementDTO.getName() != null && !departementDTO.getName().equals("")) { //Validation
				Departement departement = departementMapper.DTOtoDepartement(departementDTO, id);
				departementService.saveDepartement(departement);
				return new ResponseEntity<String>(GlobalVars.DepartementPutSuccessful, HttpStatus.OK);
				
			}else {
				return GlobalFunctions.createBadRequest(GlobalVars.NameIsEmpty, "/departement"); 
			}
		}
		else {
			return GlobalFunctions.createNotFoundResponse(GlobalVars.DepartementIdNotFound, "/departement");
		}
		
	}
	
	@DeleteMapping("/departement")
	@ApiOperation(value = "Deletes a department from the system.", notes = "404 if the department's identifier cannot be found")
	public ResponseEntity<?> deleteDepartement(@ApiParam("Id of the department to be deleted. Cannot be null.")@RequestParam(value="id") int id){
		
		Optional<Departement> optionalDepartement = this.departementService.getById(id);
		
		if(optionalDepartement.isPresent()) {
		
			Departement departement = optionalDepartement.get();
			
			if(this.personService.findAllByDepartementId(id).size() > 0) {
				return GlobalFunctions.createBadRequest(GlobalVars.EmployeeUsesDepartementCannotDelete, "/departement");
			}
			
			this.departementService.deleteDepartement(departement);
			return new ResponseEntity<String>(GlobalVars.DepartementDeleteSuccessful, HttpStatus.OK);
		}
		else {
			return GlobalFunctions.createNotFoundResponse(GlobalVars.DepartementIdNotFound, "/departement");
		}
	}

}
