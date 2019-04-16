package com.example.Timesheet.com.dto;

import java.sql.Date;

import com.example.Timesheet.com.model.Person;
import com.example.Timesheet.com.model.TimesheetStatus;

public class TimesheetComplex {
	
	private int id;
	private int total;
	private String notes;
	private Date startDate;
	private Date endDate;
	private Person employee;
	private TimesheetStatus timesheetStatus;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
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
	public Person getEmployee() {
		return employee;
	}
	public void setEmployee(Person employee) {
		this.employee = employee;
	}
	public TimesheetStatus getTimesheetStatus() {
		return timesheetStatus;
	}
	public void setTimesheetStatus(TimesheetStatus timesheetStatus) {
		this.timesheetStatus = timesheetStatus;
	}

}
