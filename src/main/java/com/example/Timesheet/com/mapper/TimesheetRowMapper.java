package com.example.Timesheet.com.mapper;

import com.example.Timesheet.com.dto.TimesheetRowDto;
import com.example.Timesheet.com.model.TimesheetRow;

public class TimesheetRowMapper implements ITimesheetRowMapper {

    @Override
    public TimesheetRow DTOtoTimesheetRow(TimesheetRowDto source, int id) {
        if ( source == null ) {
            return null;
        }

        TimesheetRow timesheetRow = new TimesheetRow();
        
        timesheetRow.setId( id );
        timesheetRow.setDate( source.getDate() );
        timesheetRow.setTimesheetId( source.getTimesheetId() );
        
        timesheetRow.compensateTimezoneOnDates();

        return timesheetRow;
    }

    @Override
    public TimesheetRowDto TimesheetRowToDTO(TimesheetRow destination) {
        if ( destination == null ) {
            return null;
        }

        TimesheetRowDto timesheetRowDTO = new TimesheetRowDto();

        timesheetRowDTO.setId( destination.getId() );
        timesheetRowDTO.setDate( destination.getDate() );
        timesheetRowDTO.setTimesheetId( destination.getTimesheetId() );

        return timesheetRowDTO;
    }

}
