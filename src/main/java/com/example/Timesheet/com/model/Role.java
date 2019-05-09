package com.example.Timesheet.com.model;

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

	public Role() {

	}

	public Role(String name) {	
		this.name = name;
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
