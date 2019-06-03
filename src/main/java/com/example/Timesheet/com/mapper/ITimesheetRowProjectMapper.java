package com.example.Timesheet.com.mapper;

import org.mapstruct.Mapper;

import com.example.Timesheet.com.dto.TimesheetRowProjectDto;
import com.example.Timesheet.com.model.TimesheetRowProject;

@Mapper
public interface ITimesheetRowProjectMapper {

	TimesheetRowProject dtoToTimesheetRowProject(TimesheetRowProjectDto source, int id);
	TimesheetRowProjectDto timesheetRowProjectToDto(TimesheetRowProject destination);
}
