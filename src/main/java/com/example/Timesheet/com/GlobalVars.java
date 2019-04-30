package com.example.Timesheet.com;

public class GlobalVars {
	
	public static final String Timezone = "Canada/Eastern";
	
	//Department messages
	public static final String DepartementIdNotFound = "No department was found with the specified id";
	public static final String EmployeeUsesDepartementCannotDelete = "The department with the specified id could not be deleted, as some employees are referencing it";
	public static final String DepartementDeleteSuccessful = "The department with the specified id was deleted";
	public static final String DepartementPutSuccessful = "The department with the specified id was modified successfully";
	
	//role messages
	public static final String RoleIdNotFound = "No role was found with the specified id";
	public static final String EmployeeUsesRoleCannotDelete = "The role with the specified cannot be deleted, as some employees are referencing it";
	public static final String RoleDeleteSuccessful = "The role with the specified id was deleted";
	public static final String RolePutSuccessful = "The role with the specified id was modified successfully";
	
	//person messages
	public static final String PersonPutSuccessful = "The person with the specified id was modified successfully";
	public static final String PersonIdNotFound = "No person with the specified id was found";
	
	//timesheetRow messages
	public static final String TimesheetRowIdNotFound = "No timesheetRow was found with the specified id";
	public static final String TimesheetRowDeleteSuccessful = "The timesheetRow with the specified id was deleted";
	
	//timesheet messages
	public static final String TimesheetIdNotFound = "No timesheet was found with the specified id";
	public static final String TimesheetPutSuccessful = "The timesheet with the specified id was modified successfully";
	
	//global messages
	public static final String NameIsEmpty = "Name is empty";
}
