package com.example.Timesheet.com.mapper;

import com.example.Timesheet.com.dto.ProjectEmployeeDto;
import com.example.Timesheet.com.model.ProjectEmployee;

public interface IProjectEmployeeMapper {

	ProjectEmployee DtoToProjectEmployee(ProjectEmployeeDto source, int id);

	ProjectEmployeeDto ProjectEmployeeToDto(ProjectEmployee destination);
	

}
