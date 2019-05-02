package com.example.Timesheet.com.dto;

import java.sql.Date;

public class TimesheetRowDTO {

	private int id;
	private Date date;
	private Integer timesheetId;
	private Integer projectId;
	private float value;
	
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
