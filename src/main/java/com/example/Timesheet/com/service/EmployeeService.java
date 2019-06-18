package com.example.Timesheet.com.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.Timesheet.com.dao.IEmployeeDao;
import com.example.Timesheet.com.model.Employee;

@Service	
public class EmployeeService implements Serializable {
	
	private static final long serialVersionUID = 7827013626627409969L;
	private static final Logger log = LogManager.getLogger(EmployeeService.class);

	@Autowired
	private IEmployeeDao employeeDao;
	
	@Autowired
	private SalaryReportService salaryReportService;

	public Employee saveEmployee(Employee employee) {
		Assert.notNull(employee, "Parameter employee must not be null");
		log.debug("Entering save");
		
		if(employeeDao.existsById(employee.getId()) == false) {
			employee.setVersion(0);
		}
		
		return employeeDao.save(employee);
		
	}
	
	public List<Employee> getAll(){
		log.debug("Entering getAll");
		return (List<Employee>) this.employeeDao.findAll();
	}
	
	public Optional<Employee> getById(int id) {
		log.debug("Entering getById with id parameter of " + id);
		return this.employeeDao.findById(id);
	}
	
	public List<Employee> getByManagerId(int id){
		log.debug("Entering getAllByManagerId with id parameter of " + id);
		return this.employeeDao.findAllByManagerId(id);
	}
	
	public List<Employee> getByRoleId(int id) {
		log.debug("Entering getAllByRoleId with id parameter of " + id);
		return this.employeeDao.findAllByRoleId(id);
	}
	
	public List<Employee> getByDepartementId(int id) {
		log.debug("Entering getAllByDepartmentId with id parameter of " + id);
		return this.employeeDao.findAllByDepartmentId(id);
	}
	
	public void delete(Employee employee) {
		Assert.notNull(employee, "Parameter employee must not be null");
		log.debug("Entering delete");
		
		salaryReportService.deleteByEmployeeId(employee.getId());
		this.employeeDao.delete(employee);
	}
	
	public boolean employeeExists(int id) {
		log.debug("Entering employeeExists with id parameter of " + id);
		return employeeDao.existsById(id);
	}
	
	public Float calculateAverageSalary(List<Employee> employees) {
		Assert.notNull(employees, "Parameter employees must not be null");
		log.debug("Entering calculateAverageSalary");		
		
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
