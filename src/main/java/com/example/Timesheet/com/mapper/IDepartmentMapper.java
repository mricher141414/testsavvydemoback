package com.example.Timesheet.com.mapper;

import org.mapstruct.Mapper;

import com.example.Timesheet.com.dto.DepartmentDto;
import com.example.Timesheet.com.model.Department;

@Mapper
public interface IDepartmentMapper {
	
	Department dtoToDepartment(DepartmentDto source, int id);
	DepartmentDto departmentToDto(Department destination);

}
