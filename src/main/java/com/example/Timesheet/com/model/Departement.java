package com.example.Timesheet.com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description = "<p>Class representing a department tracked by the application.</p>")
public class Departement {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@ApiModelProperty(notes = "<p>Unique identifier of the department. No two departments can have the same id.</p>", example = "1", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "<p>Name of the department</p>", example = "Assurance qualité", position = 1)
	private String name;
	
	@ApiModelProperty(notes = "<p>Description about the department</p>", example = "Département qui s'occupe de faire des tests", position = 3)
	private String descdepartement;
	
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
		return descdepartement;
	}

	public void setDescription(String description) {
		this.descdepartement = description;
	}
	
	
}
