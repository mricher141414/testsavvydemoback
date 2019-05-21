package cgi;

import java.util.Date;

public class TimesheetRow {

	private int id;
	private Date date;
	private int timeSheetId;
	private HourProject[] hourProject;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getTimeSheetId() {
		return timeSheetId;
	}
	public void setTimeSheetId(int timeSheetId) {
		this.timeSheetId = timeSheetId;
	}
	public HourProject[] getHourProject() {
		return hourProject;
	}
	public void setHourProject(HourProject[] hourProject) {
		this.hourProject = hourProject;
	}

}
