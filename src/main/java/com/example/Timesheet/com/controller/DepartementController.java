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

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class DepartementController {
	
	@Autowired
	DepartementService departementService = new DepartementService();
	
	@Autowired
	DepartementMapper departementMapper = new DepartementMapper();
	
	@Autowired
	PersonService personService = new PersonService();
	
	@GetMapping("/departement")
	public List<Departement> findAllDepartements(){
		return departementService.getDepartements();
		
	}
	
	@PutMapping("/departement")
	public ResponseEntity<?> saveRole(@RequestBody DepartementDTO departementDTO, @RequestParam(value="id") int id){
		
		if(this.departementService.getById(id).isPresent()) {
		
			if(!departementDTO.getName().equals("")) { //Validation
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
	public ResponseEntity<?> deleteRole(@RequestParam(value="id") int id){
		
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
