package com.example.Timesheet.com.mapper;

import org.mapstruct.Mapper;

import com.example.Timesheet.com.dto.TimesheetRowDTO;
import com.example.Timesheet.com.model.TimesheetRow;

@Mapper
public interface ITimesheetRowMapper {
	 TimesheetRow DTOtoTimesheetRow(TimesheetRowDTO source, int id);
	 TimesheetRowDTO TimesheetRowToDTO(TimesheetRow destination);

}
