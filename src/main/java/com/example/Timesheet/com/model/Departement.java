package com.example.Timesheet.com.model;

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
	
	public Departement() {
		
	}

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
}
