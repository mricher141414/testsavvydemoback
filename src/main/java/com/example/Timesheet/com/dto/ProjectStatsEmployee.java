package com.example.Timesheet.com.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "<p>Class received by the user by the endpoint project/stats/employee</p>")
public class ProjectStatsEmployee {

	@ApiModelProperty(notes = "<p>Name of the project.</p>", example = "Abénaquis", position = 0)
	private String name;
	
	@ApiModelProperty(notes = "<p>Amount of employees working on the project.</p>", example = "3", position = 1)
	private int nbEmployeeOnProject;
	
	@ApiModelProperty(notes = "<p>Average salary of all employees working on the project.</p>", example = "20.0", position = 2)
	private Float averageSalary;
	
	//getters and setters
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNbEmployeeOnProject() {
		return nbEmployeeOnProject;
	}
	public void setNbEmployeeOnProject(int nbEmployeeOnProject) {
		this.nbEmployeeOnProject = nbEmployeeOnProject;
	}
	public Float getAverageSalary() {
		return averageSalary;
	}
	public void setAverageSalary(Float averageSalary) {
		this.averageSalary = averageSalary;
	}
	
}
