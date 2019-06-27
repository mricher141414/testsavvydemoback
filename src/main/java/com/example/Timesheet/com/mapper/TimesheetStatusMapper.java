package com.example.Timesheet.com.mapper;

import java.io.Serializable;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Timesheet.com.dto.TimesheetStatusDto;
import com.example.Timesheet.com.model.TimesheetStatus;
import com.example.Timesheet.com.service.TimesheetStatusService;

@Component
public class TimesheetStatusMapper implements ITimesheetStatusMapper, Serializable {

	private static final long serialVersionUID = 7560521620023703172L;
	private static final Logger log = LogManager.getLogger(TimesheetStatusMapper.class);
	
	@Autowired
	private TimesheetStatusService timesheetStatusService;
	
	@Override
    public TimesheetStatus dtoToTimesheetStatus(TimesheetStatusDto source, int id) {
		log.debug("Entering dtoToTimesheetStatus with id parameter of " + id);
		
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
    public TimesheetStatusDto timesheetStatusToDto(TimesheetStatus destination) {
    	log.debug("Entering timesheetStatusToDto");
    	
    	if ( destination == null ) {
            return null;
        }

        TimesheetStatusDto timesheetStatusDTO = new TimesheetStatusDto();

        timesheetStatusDTO.setId( destination.getId() );
        timesheetStatusDTO.setName( destination.getName() );

        return timesheetStatusDTO;
    }
}
