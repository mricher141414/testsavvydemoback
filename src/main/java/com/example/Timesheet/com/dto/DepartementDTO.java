package com.example.Timesheet.com.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "<p>Class sent by the user to modify an existing department.<br>"
		+ "No property is required when modifying a department.</p>")

public class DepartementDTO {
	
	@ApiModelProperty(notes = "<p>Unique identifier of the department. No two departments can have the same id. <br>"
			+ "The id is not required, but it is part of the object to facilitate a copy and paste from an existing object.</p>", example = "1", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "<p>Name of the departement. <br>", example = "Assurance qualité", position = 1)
	private String name;
	
	@ApiModelProperty(notes = "<p>Physical address of the department.</p>", example = "101 rue Abénaquis, suite 100", position = 2)
	private String address;
	
	@ApiModelProperty(notes = "<p>Description about the department</p>", example = "Département qui s'occupe de faire des tests", position = 3)
	private String description;
	
	//getters and setters
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
}
