package com.example.Timesheet.com.dto;

import com.example.Timesheet.com.model.Employee;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "<p>Class received by the user by the endpoint timesheet/toverify. Is a child of the TimesheetComplex class, but with an employee added.")
public class TimesheetComplexWithEmployee extends TimesheetComplex {
	
	private static final long serialVersionUID = -7689125195277557533L;
	
	@ApiModelProperty(notes = "Full object of the employee that is the owner of the timesheet", position = 11)
	private Employee employee;

	//getters and setters
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
