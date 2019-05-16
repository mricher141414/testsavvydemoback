package com.example.Timesheet.com.dto;

import com.example.Timesheet.com.model.Employee;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "<p>Class received by the user by the endpoint timesheet/employee. Is a child of the EmployeeComplex class, but with a manager added.")
public class EmployeeComplexWithManager extends EmployeeComplex {
	
	@ApiModelProperty(notes = "Full object of the employee who manages the current employee", position = 11)
	private Employee manager;

	//getters and setters
	
	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}
	
	
}
