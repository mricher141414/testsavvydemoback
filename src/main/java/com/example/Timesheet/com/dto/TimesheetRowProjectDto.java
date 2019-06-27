package com.example.Timesheet.com.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "<p>Class sent by the user to modify an existing timeProject.<br>"
		+ "No property is required when modifying a timeProject.</p>")
public class TimesheetRowProjectDto implements Serializable {
	
	private static final long serialVersionUID = 8071544491291250179L;

	@ApiModelProperty(notes = "<p>Unique identifier of the timeProject. No two timeProjects can have the same id. <br>"
			+ "The id is not required, but it is part of the object to facilitate a copy and paste from an existing object.</p>", example = "1", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "<p>Unique identifier of the project that the employee worked on. No two projects can have the same id. <br>"
			+ "Can be null.</p>", example = "3", position = 1)
	private Integer projectId;
	
	@ApiModelProperty(notes = "<p>Amount of hours the employee worked on the project. Use a dot (.) for decimals", example = "4.5", position = 2)
	private Float value;
	
	@ApiModelProperty(notes = "<p>Unique identifier of the timesheet row that the timeProject is currently in. No two timesheets can have the same id.</p>", example = "4", position = 3)
	private Integer timesheetRowId;

	//getters and setters
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public Integer getTimesheetRowId() {
		return timesheetRowId;
	}

	public void setTimesheetRowId(Integer timesheetRowId) {
		this.timesheetRowId = timesheetRowId;
	}
	
	
}
