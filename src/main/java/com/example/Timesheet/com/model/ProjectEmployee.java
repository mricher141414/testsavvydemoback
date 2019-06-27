package com.example.Timesheet.com.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description = "<p>Class representing an employee-project assignation tracked by the application.</p>")
public class ProjectEmployee implements Serializable {

	private static final long serialVersionUID = -4373978997505082157L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@ApiModelProperty(notes = "<p>Unique identifier of the assignation. No two assignations can have the same id</p>", example = "11", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "<p>Unique identifier of the employee that is part of the assignation. No two employees can have the same id</p>", example = "6", position = 1)
	private Integer employeeId;
	
	@ApiModelProperty(notes = "<p>Unique identifier of the project that is part of the assignation. No two projects can have the same id</p>", example = "2", position = 2)
	private Integer projectId;

	//getters and setters
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	
}
