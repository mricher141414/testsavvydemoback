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
@ApiModel(description = "<p>Class representing a department tracked by the application.</p>")
public class Department implements Serializable {
	
	private static final long serialVersionUID = 3409397416921329560L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@ApiModelProperty(notes = "<p>Unique identifier of the department. No two departments can have the same id.</p>", example = "1", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "<p>Name of the department</p>", example = "Assurance qualité", position = 1)
	private String name;
	
	@ApiModelProperty(notes = "<p>Description about the department</p>", example = "Département qui s'occupe de faire des tests", position = 2)
	private String description;
	
	@Version
	@ApiModelProperty(notes = "<p>Property to make sure the person modifying the department has the current version", position = 100)
	private Integer version;
	
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
