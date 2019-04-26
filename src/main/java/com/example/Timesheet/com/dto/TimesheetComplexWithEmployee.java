package com.example.Timesheet.com.dto;

public class TimesheetComplexWithEmployee extends TimesheetComplex {
	
	private PersonComplex employee;
	
	public PersonComplex getEmployee() {
		return employee;
	}
	public void setEmployee(PersonComplex employee) {
		this.employee = employee;
	}
}
