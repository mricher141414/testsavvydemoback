package com.example.Timesheet.com.mapper;

import org.mapstruct.Mapper;

import com.example.Timesheet.com.dto.TimesheetRowDto;
import com.example.Timesheet.com.model.TimesheetRow;

@Mapper
public interface ITimesheetRowMapper {
	 TimesheetRow DTOtoTimesheetRow(TimesheetRowDto source, int id);
	 TimesheetRowDto TimesheetRowToDTO(TimesheetRow destination);

}
