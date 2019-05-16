package com.example.Timesheet.com.mapper;

import org.mapstruct.Mapper;

import com.example.Timesheet.com.dto.TimesheetDto;
import com.example.Timesheet.com.model.Timesheet;

@Mapper
public interface ITimesheetMapper {
	
	Timesheet DTOtoTimesheet(TimesheetDto source, int id);
	TimesheetDto timesheetToDTO(Timesheet destination);

}
