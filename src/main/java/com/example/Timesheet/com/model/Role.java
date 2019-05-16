package com.example.Timesheet.com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@Entity
@ApiModel(description = "<p>Class representing a role tracked by the application.</p>")
public class Role {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@ApiModelProperty(notes = "<p>Unique identifier of the role. No two roles can have the same id</p>", example = "1", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "<p>Name of the role.</p>", example = "Manager", position = 1)
	private String name;
	
	@ApiModelProperty(notes = "<p>Description about the role</p>", example = "Les gestionnaires qui s'occupe de gérer les équipes et les projets", position = 2)
	private String descrole;
	
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
		return descrole;
	}

	public void setDescription(String description) {
		this.descrole = description;
	}
}
