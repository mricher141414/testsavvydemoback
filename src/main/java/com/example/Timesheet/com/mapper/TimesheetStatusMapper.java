package com.example.Timesheet.com.mapper;

import com.example.Timesheet.com.dto.TimesheetStatusDTO;
import com.example.Timesheet.com.model.TimesheetStatus;

public class TimesheetStatusMapper implements ITimesheetStatusMapper {
	
	 @Override
	    public TimesheetStatus DTOtoTimesheetStatus(TimesheetStatusDTO source, int id) {
	        if ( source == null ) {
	            return null;
	        }

	        TimesheetStatus timesheetStatus = new TimesheetStatus();

	        timesheetStatus.setId( id );
	        timesheetStatus.setName( source.getName() );

	        return timesheetStatus;
	    }

	    @Override
	    public TimesheetStatusDTO TimesheetStatusToDTO(TimesheetStatus destination) {
	        if ( destination == null ) {
	            return null;
	        }

	        TimesheetStatusDTO timesheetStatusDTO = new TimesheetStatusDTO();

	        timesheetStatusDTO.setId( destination.getId() );
	        timesheetStatusDTO.setName( destination.getName() );

	        return timesheetStatusDTO;
	    }
}
