package com.example.Timesheet.com.dto;

import com.example.Timesheet.com.model.Person;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "<p>Class received by the user by the endpoint timesheet/employee. Is a child of the PersonComplex class, but with a manager added.")
public class PersonComplexWithManager extends PersonComplex {
	
	@ApiModelProperty(notes = "Full obejct of the person who manages the current person", position = 10)
	private Person manager;

	//getters and setters
	
	public Person getManager() {
		return manager;
	}

	public void setManager(Person manager) {
		this.manager = manager;
	}
	
	
}
