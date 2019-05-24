package com.example.Timesheet.com.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Timesheet.com.dto.TimesheetRowDto;
import com.example.Timesheet.com.dto.TimesheetRowTimeProject;
import com.example.Timesheet.com.model.TimesheetRow;
import com.example.Timesheet.com.service.TimeProjectService;
import com.example.Timesheet.com.service.TimesheetRowService;

@Component
public class TimesheetRowMapper implements ITimesheetRowMapper {

    @Autowired
    private TimesheetRowService timesheetRowService;
    
    @Autowired
    private TimeProjectService timeProjectService;
	
	@Override
    public TimesheetRow DTOtoTimesheetRow(TimesheetRowDto source, int id) {
        if ( source == null ) {
            return null;
        }

        TimesheetRow timesheetRow = new TimesheetRow();
        
        timesheetRow.setId( id );
        timesheetRow.setDate( source.getDate() );
        timesheetRow.setTimesheetId( source.getTimesheetId() );
        
        Optional<TimesheetRow> optionalTimesheetRow = timesheetRowService.getById(id);
        
        if(optionalTimesheetRow.isPresent()) {
        	TimesheetRow dbTimesheetRow = optionalTimesheetRow.get();
        	
        	if(timesheetRow.getDate() == null) {
        		timesheetRow.setDate(dbTimesheetRow.getDate());
        	}
        	
        	if(timesheetRow.getTimesheetId() == null) {
        		timesheetRow.setTimesheetId(dbTimesheetRow.getTimesheetId());
        	}
        }
        
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

    public TimesheetRowTimeProject rowDtoToRowTimeProject(TimesheetRowDto rowDto) {
    	
    	TimesheetRowTimeProject rowTimeProject = new TimesheetRowTimeProject();
    	
    	rowTimeProject.setId(rowDto.getId());
    	rowTimeProject.setDate(rowDto.getDate());
    	rowTimeProject.setTimesheetId(rowDto.getTimesheetId());
    	
    	rowTimeProject.setTimeProjects(timeProjectService.getByTimesheetRowId(rowDto.getId()));
    	
    	return rowTimeProject;
    }
    
    public TimesheetRow timesheetRowTimeProjectToTimesheetRow(TimesheetRowTimeProject rowTimeProject) {
    	
    	TimesheetRow row = new TimesheetRow();
    	
    	row.setId(rowTimeProject.getId());
    	row.setDate(rowTimeProject.getDate());
    	row.setTimesheetId(rowTimeProject.getTimesheetId());
    	
    	return row;
    }
}
