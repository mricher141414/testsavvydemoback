package com.example.Timesheet.com.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import com.example.Timesheet.com.dto.TimesheetComplex;
import com.example.Timesheet.com.dto.TimesheetDto;
import com.example.Timesheet.com.dto.TimesheetRowDto;
import com.example.Timesheet.com.dto.TimesheetRowWithProject;
import com.example.Timesheet.com.model.Timesheet;
import com.example.Timesheet.com.model.TimesheetRow;
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
	
	@Autowired
	private TimesheetRowMapper timesheetRowMapper;
	
    @Override
    public Timesheet DTOtoTimesheet(TimesheetDto source, int id) {
        if ( source == null ) {
            return null;
        }

        Timesheet timesheet = new Timesheet();

        timesheet.setId( id );
        
        Optional<Timesheet> optionalTimesheet = this.timesheetService.getById(id);
        
        if(optionalTimesheet.isPresent()) {
        	timesheet = optionalTimesheet.get();
        }
        	
    	if(source.getTimesheetStatusId() != null) {
    		timesheet.setTimesheetStatusId(source.getTimesheetStatusId());
    	}
    	
    	if(source.getEmployeeId() != null) {
    		timesheet.setEmployeeId(source.getEmployeeId());
    	}
    	
    	if(source.getTotal() != null) {
    		timesheet.setTotal(source.getTotal());
    	}
    	
    	if(source.getNotes() != null) {
    		timesheet.setNotes(source.getNotes());
    	}
    	
    	if(source.getStartDate() != null) {
    		timesheet.setStartDate(source.getStartDate());
    	}
    	
    	if(source.getEndDate() != null) {
    		timesheet.setEndDate(source.getEndDate());
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
		List<TimesheetRowWithProject> timesheetRowTimeProjects = new ArrayList<TimesheetRowWithProject>();

		timesheetComplex.setId(timesheet.getId());
		timesheetComplex.setTotal(timesheet.getTotal());
		timesheetComplex.setEndDate(timesheet.getEndDate());
		timesheetComplex.setNotes(timesheet.getNotes());
		timesheetComplex.setStartDate(timesheet.getStartDate());
		
		List<TimesheetRow> rows = timesheetRowService.getByTimesheetId(timesheet.getId());
		
		for (TimesheetRow row : rows) {
			TimesheetRowDto rowDto = timesheetRowMapper.TimesheetRowToDTO(row);
			TimesheetRowWithProject rowTimeProject = timesheetRowMapper.rowDtoToRowTimeProject(rowDto);
			
			timesheetRowTimeProjects.add(rowTimeProject);
		}
		
		timesheetComplex.setTimesheetRows(timesheetRowTimeProjects);

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
