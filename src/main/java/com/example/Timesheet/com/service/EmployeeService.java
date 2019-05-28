package com.example.Timesheet.com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Timesheet.com.dao.IEmployeeDao;
import com.example.Timesheet.com.model.Employee;

@Service	
public class EmployeeService {
	@Autowired
	private IEmployeeDao employeeDao;

	public void saveEmployee(Employee employee) {
		if(employeeDao.existsById(employee.getId()) == false) {
			employee.setVersion(0);
		}
		
		this.employeeDao.save(employee);
		
	}
	
	public List<Employee> getAll(){
		
		return (List<Employee>) this.employeeDao.findAll();
		
	}
	
	public Optional<Employee> getById(int id ) {
		
		return this.employeeDao.findById(id);
		
	}
	
	public List<Employee> getAllByManagerId(int id){
		
		return this.employeeDao.findAllByManagerId(id);
		
	}
	
	public List<Employee> getAllByRoleId(int id) {
		return this.employeeDao.findAllByRoleId(id);
	}
	
	public List<Employee> getAllByDepartementId(int id) {
		return this.employeeDao.findAllByDepartmentId(id);
	}
	
	public void delete(Employee employee) {
		this.employeeDao.delete(employee);
	}
	
	public boolean employeeExists(int id) {
		return employeeDao.existsById(id);
	}
	
	public Float calculateAverageSalary(List<Employee> employees) {
		
		int employeesSize = employees.size();
		Float totalSalary = 0F;
		
		for (Employee employee : employees) {
			
			Float salary = employee.getSalary();
			
			if(salary != null) {
				totalSalary = totalSalary + salary;
			}
		}
		
		return totalSalary / employeesSize;
	}
}
