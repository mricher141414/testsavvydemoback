package com.example.Timesheet.com.mapper;

import org.mapstruct.Mapper;

import com.example.Timesheet.com.dto.TimesheetDTO;
import com.example.Timesheet.com.model.Timesheet;

@Mapper
public interface ITimesheetMapper {
	
	Timesheet DTOtoTimesheet(TimesheetDTO source, int id);
	TimesheetDTO timesheetToDTO(Timesheet destination);

}
