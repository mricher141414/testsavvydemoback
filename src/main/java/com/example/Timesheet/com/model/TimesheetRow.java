package com.example.Timesheet.com.model;

import java.sql.Date;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.Timesheet.com.GlobalVars;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description = "<p>Class representing a row of a timesheet tracked by the application.</p>")
public class TimesheetRow {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@ApiModelProperty(notes = "<p>Unique identifier of the row. No two rows can have the same id.</p>", example = "1", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "<p>Date (year-month-date) of the row.</p>", example = "2019-07-01", position = 1)
	private Date date;
	
	@ApiModelProperty(notes = "<p>Amount of hours logged in for the row. Use a dot (.) for decimals.</p>", example = "8.5", position = 2)
	private float value;
	
	@Column(name = "timesheet_id")
	@ApiModelProperty(notes = "<p>Unique identifier of the timesheet that the row is currently in. No two timesheets can have the same id.</p>", example = "1", position = 3)
	private Integer timesheetId;
	
	@Column(name = "project_id")
	@ApiModelProperty(notes = "<p>Unique identifier of the project the person has worked on for that row. No two projects can have the same id.</p>", example = "4", position = 4)
	private Integer projectId;
	
	public void compensateTimezoneOnDates() {
		Date date = this.getDate();
		
		if(date == null) {
			return;
		}
        
        TimeZone timeZone = TimeZone.getTimeZone(GlobalVars.Timezone);
        int offset = timeZone.getOffset(date.getTime());
        
        offset = timeZone.getOffset(date.getTime());
        date.setTime(date.getTime() - offset);
        
        this.setDate(date);
	}
	
	//getters and setters
	
	public float getValue() {
		return this.value;
	}
	
	public void setValue(Float value) {
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
