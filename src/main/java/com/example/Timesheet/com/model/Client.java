package com.example.Timesheet.com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "Client")
@ApiModel(description = "<p>Class representing a client tracked by the application.</p>")
public class Client {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@ApiModelProperty(notes = "<p>Unique identifier of the client. No two clients can have the same id.</p>", example = "1", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "<p>Name of the client.</p>", example = "Les entreprises Pères et fils Inc.", position = 1)
	private String name;
	
	@ApiModelProperty(notes = "<p>Physical address of the client.</p>", example = "125 rue Bienvenue", position = 2)
	private String address;
	
	@ApiModelProperty(notes = "<p>Description about who the client is, what he does, etc..</p>", example = "Entreprise qui offres des thérapies de famille", position = 3)
	private String description;
	
	@Version
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
