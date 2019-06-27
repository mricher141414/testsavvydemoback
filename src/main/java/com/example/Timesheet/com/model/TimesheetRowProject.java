package com.example.Timesheet.com.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description = "<p>Class representing the amount of time worked on a project in a day tracked by the application.</p>")
public class TimesheetRowProject implements Serializable {

	private static final long serialVersionUID = -7627329098796554971L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@ApiModelProperty(notes = "<p>Unique identifier of the timeProject. No two timeProject can have the same id.</p>", example = "6", position = 0)
	private Integer id;
	
	@ApiModelProperty(notes = "<p>Unique identifier of the project that the employee worked on. No two projects can have the same id. <br>"
			+ "Can be null.</p>", example = "3", position = 1)
	private Integer projectId;
	
	@ApiModelProperty(notes = "<p>Amount of hours the employee worked on. Use a dot (.) for decimals", example = "4.5", position = 2)
	private Float value;
	
	@ApiModelProperty(notes = "<p>Unique identifier of the timesheet row that the timeProject is currently in. No two timesheets can have the same id.</p>", example = "4", position = 3)
	private Integer timesheetRowId;

	@Version
	private Integer version;
	
	//getters and setters
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public Integer getTimesheetRowId() {
		return timesheetRowId;
	}

	public void setTimesheetRowId(Integer timesheetRowId) {
		this.timesheetRowId = timesheetRowId;
	}
	
	//getVersion, but with a different name so swagger won't pick it up
	public Integer fetchVersion() {
		return this.version;
	}
	
	public void setVersion(int version) {
		this.version = version;
	}
}
