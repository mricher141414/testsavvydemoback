package com.example.Timesheet.com.mapper;

import org.mapstruct.Mapper;

import com.example.Timesheet.com.dto.EmployeeDto;
import com.example.Timesheet.com.model.Employee;

@Mapper
public interface IEmployeeMapper {
	Employee dtoToEmployee(EmployeeDto source, int id);
	EmployeeDto employeeToDto(Employee destination);

}
