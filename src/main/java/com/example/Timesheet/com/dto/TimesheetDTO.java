package com.example.Timesheet.com.dto;

import java.sql.Date;

public class TimesheetDTO {

	private int id;
	private Integer total;
	private String notes;
	private Date startDate;
	private Date endDate;
	private Integer employeeId;
	private Integer timesheetStatusId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
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
