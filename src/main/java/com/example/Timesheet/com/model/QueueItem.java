package com.example.Timesheet.com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "Queue")
@ApiModel(description = "<p>Class representing an item from the database table named Queue.</p>")

public class QueueItem {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@ApiModelProperty(notes = "<p>Unique identifier of the queue item. No two queue items can have the same id</p>", example = "1", position = 0)
	private Integer id;
	
	@ApiModelProperty(notes = "<p>Unique identifier of a timesheet.</p>", example = "1", position = 1)
	private Integer timesheetId;

	//getters and setters
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTimesheetId() {
		return timesheetId;
	}

	public void setTimesheetId(Integer timesheetId) {
		this.timesheetId = timesheetId;
	}
	
	
}
