package cgi;

import java.util.Date;

public class Timesheet {
	
	private int id;
	private Float total;
	private String notes;
	private Date startDate;
	private Date endDate;
	private Integer employeeId;
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
	public Integer getTimesheetStatusId() {
		return timesheetStatusId;
	}
	public void setTimesheetStatusId(Integer timesheetStatusId) {
		this.timesheetStatusId = timesheetStatusId;
	}	
}
