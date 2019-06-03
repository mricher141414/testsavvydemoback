package com.example.Timesheet.com.model;

import java.sql.Date;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import com.example.Timesheet.com.GlobalVars;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description = "<p>Class representing a row of a timesheet tracked by the application.</p>")
public class TimesheetRow {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@ApiModelProperty(notes = "<p>Unique identifier of the row. No two rows can have the same id.</p>", example = "1", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "<p>Date (year-month-date) of the row.</p>", example = "2019-07-01", position = 1)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date date;
	
	@Column(name = "timesheet_id")
	@ApiModelProperty(notes = "<p>Unique identifier of the timesheet that the row is currently in. No two timesheets can have the same id.</p>", example = "1", position = 2)
	private Integer timesheetId;
	
	@Version
	private Integer version;
	
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
	public void setVersion(int version) {
		this.version = version;
	}
}
