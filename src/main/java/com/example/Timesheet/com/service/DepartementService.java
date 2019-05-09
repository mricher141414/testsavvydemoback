package com.example.Timesheet.com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Timesheet.com.dao.IDepartementDAO;
import com.example.Timesheet.com.model.Departement;

@Service
public class DepartementService {
	
	@Autowired
	private IDepartementDAO departement;
	
	public List<Departement> getAll(){
		
		return (List<Departement>) this.departement.findAll();
		
	}
	
	public void save(Departement departement) {
		this.departement.save(departement);	
	}
	
	public void delete(Departement departement) {
		this.departement.delete(departement);	
	}
	
	public Optional<Departement> getById(int id) {
		return this.departement.findById(id);
	}

}
