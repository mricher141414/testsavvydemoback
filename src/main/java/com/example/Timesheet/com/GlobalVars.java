package com.example.Timesheet.com;

public class GlobalVars {
	
	public static final String Timezone = "Canada/Eastern";
	
	//Department messages
	public static final String DepartementIdNotFound = "No department was found with the specified id";
	public static final String EmployeeUsesDepartementCannotDelete = "The department with the specified id could not be deleted, as some employees are referencing it";
	public static final String DepartementDeleteSuccessful = "The department with the specified id was deleted";
	public static final String DepartementPutSuccessful = "The department with the specified id was modified successfully";
	public static final String DepartementIsNull = "The departementId in body is null. Please add a departementId";
	
	//role messages
	public static final String RoleIdNotFound = "No role was found with the specified id";
	public static final String EmployeeUsesRoleCannotDelete = "The role with the specified cannot be deleted, as some employees are referencing it";
	public static final String RoleDeleteSuccessful = "The role with the specified id was deleted";
	public static final String RolePutSuccessful = "The role with the specified id was modified successfully";
	public static final String RoleIdIsNull = "The roleId in body is null. Please add a roleId";
	
	//employee messages
	public static final String EmployeeDeleteSuccessful = "The employee with the specified id was deleted.";
	public static final String EmployeePutSuccessful = "The employee with the specified id was modified successfully";
	public static final String EmployeeIdNotFound = "No employee with the specified id was found";
	public static final String EmployeeIdParameterNotFound = "No employee with the specified employeeId was found";
	public static final String ManagerIdNotFound = "No employee with the specified managerId was found";
	public static final String EmployeeIdIsNull = "The employeeId in body is null. Please add an employeeId";
	public static final String ManagerIdIsNull = "The managerId in body is null. Please add a managerId";
	public static final String EmployeeUsesManagerCannotDelete = "The employee with the specified id cannot be deleted, as some employees are referencing it as their manager.";
	public static final String TimesheetUsesEmployeeCannotDelete = "The employee with the specified id cannot be deleted, as some timesheets are referencing it.";
	public static final String ProjectUsesEmployeeCannotDelete = "The employee with the specified id cannot be deleted, as some projects are referencing it as their project manager.";
	
	//timesheetRow messages
	public static final String TimesheetRowIdNotFound = "No timesheetRow was found with the specified id";
	public static final String TimesheetRowDeleteSuccessful = "The timesheetRow with the specified id was deleted";
	public static final String TimesheetRowPutSuccessful = "The timesheetRow with the specified id was modified sucessfully.";
	
	//timesheet messages
	public static final String TimesheetIdNotFound = "No timesheet was found with the specified id";
	public static final String TimesheetRowUsesTimesheetCannotDelete = "The timesheet with the specified id cannot be deleted, as some timesheet rows are referencing it.";
	public static final String TimesheetDeleteSuccessful = "The timesheet with the specified id was deleted.";
	public static final String TimesheetPutSuccessful = "The timesheet with the specified id was modified successfully";
	public static final String TimesheetIdIsNull = "The timesheetId in body is null. Please add a timesheetId";
	
	//timesheetStatus messages
	public static final String TimesheetStatusIdNotFound = "No timesheet status was found with the specified timesheetStatusId";
	public static final String TimesheetStatusPutSuccessful = "The timesheet status with the specified id was modified successfully";
	public static final String TimesheetUsesTimesheetStatusCannotDelete = "The timesheet status with the specified id cannot be deleted, as some timesheets are referencing it.";
	public static final String TimesheetStatusDeleteSuccessful = "The timesheet status with the specified id was deleted.";
	public static final String TimesheetStatusIdIsNull = "The timesheetStatusId in body is null. Please add a timesheetStatusId";
	
	//project messages
	public static final String ProjectIdParameterNotFound = "No project was found with the specified projectId";
	public static final String ProjectIdNotFound = "No project was found with the specified id";
	public static final String ProjectIdIsNull = "The projectId in body is null. Please add a projectId";
	public static final String ProjectPutSuccessful = "The project with the specified id was modified successfully";
	public static final String TimesheetRowUsesProjectCannotDelete = "The project with the specified id cannot be deleted, as some timesheet rows are referencing it.";
	public static final String ProjectDeleteSuccessful = "The project with the specified id was deleted.";
	
	//client messages
	public static final String ClientIdParameterNotFound = "No client was found with the specified clientId";
	
	//global messages
	public static final String NameIsEmpty = "Name is empty";
}
