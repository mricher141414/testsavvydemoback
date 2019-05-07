package com.example.Timesheet.com.mapper;

import org.mapstruct.Mapper;

import com.example.Timesheet.com.dto.TimesheetStatusDTO;
import com.example.Timesheet.com.model.TimesheetStatus;



@Mapper
public interface ITimesheetStatusMapper {
	TimesheetStatus DTOtoTimesheetStatus(TimesheetStatusDTO source, int id);
	TimesheetStatusDTO TimesheetStatusToDTO(TimesheetStatus destination);

}
