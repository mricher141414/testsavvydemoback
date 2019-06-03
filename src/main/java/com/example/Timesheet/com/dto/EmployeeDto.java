package com.example.Timesheet.com.dto;

import java.sql.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "<p>Class sent by the user to modify an existing employee. <br>"
		+ "No property is required when modifying a employee.</p>")
public class EmployeeDto {

	@ApiModelProperty(notes = "<p>Unique identifier of the employee. No two employees can have the same id. <br>"
			+ "The id is not required, but it is part of the object to facilitate a copy and paste from an existing object.</p>", example = "1", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "<p>Email address of the employee.</p>", example = "f.lecomte@cgi.com", position = 1)
	private String email;
	
	@ApiModelProperty(notes = "<p>Last name of the employee.</p>", example = "Lecomte", position = 2)
	private String lastName;
	
	@ApiModelProperty(notes = "<p>First name of the employee.</p>", example = "François", position = 3)
	private String firstName;
	
	@ApiModelProperty(notes = "<p>Password of the employee.</p>", example = "aaa", position = 4)
	private String password;
	
	@ApiModelProperty(notes = "<p>Unique identifier of the role the employee has. <br>"
			+ "Will cause an error if the role id specified does not belong to an existing role.</p>", example = "2", position = 5)
	private Integer roleId;
	
	@ApiModelProperty(notes = "<p>Unique identifier of the department the employee is in. <br>"
			+ "Will cause an error if the department id specified does not belong to an existing department.</p>", example = "1", position = 6)
	private Integer departmentId;
	
	@ApiModelProperty(notes = "<p>Unique identifier of the employee that manages the employee <br>"
			+ "Will cause an error if the employee id specified does not belong to an existing employee.</p>", example = "2", position = 7)
	private Integer managerId;
	
	@ApiModelProperty(notes = "<p>Physical address of the employee's working place.</p>", example = "101 rue des Abénaquis", position = 9)
	private String address;
	
	@ApiModelProperty(notes = "<p>Date of birth (year-month-day) of the employee.</p>", example = "1955-01-13", position = 8)
	private Date dateOfBirth;
	
	@ApiModelProperty(notes = "<p>Amount of money the employee makes</p>.", example = "16.50", position = 10)
	private Float salary;
	
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
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
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
	public Float getSalary() {
		return salary;
	}
	public void setSalary(Float salary) {
		this.salary = salary;
	}	

}
