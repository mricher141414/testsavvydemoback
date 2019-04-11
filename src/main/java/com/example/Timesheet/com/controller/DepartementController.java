package com.example.Timesheet.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Timesheet.com.dto.DepartementDTO;
import com.example.Timesheet.com.mapper.DepartementMapper;
import com.example.Timesheet.com.model.Departement;
import com.example.Timesheet.com.model.Role;
import com.example.Timesheet.com.service.DepartementService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class DepartementController {
	
	@Autowired
	DepartementService departementService = new DepartementService();
	DepartementMapper departementMapper = new DepartementMapper();
	
	@GetMapping("/departement")
	public List<Departement> findAllDepartements(){
		return departementService.getDepartements();
		
	}
	
	@PutMapping("/departement")
	public void saveRole(@RequestBody DepartementDTO departementDTO){
		
		if(!departementDTO.getName().equals("")) {
			Departement departement = departementMapper.DTOtoDepartement(departementDTO);
			departementService.saveDepartement(departement);
			
		}else {
			throw new NullPointerException("name is null"); 
		}
		
	}
	
	@DeleteMapping("/departement")
	public void deleteRole(@RequestBody DepartementDTO departementDTO){
		
		Departement departement = departementMapper.DTOtoDepartement(departementDTO);
		departementService.deleteDepartement(departement);
		
		
	}

}
