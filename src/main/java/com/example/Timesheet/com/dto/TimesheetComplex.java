package com.example.Timesheet.com.dto;

import java.sql.Date;
import java.util.List;

import com.example.Timesheet.com.model.Person;
import com.example.Timesheet.com.model.TimesheetRow;
import com.example.Timesheet.com.model.TimesheetStatus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "<p>Class used by PersonComplex. Is a more detailed version of the Timesheet model.</p>")
public class TimesheetComplex {
	
	@ApiModelProperty(notes = "<p>Unique identifier of the timesheet. No two timesheets can have the same id.</p>", example = "1", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "<p>Total amount of hours of the timesheet. Uses a dot (.) for decimals.</p>", example = "40.5", position = 1)
	private float total;
	
	@ApiModelProperty(notes = "<p>Notes of the timesheet.</p>", example = "Première timesheet", position = 2)
	private String notes;
	
	@ApiModelProperty(notes = "<p>Date (year-month-date) at which the timesheet started.</p>", example = "2019-06-29", position = 3)
	private Date startDate;
	
	@ApiModelProperty(notes = "<p>Date (year-month-date) at which the timesheet ended.</p>", example = "2019-07-05", position = 4)
	private Date endDate;
	
	@ApiModelProperty(notes = "<p>List of all timesheetRows that reference this timesheet.</p>", position = 5)
	private List<TimesheetRow> timesheetRows;
	
	@ApiModelProperty(notes = "<p>Object of the timesheetStatus used by the timesheet.</p>", position = 6)
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
