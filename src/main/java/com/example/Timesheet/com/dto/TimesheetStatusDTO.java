package com.example.Timesheet.com.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Class sent by the user to create a new timesheet status.")

public class TimesheetStatusDTO {
	
	@ApiModelProperty(notes = "Unique identifier of the status. No two statuses can have the same id. \n"
			+ "The id is not required, but it is part of the object to facilitate a copy and paste from an existing object", example = "1", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "Name of the status. \n"
			+ "The name is required as it is the only property that can be modified", example = "Brouillon", position = 1)
	private String name;

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
	
	
}
