package com.example.Timesheet.com.mapper;

import org.mapstruct.Mapper;

import com.example.Timesheet.com.dto.TimeProjectDto;
import com.example.Timesheet.com.model.TimeProject;

@Mapper
public interface ITimeProjectMapper {

	TimeProject DtoToTimeProject(TimeProjectDto source, int id);
	TimeProjectDto TimeProjectToDto(TimeProject destination);
}
