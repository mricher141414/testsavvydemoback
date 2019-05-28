package com.example.Timesheet.com.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "<p>Class received by the user by the endpoint project/stats/employee</p>")
public class ClientStatsEmployee {
	
	@ApiModelProperty(notes = "<p>Name of the client.</p>", example = "example = Les entreprises Pères et fils Inc.", position = 0)
	private String clientName;
	
	@ApiModelProperty(notes = "<p>Amount of employees working on projects of the client.</p>", example = "3", position = 1)
	private int nbEmployeeOnClientProjects;
	
	@ApiModelProperty(notes = "<p>Average salary of all employees working on these projects.</p>", example = "20.0", position = 2)
	private Float averageSalary;

	//getters and setters
	
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public int getNbEmployeeOnClientProjects() {
		return nbEmployeeOnClientProjects;
	}

	public void setNbEmployeeOnClientProjects(int nbEmployeeOnClientProjects) {
		this.nbEmployeeOnClientProjects = nbEmployeeOnClientProjects;
	}

	public Float getAverageSalary() {
		return averageSalary;
	}

	public void setAverageSalary(Float averageSalary) {
		this.averageSalary = averageSalary;
	}

	
}
