package com.example.Timesheet.com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Timesheet.com.dao.IDepartementDao;
import com.example.Timesheet.com.model.Department;

@Service
public class DepartementService {
	
	@Autowired
	private IDepartementDao departmentDao;
	
	public List<Department> getAll(){
		
		return (List<Department>) this.departmentDao.findAll();
		
	}
	
	public void save(Department department) {
		if(departmentDao.existsById(department.getId()) == false) {
			department.setVersion(0);
		}
		
		this.departmentDao.save(department);	
	}
	
	public void delete(Department department) {
		this.departmentDao.delete(department);	
	}
	
	public Optional<Department> getById(int id) {
		return this.departmentDao.findById(id);
	}

}
