package com.example.Timesheet.com.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "<p>Class sent by the user to modify an existing assignation.")
public class ProjectEmployeeDto implements Serializable {

	private static final long serialVersionUID = -6106722012995764025L;

	@ApiModelProperty(notes = "<p>Unique identifier of the assignation. No two assignations can have the same id <br>" 
			+ "The id is not required, but it is part of the object to facilitate a copy and paste from an existing object.</p>", example = "11", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "<p>Unique identifier of the employee that is part of the assignation. <br>" 
			+ "Will cause an error if the role id specified does not belong to an existing role.</p>", example = "6", position = 1)
	private Integer employeeId;
	
	@ApiModelProperty(notes = "<p>Unique identifier of the project that is part of the assignation. No two projects can have the same id <br>" 
			+ "Will cause an error if the role id specified does not belong to an existing role.</p>", example = "2", position = 2)
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
