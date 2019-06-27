package com.example.Timesheet.com.dto;

import java.io.Serializable;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "<p>Class sent by the user to modify an existing timesheet or create a new one. <br>"
		+ "No property is required when modifying a timesheet. <br>"
		+ "All properties except id are recommanded when creating a new timesheet.</p>")
public class TimesheetDto implements Serializable {

	private static final long serialVersionUID = 1518740759924270565L;

	@ApiModelProperty(notes = "<p>Unique identifier of the timesheet. No two timesheets can have the same id. <br>"
			+ "The id is not required, but it is part of the object to facilitate a copy and paste from an existing object.</p>", example = "1", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "<p>Total amount of hours of the timesheet. Use a dot (.) for decimals. <br>"
			+ "Defaults to 0 if no value was given at creation.</p>", example = "40.5", position = 1)
	private Float total;
	
	@ApiModelProperty(notes = "<p>Notes of the timesheet.</p>", example = "Première timesheet", position = 2)
	private String notes;
	
	@ApiModelProperty(notes = "<p>Date (year-month-date) at which the timesheet started.</p>", example = "2019-06-30", position = 3)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date startDate;
	
	@ApiModelProperty(notes = "<p>Date (year-month-date) at which the timesheet started.</p>", example = "2019-07-06", position = 4)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date endDate;
	
	@ApiModelProperty(notes = "<p>Unique identifier of the person the timesheet is referencing. <br>"
			+ "Will cause an error if the person id specified does not belong to an existing person.</p>", example = "1", position = 5)
	private Integer employeeId;
	
	@ApiModelProperty(notes = "<p>Unique identifier of the timesheet status. <br>"
			+ "Will cause an error if the timesheet status id specified does not belong to an existing timesheet status.</p>", example = "1", position = 6)
	private Integer timesheetStatusId;
	 
	//getters and setters
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Float getTotal() {
		return total;
	}
	public void setTotal(Float total) {
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
