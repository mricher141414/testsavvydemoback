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
@ApiModel(description = "<p>Class representing the status of a timesheet tracked by the application.</p>")
public class TimesheetStatus implements Serializable {

	private static final long serialVersionUID = 1582134938619233099L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@ApiModelProperty(notes = "<p>Unique identifier of the status. No two statuses can have the same id.</p>", example = "1", position = 0)
	private int id;

	@ApiModelProperty(notes = "<p>Name of the status.</p>", example = "Brouillon", position = 1)
	private String name;
	
	@Version
	private Integer version;

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
	
	public void setVersion(int version) {
		this.version = version;
	}

}
