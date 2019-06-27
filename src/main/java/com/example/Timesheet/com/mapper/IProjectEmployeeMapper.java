package com.example.Timesheet.com.mapper;

import com.example.Timesheet.com.dto.ProjectEmployeeDto;
import com.example.Timesheet.com.model.ProjectEmployee;

public interface IProjectEmployeeMapper {

	ProjectEmployee dtoToProjectEmployee(ProjectEmployeeDto source, int id);

	ProjectEmployeeDto ProjectEmployeeToDto(ProjectEmployee destination);
	

}
