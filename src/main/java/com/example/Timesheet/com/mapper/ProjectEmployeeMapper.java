package com.example.Timesheet.com.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Timesheet.com.dto.ProjectEmployeeDto;
import com.example.Timesheet.com.model.ProjectEmployee;
import com.example.Timesheet.com.service.ProjectEmployeeService;

@Component
public class ProjectEmployeeMapper implements IProjectEmployeeMapper {

	@Autowired
	private ProjectEmployeeService projectEmployeeService;
	
	@Override
	public ProjectEmployee DtoToProjectEmployee(ProjectEmployeeDto source, int id) {
		if(source == null) {
			return null;
		}
		
		ProjectEmployee projectEmployee = new ProjectEmployee();
		
		projectEmployee.setId(id);
		projectEmployee.setEmployeeId(source.getEmployeeId());
		projectEmployee.setProjectId(source.getProjectId());
		
		Optional<ProjectEmployee> optionalProjectEmployee = projectEmployeeService.getById(id);
		
		if(optionalProjectEmployee.isPresent()) {
			ProjectEmployee dbProjectEmployee = optionalProjectEmployee.get();
			
			if(projectEmployee.getEmployeeId() == null) {
				projectEmployee.setEmployeeId(dbProjectEmployee.getEmployeeId());
			}
			
			if(projectEmployee.getProjectId() == null) {
				projectEmployee.setProjectId(dbProjectEmployee.getProjectId());
			}
		}
		
		return projectEmployee;
	}
	
	@Override
	public ProjectEmployeeDto ProjectEmployeeToDto(ProjectEmployee destination) {
		if(destination == null) {
			return null;
		}
		
		ProjectEmployeeDto projectEmployeeDto = new ProjectEmployeeDto();
		
		projectEmployeeDto.setId(destination.getId());
		projectEmployeeDto.setEmployeeId(destination.getEmployeeId());
		projectEmployeeDto.setProjectId(destination.getProjectId());
		
		return projectEmployeeDto;
	}
}
