package com.example.Timesheet.com.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "<p>Class sent by the user to create a new timesheet status.</p>")

public class TimesheetStatusDto implements Serializable {
	
	private static final long serialVersionUID = 6769234931211432691L;

	@ApiModelProperty(notes = "<p>Unique identifier of the status. No two statuses can have the same id. <br>"
			+ "The id is not required, but it is part of the object to facilitate a copy and paste from an existing object.</p>", example = "1", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "<p>Name of the status. <br>"
			+ "The name is required as it is the only property that can be modified.</p>", example = "Brouillon", position = 1)
	private String name;
	
	@ApiModelProperty(notes = "<p>Property to make sure the person modifying the client has the current version", position = 100)
	private Integer version;
	
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
}
