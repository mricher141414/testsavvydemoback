package com.example.Timesheet.com.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import com.example.Timesheet.com.dto.TimesheetDTO;
import com.example.Timesheet.com.model.Timesheet;
import com.example.Timesheet.com.service.TimesheetService;

@Component
public class TimesheetMapper implements ITimesheetMapper {
	
	@Autowired
	private TimesheetService timesheetService;
	
    @Override
    public Timesheet DTOtoTimesheet(TimesheetDTO source, int id) {
        if ( source == null ) {
            return null;
        }

        Timesheet timesheet = new Timesheet();

        timesheet.setId( id );
        timesheet.setTimesheetStatusId( source.getTimesheetStatusId() );
        timesheet.setEmployeeId( source.getEmployeeId() );
        timesheet.setTotal( source.getTotal() );
        timesheet.setNotes( source.getNotes() );
        timesheet.setStartDate( source.getStartDate() );
        timesheet.setEndDate( source.getEndDate() );
        
        Optional<Timesheet> optionalTimesheet = this.timesheetService.getTimesheetById(id);
        
        if(optionalTimesheet.isPresent()) {
        	Timesheet dbTimesheet = optionalTimesheet.get();
        	
        	if(timesheet.getTimesheetStatusId() == null) {
        		timesheet.setTimesheetStatusId(dbTimesheet.getTimesheetStatusId());
        	}
        	
        	if(timesheet.getEmployeeId() == null) {
        		timesheet.setEmployeeId(dbTimesheet.getEmployeeId());
        	}
        	
        	if(timesheet.getTotal() == 0) {
        		timesheet.setTotal(dbTimesheet.getTotal());
        	}
        	
        	if(timesheet.getNotes() == null) {
        		timesheet.setNotes(dbTimesheet.getNotes());
        	}
        	
        	if(timesheet.getStartDate() == null) {
        		timesheet.setStartDate(dbTimesheet.getStartDate());
        	}
        	
        	if(timesheet.getEndDate() == null) {
        		timesheet.setEndDate(dbTimesheet.getEndDate());
        	}
        }
        
        timesheet.compensateTimezoneOnDates();
        
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
