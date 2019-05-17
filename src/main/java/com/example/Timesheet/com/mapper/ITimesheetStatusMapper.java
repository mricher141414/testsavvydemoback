package com.example.Timesheet.com.mapper;

import org.mapstruct.Mapper;

import com.example.Timesheet.com.dto.TimesheetStatusDto;
import com.example.Timesheet.com.model.TimesheetStatus;



@Mapper
public interface ITimesheetStatusMapper {
	TimesheetStatus DTOtoTimesheetStatus(TimesheetStatusDto source, int id);
	TimesheetStatusDto TimesheetStatusToDTO(TimesheetStatus destination);

}
