package com.example.Timesheet.com.dto;

import java.io.Serializable;

import org.springframework.util.Assert;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "<p>Class received by the frontend to validate a successful login")
public class UserDto implements Serializable {
	
	private static final long serialVersionUID = 5340489619232396258L;

	@ApiModelProperty(notes = "<p>Unique identifier of the employee. No two employee can have the same id", example = "1", position = 0)
	private int id;
	
	@ApiModelProperty(notes = "<p>Email address of the employee.</p>", example = "f.lecomte@cgi.com", position = 1)
	private String username;
	
	@ApiModelProperty(notes = "<p>Password of the employee.</p>", example = "aaa", position = 2)
	private String password;
	
	@ApiModelProperty(notes = "<p>First name of the employee.</p>", example = "François", position = 3)
	private String firstName;
	
	@ApiModelProperty(notes = "<p>Last name of the employee.</p>", example = "Lecomte", position = 4)
	private String lastName;
	
	@ApiModelProperty(notes = "<p>Unique identifier of the role the employee has. </p>")
	private int roleId;
	
	@ApiModelProperty(notes = "Hashed token to indicate info about the user and where this info comes from ", example = "fake-jwt-token", position = 6)
	private String token;
	
	//getters and setters
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		Assert.notNull(token, "token cannot be null");
		this.token = token;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
}
