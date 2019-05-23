package com.example.Timesheet.com.model;

import java.sql.Date;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import com.example.Timesheet.com.GlobalMessages;
import com.example.Timesheet.com.GlobalVars;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description = "<p>Class representing a project tracked by the application. <br>")
public class Project {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@ApiModelProperty(notes = "<p>Unique identifier of the project. No two projects can have the same id. <br>", example = "3", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "<p>Name of the project.</p>", example = "Abénaquis", position = 1)
	private String name;
	
	@ApiModelProperty(notes = "<p>Description of the project.</p>", example = "Système d'API REST & SOAP", position = 2)
	private String description;
	
	@Column(name = "start_date")
	@ApiModelProperty(notes = "<p>Date (year-month-day) at which the project started.</p>", example = "2018-05-01", position = 3)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date startDate;
	
	@Column(name = "end_date")
	@ApiModelProperty(notes = "<p>Date (year-month-day) at which the project ends.</p>", example = "2018-11-30", position = 4)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date endDate;
	
	@Column(name = "client_id")
	@ApiModelProperty(notes = "<p>Unique identifier of the client the project is for. <br>", example = "1", position = 5)
	private Integer clientId;
	
	@Column(name = "project_manager_id")
	@ApiModelProperty(notes = "<p>Unique identifier of the employee that manages the project. <br>", example = "1", position = 6)
	private Integer projectManagerId;
	
	@Version
	private Integer version;
	
	public void compensateTimezoneOnDates() {
		Date startDate = this.getStartDate();
		Date endDate = this.getEndDate();
		
		if(startDate == null || endDate == null) {
			return;
		}
        
        TimeZone timeZone = TimeZone.getTimeZone(GlobalVars.Timezone);
        int offset = timeZone.getOffset(startDate.getTime());
        startDate.setTime(startDate.getTime() - offset);
        
        offset = timeZone.getOffset(endDate.getTime());
        endDate.setTime(endDate.getTime() - offset);
        
        this.setStartDate(startDate);
        this.setEndDate(endDate);
	}
	
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public Integer getProjectManagerId() {
		return projectManagerId;
	}
	public void setProjectManagerId(Integer projectManagerId) {
		this.projectManagerId = projectManagerId;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}	
}
