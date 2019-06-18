package com.example.Timesheet.com.dto;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import com.example.Timesheet.com.model.TimesheetRowProject;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "<p>Class sent by the front-end to modify a timesheetRow and its timesheetRowProjects. <br>"
		+ "Also, the class the user receives when checking a specific timesheetRow, and it is used by TimesheetComplex <br>"
		+ "All properties except id are recommanded when creating a new timesheet row.</p>")
public class TimesheetRowWithProject implements Serializable {

	private static final long serialVersionUID = 7356986511719672935L;

	@ApiModelProperty(notes = "<p>Unique identifier of the timesheet row. No two timesheet rows can have the same id. <br>"
			+ "The id is not required, but it is part of the object to facilitate a copy and paste from an existing object</p>", example = "1", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "<p>Date (year-month-date) of the row.</p>", example = "2019-07-01", position = 1)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date date;
	
	@ApiModelProperty(notes = "<p>Unique identifier of the timesheet that the row is currently in. <br>"
			+ "Will cause an error if the timesheet id specified does not belong to an existing timesheet.</p>", example = "1", position = 3)
	private Integer timesheetId;
	
	@ApiModelProperty(notes = "<p>List of all timesheetRowProjects that reference this timesheetRow.</p>", position = 3)
	private List<TimesheetRowProject> timesheetRowProjects;

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

	public List<TimesheetRowProject> getTimesheetRowProjects() {
		return timesheetRowProjects;
	}

	public void setTimesheetRowProjects(List<TimesheetRowProject> timesheetRowProjects) {
		this.timesheetRowProjects = timesheetRowProjects;
	}
	
	
}
