package com.example.Timesheet.com.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Class sent by the user to modify an existing role.")
public class RoleDTO {
	@ApiModelProperty(notes = "Unique identifier of the role. No two roles can have the same id. \n"
			+ "The id is not required, but it is part of the object to facilitate a copy and paste from an existing object", example = "2", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "Name of the role. \n"
			+ "The role is required as it is the only property that can be modified", example = "Consultant", position = 1)
	private String name;
	
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

}
