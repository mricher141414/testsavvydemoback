package com.example.Timesheet.com.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.example.Timesheet.com.model.Departement;
import com.example.Timesheet.com.model.Person;
import com.example.Timesheet.com.model.Role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Class received by the user by the endpoint timesheet/employee. Is a more detailed version of the Person model.")
public class PersonWithManager  {
	
	@ApiModelProperty(notes = "Unique identifier of the person. No two persons can have the same id.", example = "1", position = 0)
	protected int id;
	
	@ApiModelProperty(notes = "Email address of the person.", example = "f.lecomte@cgi.com", position = 1)
	protected String emailAddress;
	
	@ApiModelProperty(notes = "Last name of the person.", example = "Lecomte", position = 2)
	protected String lastName;
	
	@ApiModelProperty(notes = "First name of the person.", example = "François", position = 3)
	protected String firstName;
	
	@ApiModelProperty(notes = "Password of the person.", example = "aaa", position = 4)
	protected String password;
	
	@ApiModelProperty(notes = "Full object of the role the person has. \n"
			+ "Check the role model for more information.", position = 5)
	protected Role role;
	
	@ApiModelProperty(notes = "Full object of the department the person is in. \n"
			+ "Check the department model for more information.", position = 6)
	protected Departement departement;
	
	@ApiModelProperty(notes = "Physical address of the person's working place.", example = "101 rue des Abénaquis", position = 8)
	protected String address;
	
	@ApiModelProperty(notes = "Date of birth (year-month-day) of the person.", example = "1955-01-13", position = 7)
	protected Date dateOfBirth;
	
	@ApiModelProperty(notes = "List of TimesheetComplex objects to represent all the timesheets that the person has that matches the condition.", position = 9)
	protected List<TimesheetComplex> timesheets = new ArrayList<TimesheetComplex>();
	
	@ApiModelProperty(notes = "Full object of the person that manages this person. \n"
			+ "Check the person model for more information.", position = 10)
	private Person manager;

	//getters and setters
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public Person getManager() {
		return manager;
	}

	public void setManager(Person manager) {
		this.manager = manager;
	}
}
