package com.example.Timesheet.com.dto;

import java.util.ArrayList;
import java.util.List;

public class PersonComplexWithTimesheets extends PersonComplex {
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

}
