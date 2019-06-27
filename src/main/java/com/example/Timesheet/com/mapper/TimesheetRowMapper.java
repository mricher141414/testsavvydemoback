package com.example.Timesheet.com.mapper;

import java.io.Serializable;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Timesheet.com.dto.TimesheetRowDto;
import com.example.Timesheet.com.dto.TimesheetRowWithProject;
import com.example.Timesheet.com.model.TimesheetRow;
import com.example.Timesheet.com.service.TimesheetRowProjectService;
import com.example.Timesheet.com.service.TimesheetRowService;

@Component
public class TimesheetRowMapper implements ITimesheetRowMapper, Serializable {

	private static final long serialVersionUID = -8868832302221973805L;
	private static final Logger log = LogManager.getLogger(TimesheetRowMapper.class);

	@Autowired
    private TimesheetRowService timesheetRowService;
    
    @Autowired
    private TimesheetRowProjectService timeProjectService;
	
	@Override
    public TimesheetRow dtoToTimesheetRow(TimesheetRowDto source, int id) {
		log.debug("Entering dtoToTimesheetRow with id parameter of " + id);
		
		if ( source == null ) {
            return null;
        }

        TimesheetRow timesheetRow = new TimesheetRow();
        
        Optional<TimesheetRow> optionalTimesheetRow = timesheetRowService.getById(id);
        
        if(optionalTimesheetRow.isPresent()) {
        	timesheetRow = optionalTimesheetRow.get();
        }
        
        timesheetRow.setId( id );
    
    	if(source.getDate() != null) {
    		timesheetRow.setDate(source.getDate());
    	}
    	
    	if(source.getTimesheetId() != null) {
    		timesheetRow.setTimesheetId(source.getTimesheetId());
    	}

        return timesheetRow;
    }

    @Override
    public TimesheetRowDto timesheetRowToDto(TimesheetRow destination) {
    	log.debug("Entering timesheetRowToDto");
    	
    	if ( destination == null ) {
            return null;
        }

        TimesheetRowDto timesheetRowDTO = new TimesheetRowDto();

        timesheetRowDTO.setId( destination.getId() );
        timesheetRowDTO.setDate( destination.getDate() );
        timesheetRowDTO.setTimesheetId( destination.getTimesheetId() );

        return timesheetRowDTO;
    }

    public TimesheetRowWithProject rowDtoToRowWithProject(TimesheetRowDto rowDto) {
    	log.debug("Entering rowDtoToRowWithProject");
    	
    	TimesheetRowWithProject rowTimeProject = new TimesheetRowWithProject();
    	
    	rowTimeProject.setId(rowDto.getId());
    	rowTimeProject.setDate(rowDto.getDate());
    	rowTimeProject.setTimesheetId(rowDto.getTimesheetId());
    	
    	rowTimeProject.setTimesheetRowProjects(timeProjectService.getByTimesheetRowId(rowDto.getId()));
    	
    	return rowTimeProject;
    }
    
    public TimesheetRow timesheetRowWithProjectToTimesheetRow(TimesheetRowWithProject rowTimeProject) {
    	log.debug("Entering timesheetRowWithProjectToTimesheetRow");
    	
    	TimesheetRow row = new TimesheetRow();
    	
    	row.setId(rowTimeProject.getId());
    	row.setDate(rowTimeProject.getDate());
    	row.setTimesheetId(rowTimeProject.getTimesheetId());
    	
    	return row;
    }
}
