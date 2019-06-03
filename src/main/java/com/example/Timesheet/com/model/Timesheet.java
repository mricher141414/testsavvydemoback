package com.example.Timesheet.com.model;

import java.sql.Date;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import com.example.Timesheet.com.GlobalMessages;
import com.example.Timesheet.com.GlobalVars;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description = "<p>Class representing a timesheet tracked by the application.</p>")
public class Timesheet {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@ApiModelProperty(notes = "<p>Unique identifier of the timesheet. No two timesheets can have the same id</p>", example = "1", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "<p>Total amount of hours of the timesheet. Uses a dot (.) for decimals.</p>", example = "40.5", position = 1)
	private Float total;
	
	@ApiModelProperty(notes = "<p>Notes of the timesheet.</p>", example = "Première timesheet", position = 2)
	private String notes;
	
	@ApiModelProperty(notes = "<p>Date (year-month-date) at which the timesheet started.</p>", example = "2019-06-29", position = 3)
	@Column(name = "start_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date startDate;
	
	@Column(name = "end_date")
	@ApiModelProperty(notes = "<p>Date (year-month-date) at which the timesheet ended.</p>", example = "2019-07-05", position = 4)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date endDate;
	
	@Column(name = "employee_id")
	@ApiModelProperty(notes = "<p>Unique identifier of the person the timesheet is referencing. No two persons can have the same id.</p>", example = "1", position = 5)
	private Integer employeeId;
	
	@Column(name = "timesheet_status_id")
	@ApiModelProperty(notes = "<p>Unique identifier of the timesheet status. No two timesheet statuses can have the same id.</p>", example = "1", position = 6)
	private Integer timesheetStatusId;
	
	@Version
	private Integer version;
	
	public void compensateTimezoneOnDates() {
		Date startDate = this.getStartDate();
		Date endDate = this.getEndDate();
		
		if(startDate == null || endDate == null) {
			return;
		}
        
        TimeZone timeZone = TimeZone.getTimeZone(GlobalVars.Timezone);
        int offset = timeZone.getOffset(startDate.getTime());
        startDate.setTime(startDate.getTime() - offset);
        
        offset = timeZone.getOffset(endDate.getTime());
        endDate.setTime(endDate.getTime() - offset);
        
        this.setStartDate(startDate);
        this.setEndDate(endDate);
	}
	
	//getters and setters
	
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public void setTimesheetStatusId(Integer timesheetStatusId) {
		this.timesheetStatusId = timesheetStatusId;
	}
	
	public int getId() {
		return id;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public Integer getTimesheetStatusId() {
		return timesheetStatusId;
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
	public void setVersion(int version) {
		this.version = version;
	}


}
