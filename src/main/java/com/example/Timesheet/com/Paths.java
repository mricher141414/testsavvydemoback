package com.example.Timesheet.com;

public class Paths {
	
	//client paths
	public static final String ClientBasicPath = "/client";
	
	//frontend only
	public static final String ClientGetOne = "/client/one";
	public static final String ClientGetStatsEmployee = "/client/stats/employee";
	public static final String ClientProjects = "/client/project";
	
	//department paths
	public static final String DepartmentBasicPath = "/department";
	
	//frontend only
	public static final String DepartmentGetOne = "/department/one";
	
	//employee paths
	public static final String EmployeeBasicPath = "/employee";
	public static final String EmployeeGetOne = "/employee/one";
	public static final String EmployeeAssignations = "/employee/assignation";
	
	//frontend only
	public static final String EmployeeGetManaged = "/employee/managed";
	
	//login paths
	public static final String LoginPath = "/login";
	public static final String LogoutPath = "/logout";
	
	//project paths
	public static final String ProjectBasicPath = "/project";
	public static final String ProjectAssignations = "/project/assignation";
	
	//frontend only
	public static final String ProjectGetStatsEmployee = "/project/stats/employee";
	public static final String ProjectGetStatsTimePerDay = "/project/stats/average/timeperday";
	
	//projectEmployee paths
	public static final String ProjectEmployeeBasicPath = "/assignation";
	public static final String ProjectEmployeeDeleteWithIds = "/assignation/withids";
	
	//queue paths
	public static final String ApiCallToBatFile = "/bat";
	public static final String QueueBasicPath = "/queue";
	public static final String QueueExistsByTimesheet = "/queue/exists/timesheet";
	public static final String QueueByTimesheet = "/queue/timesheet";
	
	//role paths
	public static final String RoleBasicPath = "/role";
	public static final String RoleGetOne = "/role/one";
	
	//frontend only
	public static final String RoleGetEmployees = "/role/employee";
	
	//salaryReport paths
	public static final String SalaryReportBasicPath = "/salaryreport";
	public static final String SalaryReportDeleteAll = "/salaryreport/all";
	
	//timesheet paths
	public static final String TimesheetBasicPath = "/timesheet";
	public static final String TimesheetGetOne = "/timesheet/one";
	public static final String TimesheetGetByEmployeeAndStartDate = "/timesheet/employee";
	public static final String TimesheetGetByEmployeeManagerAndStartDate = "/timesheet/manager";
	
	//frontend only
	public static final String TimesheetCreateWithRows = "/timesheet/create/rows";
	public static final String TimesheetEditWithTimesheetComplex = "/timesheet/edit";
	public static final String TimesheetDeleteWithRows = "/timesheet/delete/rows";
	public static final String TimesheetGetAllFromEmployee = "/timesheet/employee/all";
	public static final String TimesheetGetAllComplexFromEmployee = "/timesheet/employee/all/detailed";
	public static final String TimesheetGetAllToVerify = "/timesheet/toverify";
	public static final String TimesheetGetAllProjects = "/timesheet/projects";
	public static final String TimesheetGetLastWeekComplex = "/timesheet/lastweek/detailed";
	
	//timesheetRow paths
	public static final String TimesheetRowBasicPath = "/timesheetrow";
	public static final String TimesheetRowGetOne = "/timesheetrow/one";
	
	//timesheetRowProject paths
	public static final String TimesheetRowProjectBasicPath = "/timesheetrowproject";
	
	//timesheetStatus paths
	public static final String TimesheetStatusBasicPath = "/timesheetstatus";
	public static final String TimesheetStatusGetOne = "/timesheetstatus/one";
}
