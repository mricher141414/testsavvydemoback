package com.example.Timesheet.com.mapper;

import com.example.Timesheet.com.dto.TimesheetDTO;
import com.example.Timesheet.com.model.Timesheet;

public class TimesheetMapper implements ITimesheetMapper {
	
    @Override
    public Timesheet DTOtoTimesheet(TimesheetDTO source) {
        if ( source == null ) {
            return null;
        }

        Timesheet timesheet = new Timesheet();

        timesheet.setEmployeeId( source.getEmployeeId() );
        timesheet.setTimesheetStatusId( source.getTimesheetStatusId() );
        timesheet.setId( source.getId() );
        timesheet.setTotal( source.getTotal() );
        timesheet.setNotes( source.getNotes() );
        timesheet.setStartDate( source.getStartDate() );
        timesheet.setEndDate( source.getEndDate() );

        return timesheet;
    }

    @Override
    public TimesheetDTO timesheetToDTO(Timesheet destination) {
        if ( destination == null ) {
            return null;
        }

        TimesheetDTO timesheetDTO = new TimesheetDTO();

        timesheetDTO.setId( destination.getId() );
        timesheetDTO.setTotal( destination.getTotal() );
        timesheetDTO.setNotes( destination.getNotes() );
        timesheetDTO.setStartDate( destination.getStartDate() );
        timesheetDTO.setEndDate( destination.getEndDate() );
        timesheetDTO.setEmployeeId( destination.getEmployeeId() );
        if ( destination.getTimesheetStatusId() != null ) {
            timesheetDTO.setTimesheetStatusId( destination.getTimesheetStatusId() );
        }

        return timesheetDTO;
    }
}
