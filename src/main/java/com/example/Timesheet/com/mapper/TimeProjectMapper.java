package com.example.Timesheet.com.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Timesheet.com.dto.TimeProjectDto;
import com.example.Timesheet.com.model.TimeProject;
import com.example.Timesheet.com.service.TimeProjectService;

@Component
public class TimeProjectMapper implements ITimeProjectMapper {

	@Autowired
	private TimeProjectService timeProjectService;

	@Override
	public TimeProject DtoToTimeProject(TimeProjectDto source, int id) {
		if(source == null) {
			return null;
		}
		
		TimeProject timeProject = new TimeProject();
		
		timeProject.setId(id);
		timeProject.setProjectId(source.getProjectId());
		timeProject.setValue(source.getValue());
		timeProject.setTimesheetRowId(source.getTimesheetRowId());
		
		Optional<TimeProject> optionalTimeProject = this.timeProjectService.getById(id);
		
		if(optionalTimeProject.isPresent()) {
			TimeProject dbTimeProject = optionalTimeProject.get();
			
			if(timeProject.getProjectId() == null) {
				timeProject.setProjectId(dbTimeProject.getProjectId());
			}
			
			if(timeProject.getValue() == null) {
				timeProject.setValue(dbTimeProject.getValue());
			}
			
			if(timeProject.getTimesheetRowId() == null) {
				timeProject.setTimesheetRowId(dbTimeProject.getTimesheetRowId());
			}
		}
		
		return timeProject;
	}

	@Override
	public TimeProjectDto TimeProjectToDto(TimeProject destination) {
		if(destination == null) {
			return null;
		}
		
		TimeProjectDto timeProjectDto = new TimeProjectDto();
		
		timeProjectDto.setId(destination.getId());
		timeProjectDto.setProjectId(destination.getProjectId());
		timeProjectDto.setValue(destination.getValue());
		timeProjectDto.setTimesheetRowId(destination.getTimesheetRowId());
		
		return timeProjectDto;
	}
}
