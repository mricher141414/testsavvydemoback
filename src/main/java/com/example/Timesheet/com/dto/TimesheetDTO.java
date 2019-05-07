package com.example.Timesheet.com.dto;

import java.sql.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Class sent by the user to modify an existing timesheet or create a new one. \n"
		+ "No property is required when modifying a timesheet. \n"
		+ "All properties except id are recommanded when creating a new timesheet")
public class TimesheetDTO {

	@ApiModelProperty(notes = "Unique identifier of the timesheet. No two timesheets can have the same id. \n"
			+ "The id is not required, but it is part of the object to facilitate a copy and paste from an existing object", example = "1", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "Total amount of hours of the timesheet. Use a dot (.) for decimals. \n"
			+ "Defaults to 0 if no value was given at creation", example = "40.5", position = 1)
	private float total;
	
	@ApiModelProperty(notes = "Notes of the timesheet.", example = "Première timesheet", position = 2)
	private String notes;
	
	@ApiModelProperty(notes = "Date (year-month-date) at which the timesheet started.", example = "2019-06-30", position = 3)
	private Date startDate;
	
	@ApiModelProperty(notes = "Date (year-month-date) at which the timesheet started.", example = "2019-07-06", position = 4)
	private Date endDate;
	
	@ApiModelProperty(notes = "Unique identifier of the person the timesheet is referencing. \n"
			+ "Will cause an error if the person id specified does not belong to an existing person", example = "1", position = 5)
	private Integer employeeId;
	
	@ApiModelProperty(notes = "Unique identifier of the timesheet status. \n"
			+ "Will cause an error if the timesheet status id specified does not belong to an existing timesheet status", example = "1", position = 6)
	private Integer timesheetStatusId;
	
	//getters and setters
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public void setTimesheetStatusId(Integer timesheetStatusId) {
		this.timesheetStatusId = timesheetStatusId;
	}
	public Integer getTimesheetStatusId() {
		return timesheetStatusId;
	}
	public void setTimesheetStatusId(int timesheetStatusId) {
		this.timesheetStatusId = timesheetStatusId;
	}

}
