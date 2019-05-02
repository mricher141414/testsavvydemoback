package com.example.Timesheet.com.dto;

import java.sql.Date;
import java.util.List;

import com.example.Timesheet.com.model.Person;
import com.example.Timesheet.com.model.TimesheetRow;
import com.example.Timesheet.com.model.TimesheetStatus;

public class TimesheetComplex {
	
	private int id;
	private float total;
	private String notes;
	private Date startDate;
	private Date endDate;
	private List<TimesheetRow> timesheetRows;
	private TimesheetStatus timesheetStatus;
	
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
	public List<TimesheetRow> getTimesheetRows() {
		return timesheetRows;
	}
	public void setTimesheetRows(List<TimesheetRow> timesheetRows) {
		this.timesheetRows = timesheetRows;
	}
	public TimesheetStatus getTimesheetStatus() {
		return timesheetStatus;
	}
	public void setTimesheetStatus(TimesheetStatus timesheetStatus) {
		this.timesheetStatus = timesheetStatus;
	}

}
