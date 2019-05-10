package com.example.Timesheet.com.mapper;

import org.mapstruct.Mapper;

import com.example.Timesheet.com.dto.ProjectDto;
import com.example.Timesheet.com.model.Project;

@Mapper
public interface IProjectMapper {
	Project dtoToProject(ProjectDto source, int id);
	 ProjectDto projectToDto(Project destination);
}
