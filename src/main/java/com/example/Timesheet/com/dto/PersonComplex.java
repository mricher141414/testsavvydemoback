package com.example.Timesheet.com.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.example.Timesheet.com.model.Departement;
import com.example.Timesheet.com.model.Person;
import com.example.Timesheet.com.model.Role;

public class PersonComplex {

	private int id;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String password;
	private Role role;
	private Departement departement;
	private Person manager;
	private String address;
	private Date dateOfBirth;
	private List<TimesheetComplex> timesheets = new ArrayList<TimesheetComplex>();


	public List<TimesheetComplex> getTimesheets() {
		return timesheets;
	}
	public void setTimesheets(List<TimesheetComplex> timesheets) {
		this.timesheets = timesheets;
	}
	
	public void addToTimesheets(TimesheetComplex timesheetComplex) {		
		timesheets.add(timesheetComplex);
		
	}

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
	public Person getManager() {
		return manager;
	}
	public void setManager(Person manager) {
		this.manager = manager;
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

}
