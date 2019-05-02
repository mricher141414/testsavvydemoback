package com.example.Timesheet.com.model;

import java.sql.Date;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.Timesheet.com.GlobalVars;

@Entity
public class TimesheetRow {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private Date date;
	private float value;
	@Column(name = "timesheet_id")
	private Integer timesheetId;
	@Column(name = "project_id")
	private Integer projectId;
	
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
	
	public void compensateTimezoneOnDates() {
		Date date = this.getDate();
        
        TimeZone timeZone = TimeZone.getTimeZone(GlobalVars.Timezone);
        int offset = timeZone.getOffset(date.getTime());
        
        offset = timeZone.getOffset(date.getTime());
        date.setTime(date.getTime() - offset);
        
        this.setDate(date);
	}
	
}
