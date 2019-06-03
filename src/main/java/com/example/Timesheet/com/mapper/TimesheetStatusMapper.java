package com.example.Timesheet.com.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Timesheet.com.dto.TimesheetStatusDto;
import com.example.Timesheet.com.model.TimesheetStatus;
import com.example.Timesheet.com.service.TimesheetStatusService;

@Component
public class TimesheetStatusMapper implements ITimesheetStatusMapper {
	
	@Autowired
	private TimesheetStatusService timesheetStatusService;
	
	@Override
    public TimesheetStatus DTOtoTimesheetStatus(TimesheetStatusDto source, int id) {
        if ( source == null ) {
            return null;
        }

        TimesheetStatus timesheetStatus = new TimesheetStatus();
        
        Optional<TimesheetStatus> optionalTimesheetStatus = timesheetStatusService.getById(id); 

        if(optionalTimesheetStatus.isPresent()) {
        	timesheetStatus = optionalTimesheetStatus.get();
        }
        
        timesheetStatus.setId( id );
        
        if(source.getName() != null) {
        	timesheetStatus.setName( source.getName() );
        }
        
        return timesheetStatus;
    }

    @Override
    public TimesheetStatusDto TimesheetStatusToDTO(TimesheetStatus destination) {
        if ( destination == null ) {
            return null;
        }

        TimesheetStatusDto timesheetStatusDTO = new TimesheetStatusDto();

        timesheetStatusDTO.setId( destination.getId() );
        timesheetStatusDTO.setName( destination.getName() );

        return timesheetStatusDTO;
    }
}
