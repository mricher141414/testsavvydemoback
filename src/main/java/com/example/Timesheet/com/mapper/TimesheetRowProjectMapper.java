package com.example.Timesheet.com.mapper;

import java.io.Serializable;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Timesheet.com.dto.TimesheetRowProjectDto;
import com.example.Timesheet.com.model.TimesheetRowProject;
import com.example.Timesheet.com.service.TimesheetRowProjectService;

@Component
public class TimesheetRowProjectMapper implements ITimesheetRowProjectMapper, Serializable {

	private static final long serialVersionUID = -3253794897607658132L;
	private static final Logger log = LogManager.getLogger(TimesheetRowProjectMapper.class);
	
	@Autowired
	private TimesheetRowProjectService timesheetRowProjectService;

	@Override
	public TimesheetRowProject dtoToTimesheetRowProject(TimesheetRowProjectDto source, int id) {
		log.debug("Entering dtoToTimesheetRowProject with id parameter of " + id);
		
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
		log.debug("Entering timesheetRowProjectToDto");
		
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
