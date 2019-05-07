package com.example.Timesheet.com.dto;

import java.sql.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Class sent by the user to modify an existing person. \n"
		+ "No property is required when modifying a person.")
public class PersonDTO {

	@ApiModelProperty(notes = "Unique identifier of the person. No two persons can have the same id. \n"
			+ "The id is not required, but it is part of the object to facilitate a copy and paste from an existing object", example = "1", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "Email address of the person.", example = "f.lecomte@cgi.com", position = 1)
	private String email;
	
	@ApiModelProperty(notes = "Last name of the person.", example = "Lecomte", position = 2)
	private String lastName;
	
	@ApiModelProperty(notes = "First name of the person.", example = "François", position = 3)
	private String firstName;
	
	@ApiModelProperty(notes = "Password of the person.", example = "aaa", position = 4)
	private String password;
	
	@ApiModelProperty(notes = "Unique identifier of the role the person has. \n"
			+ "Will cause an error if the role id specified does not belong to an existing role", example = "2", position = 5)
	private Integer roleId;
	
	@ApiModelProperty(notes = "Unique identifier of the department the person is in. \n"
			+ "Will cause an error if the department id specified does not belong to an existing department", example = "1", position = 6)
	private Integer departementId;
	
	@ApiModelProperty(notes = "Unique identifier of the person that manages the person \n"
			+ "Will cause an error if the person id specified does not belong to an existing person", example = "2", position = 7)
	private Integer managerId;
	
	@ApiModelProperty(notes = "Physical address of the person's working place.", example = "101 rue des Abénaquis", position = 9)
	private String address;
	
	@ApiModelProperty(notes = "Date of birth (year-month-day) of the person.", example = "1955-01-13", position = 8)
	private Date dateOfBirth;
	
	//getters and setters
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getDepartementId() {
		return departementId;
	}
	public void setDepartementId(Integer departementId) {
		this.departementId = departementId;
	}
	public Integer getManagerId() {
		return managerId;
	}
	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
