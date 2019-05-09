package com.example.Timesheet.com.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.example.Timesheet.com.model.Departement;
import com.example.Timesheet.com.model.Role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "<p>Class received by the user by the endpoint timesheet/manager. Is a more detailed version of the Person model.</p>"
			)
public class PersonComplex {
	
	@ApiModelProperty(notes = "<p>Unique identifier of the person. No two persons can have the same id.</p>", example = "1", position = 0)
	protected int id;
	
	@ApiModelProperty(notes = "<p>Email address of the person.</p>", example = "f.lecomte@cgi.com", position = 1)
	protected String emailAddress;
	
	@ApiModelProperty(notes = "<p>Last name of the person.</p>", example = "Lecomte", position = 2)
	protected String lastName;
	
	@ApiModelProperty(notes = "<p>First name of the person.</p>", example = "François", position = 3)
	protected String firstName;
	
	@ApiModelProperty(notes = "<p>Password of the person.</p>", example = "aaa", position = 4)
	protected String password;
	
	@ApiModelProperty(notes = "<p>Full object of the role the person has. <br>"
			+ "Check the role model for more information.</p>", position = 5)
	protected Role role;
	
	@ApiModelProperty(notes = "<p>Full object of the department the person is in. <br>"
			+ "Check the department model for more information.</p>", position = 6)
	protected Departement departement;
	
	@ApiModelProperty(notes = "<p>Physical address of the person's working place.</p>", example = "101 rue des Abénaquis", position = 8)
	protected String address;
	
	@ApiModelProperty(notes = "<p>Date of birth (year-month-day) of the person.</p>", example = "1955-01-13", position = 7)
	protected Date dateOfBirth;
	
	@ApiModelProperty(notes = "<p>List of TimesheetComplex objects to represent all the timesheets that the person has that matches the condition.</p>", position = 9)
	protected List<TimesheetComplex> timesheets = new ArrayList<TimesheetComplex>();

	//getters and setters
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Departement getDepartement() {
		return departement;
	}
	public void setDepartement(Departement departement) {
		this.departement = departement;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public List<TimesheetComplex> getTimesheets() {
		return timesheets;
	}
	public void setTimesheets(List<TimesheetComplex> timesheets) {
		this.timesheets = timesheets;
	}
	
	public void addToTimesheets(TimesheetComplex timesheetComplex) {		
		timesheets.add(timesheetComplex);
		
	}

	
}
