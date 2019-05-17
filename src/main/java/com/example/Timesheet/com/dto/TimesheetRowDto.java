package com.example.Timesheet.com.dto;

import java.sql.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "<p>Class sent by the user to create a new timesheet row. <br>"
		+ "All properties except id are recommanded when creating a new timesheet row.</p>")
public class TimesheetRowDto {

	@ApiModelProperty(notes = "<p>Unique identifier of the timesheet. No two timesheets can have the same id. <br>"
			+ "The id is not required, but it is part of the object to facilitate a copy and paste from an existing object</p>", example = "1", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "<p>Date (year-month-date) of the row.</p>", example = "2019-07-01", position = 1)
	private Date date;
	
	@ApiModelProperty(notes = "<p>Unique identifier of the timesheet that the row is currently in. <br>"
			+ "Will cause an error if the timesheet id specified does not belong to an existing timesheet.</p>", example = "1", position = 3)
	private Integer timesheetId;
	
	@ApiModelProperty(notes = "<p>Unique identifier of the project the person has worked on for that row. <br>"
			+ "Will cause an error if the project id specified does not belong to an existing project.</p>", example = "4", position = 4)
	private Integer projectId;
	
	@ApiModelProperty(notes = "<p>Amount of hours logged in for the row. Use a dot (.) for decimals.</p>", example = "8.5", position = 2)
	private float value;
	
	//getters and setters
	
	public float getValue() {
		return this.value;
	}
	
	public void setValue(float value) {
		this.value = value;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getTimesheetId() {
		return timesheetId;
	}
	public void setTimesheetId(Integer timesheetId) {
		this.timesheetId = timesheetId;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	
}
