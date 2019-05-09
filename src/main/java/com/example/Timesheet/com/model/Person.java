package com.example.Timesheet.com.model;

import java.sql.Date;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.Timesheet.com.GlobalVars;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "Employee")
@ApiModel(description = "<p>Class representing a person tracked by the application.</p>")
public class Person {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@ApiModelProperty(notes = "<p>Unique identifier of the person. No two persons can have the same id</p>", example = "1", position = 0)
	private int id;
	
	@Column(name = "email_address")
	@ApiModelProperty(notes = "<p>Email address of the person.</p>", example = "f.lecomte@cgi.com", position = 1)
	private String email;
	
	@ApiModelProperty(notes = "<p>Last name of the person.</p>", example = "Lecomte", position = 2)
	private String lastName;
	
	@ApiModelProperty(notes = "<p>First name of the person.</p>", example = "François", position = 3)
	private String firstName;
	
	@ApiModelProperty(notes = "<p>Password of the person.</p>", example = "aaa", position = 4)
	private String password;
	
	@Column(name = "role_id")
	@ApiModelProperty(notes = "<p>Unique identifier of the person's role.</p>", example = "2", position = 5)
	private Integer roleId;
	
	@Column(name = "departement_id")
	@ApiModelProperty(notes = "<p>Unique identifier of the person's department.</p>", example = "1", position = 6)
	private Integer departementId;
	
	@Column(name = "manager_id")
	@ApiModelProperty(notes = "<p>Unique identifier of the person's manager.</p>", example = "2", position = 7)
	private Integer managerId;
	
	@Column(name = "date_of_birth")
	@ApiModelProperty(notes = "<p>Date of birth (year-month-date) of the person.</p>", example = "1955-01-13", position = 8)
	private Date dateOfBirth;
	
	@ApiModelProperty(notes = "<p>Physical address of the person's working location.", example = "101 rue des Abénaquis", position = 9)
	private String address;
	
	public void compensateTimezoneOnDates() {
		Date dateOfBirth = this.getDateOfBirth();
        
        TimeZone timeZone = TimeZone.getTimeZone(GlobalVars.Timezone);
        int offset = timeZone.getOffset(dateOfBirth.getTime());
        
        dateOfBirth.setTime(dateOfBirth.getTime() - offset);
        
        this.setDateOfBirth(dateOfBirth);
	}
	
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
