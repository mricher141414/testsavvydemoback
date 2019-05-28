package com.example.Timesheet.com.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "<p>Class sent by the user to modify an existing client or create a new one.<br>" 
				+ "No property is required when modifying a client.</p>")

public class ClientDto {
	
	@ApiModelProperty(notes = "<p>Unique identifier of the client. No two clients can have the same id. <br>"
			+ "The id is not required, but it is part of the object to facilitate a copy and paste from an existing object.</p>", example = "1", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "<p>Name of the client.", example = "Les entreprises Pères et fils Inc.", position = 1)
	private String name;

	@ApiModelProperty(notes = "<p>Physical address of the client.</p>", example = "125 rue Bienvenue", position = 2)
	private String address;
	
	@ApiModelProperty(notes = "<p>Description about who the client is, what he does, etc..</p>", example = "Entreprise qui offres des thérapies de famille", position = 3)
	private String description;
	
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
	
	
}
