package com.example.Timesheet.com.dto;

import java.io.Serializable;
import java.sql.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "<p>Class sent by the user to modify an existing project. <br>"
		+ "No property is required when modifying a project.</p>")
public class ProjectDto implements Serializable {

	private static final long serialVersionUID = 9150688854266609965L;

	@ApiModelProperty(notes = "<p>Unique identifier of the project. No two projects can have the same id. <br>"
			+ "The id is not required, but it is part of the object to facilitate a copy and paste from an existing object.</p>", example = "3", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "<p>Name of the project.</p>", example = "Abénaquis", position = 1)
	private String name;
	
	@ApiModelProperty(notes = "<p>Description of the project.</p>", example = "Système d'API REST & SOAP", position = 2)
	private String description;
	
	@ApiModelProperty(notes = "<p>Date (year-month-day) at which the project started.</p>", example = "2018-05-01", position = 3)
	private Date startDate;
	
	@ApiModelProperty(notes = "<p>Date (year-month-day) at which the project ends.</p>", example = "2018-11-30", position = 4)
	private Date endDate;
	
	@ApiModelProperty(notes = "<p>Unique identifier of the client the project is for. <br>"
			+ "Will cause an error if the client id specified does not belong to an existing client.</p>", example = "1", position = 5)
	private Integer clientId;
	
	@ApiModelProperty(notes = "<p>Unique identifier of the employee that manages the project. <br>"
			+ "Will cause an error if the project manager id specified does not belong to an existing employee.</p>", example = "1", position = 6)
	private Integer projectManagerId;

	//getters and setters
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getProjectManagerId() {
		return projectManagerId;
	}

	public void setProjectManagerId(Integer projectManagerId) {
		this.projectManagerId = projectManagerId;
	}
}
