package com.example.Timesheet.com.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import com.example.Timesheet.com.dto.TimesheetComplex;
import com.example.Timesheet.com.dto.TimesheetDto;
import com.example.Timesheet.com.model.Timesheet;
import com.example.Timesheet.com.service.TimesheetRowService;
import com.example.Timesheet.com.service.TimesheetService;
import com.example.Timesheet.com.service.TimesheetStatusService;

@Component
public class TimesheetMapper implements ITimesheetMapper {
	
	@Autowired
	private TimesheetService timesheetService;
	
	@Autowired
	private TimesheetRowService timesheetRowService;
	
	@Autowired
	private TimesheetStatusService timesheetStatusService;
	
    @Override
    public Timesheet DTOtoTimesheet(TimesheetDto source, int id) {
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
        
        Optional<Timesheet> optionalTimesheet = this.timesheetService.getById(id);
        
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
    public TimesheetDto timesheetToDTO(Timesheet destination) {
        if ( destination == null ) {
            return null;
        }

        TimesheetDto timesheetDTO = new TimesheetDto();

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
    
    public TimesheetComplex fromTimesheetToComplex(Timesheet timesheet) {

		TimesheetComplex timesheetComplex = new TimesheetComplex();


		timesheetComplex.setId(timesheet.getId());
		timesheetComplex.setTotal(timesheet.getTotal());
		timesheetComplex.setEndDate(timesheet.getEndDate());
		timesheetComplex.setNotes(timesheet.getNotes());
		timesheetComplex.setStartDate(timesheet.getStartDate());
		timesheetComplex.setTimesheetRows(this.timesheetRowService.getByTimesheetId(timesheet.getId()));

		if(timesheet.getTimesheetStatusId()!= null) {
			timesheetComplex.setTimesheetStatus(timesheetStatusService.getById(timesheet.getTimesheetStatusId()).get());

		}
		return timesheetComplex;
	}
    
    public Timesheet fromComplexToTimesheet(TimesheetComplex timesheetComplex, int employeeId) {
    	Timesheet timesheet = new Timesheet();
    	
    	timesheet.setId(timesheetComplex.getId());
    	timesheet.setTotal(timesheetComplex.getTotal());
    	timesheet.setEndDate(timesheetComplex.getEndDate());
    	timesheet.setNotes(timesheetComplex.getNotes());
    	timesheet.setStartDate(timesheetComplex.getStartDate());
    	timesheet.setTimesheetStatusId(timesheetComplex.getTimesheetStatus().getId());
    	timesheet.setEmployeeId(employeeId);
    	
    	return timesheet;
    }
}
