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
public class Timesheet {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private Integer total;
	private String notes;
	@Column(name = "start_date")
	private Date startDate;
	@Column(name = "end_date")
	private Date endDate;
	@Column(name = "employee_id")
	private Integer employeeId;
	
	@Column(name = "timesheet_status_id")
	private Integer timesheetStatusId;
	
	public void compensateTimezoneOnDates() {
		Date startDate = this.getStartDate();
		Date endDate = this.getEndDate();
        
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


}
