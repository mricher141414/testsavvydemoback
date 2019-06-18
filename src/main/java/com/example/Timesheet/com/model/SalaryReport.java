package com.example.Timesheet.com.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description = "<p>Class representing a salary report from the table with the same name.</p>")
public class SalaryReport implements Serializable {

	private static final long serialVersionUID = 8256836841240875261L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@ApiModelProperty(notes = "<p>Unique identifier of the client. No two clients can have the same id.</p>", example = "1", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "<p>Id of the employee that is getting reported. No two employees can have the same id.</p>", example = "1", position = 1)
	private Integer employeeId;
	
	@ApiModelProperty(notes = "<p>Paycheck of the employee</p>")
	private Float paycheck;

	//getters and setters
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Float getPaycheck() {
		return paycheck;
	}

	public void setPaycheck(Float paycheck) {
		this.paycheck = paycheck;
	}
	
	
}
