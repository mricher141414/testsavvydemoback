package com.example.Timesheet.com.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "<p>Class sent by the user to modify an existing client or create a new one.</p>")

public class ClientDto {
	
	@ApiModelProperty(notes = "<p>Unique identifier of the client. No two clients can have the same id. <br>"
			+ "The id is not required, but it is part of the object to facilitate a copy and paste from an existing object.</p>", example = "1", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "<p>Name of the client. <br>"
			+ "The name is required as it is the only property that can be modified.</p>", example = "Les entreprises Pères et fils Inc.", position = 1)
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
