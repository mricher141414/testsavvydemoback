package com.example.Timesheet.com.mapper;

import com.example.Timesheet.com.dto.TimesheetRowDTO;
import com.example.Timesheet.com.model.TimesheetRow;

public class TimesheetRowMapper implements ITimesheetRowMapper {

    @Override
    public TimesheetRow DTOtoTimesheetRow(TimesheetRowDTO source) {
        if ( source == null ) {
            return null;
        }

        TimesheetRow timesheetRow = new TimesheetRow();

        timesheetRow.setValue( source.getValue() );
        timesheetRow.setId( source.getId() );
        timesheetRow.setDate( source.getDate() );
        timesheetRow.setTimesheetId( source.getTimesheetId() );
        timesheetRow.setProjectId( source.getProjectId() );

        return timesheetRow;
    }

    @Override
    public TimesheetRowDTO TimesheetRowToDTO(TimesheetRow destination) {
        if ( destination == null ) {
            return null;
        }

        TimesheetRowDTO timesheetRowDTO = new TimesheetRowDTO();

        timesheetRowDTO.setValue( destination.getValue() );
        timesheetRowDTO.setId( destination.getId() );
        timesheetRowDTO.setDate( destination.getDate() );
        timesheetRowDTO.setTimesheetId( destination.getTimesheetId() );
        timesheetRowDTO.setProjectId( destination.getProjectId() );

        return timesheetRowDTO;
    }

}
