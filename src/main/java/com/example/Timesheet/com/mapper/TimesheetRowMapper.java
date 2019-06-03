package com.example.Timesheet.com.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Timesheet.com.dto.TimesheetRowDto;
import com.example.Timesheet.com.dto.TimesheetRowWithProject;
import com.example.Timesheet.com.model.TimesheetRow;
import com.example.Timesheet.com.service.TimesheetRowProjectService;
import com.example.Timesheet.com.service.TimesheetRowService;

@Component
public class TimesheetRowMapper implements ITimesheetRowMapper {

    @Autowired
    private TimesheetRowService timesheetRowService;
    
    @Autowired
    private TimesheetRowProjectService timeProjectService;
	
	@Override
    public TimesheetRow DTOtoTimesheetRow(TimesheetRowDto source, int id) {
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

    public TimesheetRowWithProject rowDtoToRowTimeProject(TimesheetRowDto rowDto) {
    	
    	TimesheetRowWithProject rowTimeProject = new TimesheetRowWithProject();
    	
    	rowTimeProject.setId(rowDto.getId());
    	rowTimeProject.setDate(rowDto.getDate());
    	rowTimeProject.setTimesheetId(rowDto.getTimesheetId());
    	
    	rowTimeProject.setTimeProjects(timeProjectService.getByTimesheetRowId(rowDto.getId()));
    	
    	return rowTimeProject;
    }
    
    public TimesheetRow timesheetRowTimeProjectToTimesheetRow(TimesheetRowWithProject rowTimeProject) {
    	
    	TimesheetRow row = new TimesheetRow();
    	
    	row.setId(rowTimeProject.getId());
    	row.setDate(rowTimeProject.getDate());
    	row.setTimesheetId(rowTimeProject.getTimesheetId());
    	
    	return row;
    }
}
