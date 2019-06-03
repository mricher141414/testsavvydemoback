package com.example.Timesheet.com.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Timesheet.com.dto.TimesheetRowProjectDto;
import com.example.Timesheet.com.model.TimesheetRowProject;
import com.example.Timesheet.com.service.TimesheetRowProjectService;

@Component
public class TimesheetRowProjectMapper implements ITimesheetRowProjectMapper {

	@Autowired
	private TimesheetRowProjectService timesheetRowProjectService;

	@Override
	public TimesheetRowProject dtoToTimesheetRowProject(TimesheetRowProjectDto source, int id) {
		if(source == null) {
			return null;
		}
		
		TimesheetRowProject timesheetRowProject = new TimesheetRowProject();
		
		Optional<TimesheetRowProject> optionalTimesheetRowProject = this.timesheetRowProjectService.getById(id);
		
		if(optionalTimesheetRowProject.isPresent()) {
			timesheetRowProject = optionalTimesheetRowProject.get();
		}
			
		timesheetRowProject.setId(id);
		
		if(source.getProjectId() != null) {
			timesheetRowProject.setProjectId(source.getProjectId());
		}
		
		if(source.getValue() != null) {
			timesheetRowProject.setValue(source.getValue());
		}
		
		if(source.getTimesheetRowId() != null) {
			timesheetRowProject.setTimesheetRowId(source.getTimesheetRowId());
		}
		
		
		return timesheetRowProject;
	}

	@Override
	public TimesheetRowProjectDto timesheetRowProjectToDto(TimesheetRowProject destination) {
		if(destination == null) {
			return null;
		}
		
		TimesheetRowProjectDto timesheetRowProjectDto = new TimesheetRowProjectDto();
		
		timesheetRowProjectDto.setId(destination.getId());
		timesheetRowProjectDto.setProjectId(destination.getProjectId());
		timesheetRowProjectDto.setValue(destination.getValue());
		timesheetRowProjectDto.setTimesheetRowId(destination.getTimesheetRowId());
		
		return timesheetRowProjectDto;
	}
}
