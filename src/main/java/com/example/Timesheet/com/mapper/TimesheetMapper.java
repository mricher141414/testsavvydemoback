package com.example.Timesheet.com.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Timesheet.com.dto.TimesheetComplex;
import com.example.Timesheet.com.dto.TimesheetComplexWithEmployee;
import com.example.Timesheet.com.dto.TimesheetDto;
import com.example.Timesheet.com.dto.TimesheetRowDto;
import com.example.Timesheet.com.dto.TimesheetRowWithProject;
import com.example.Timesheet.com.model.Timesheet;
import com.example.Timesheet.com.model.TimesheetRow;
import com.example.Timesheet.com.service.EmployeeService;
import com.example.Timesheet.com.service.TimesheetRowService;
import com.example.Timesheet.com.service.TimesheetService;
import com.example.Timesheet.com.service.TimesheetStatusService;

@Component
public class TimesheetMapper implements ITimesheetMapper, Serializable {
	
	private static final long serialVersionUID = 4403383706852682929L;
	private static final Logger log = LogManager.getLogger(TimesheetMapper.class);
	@Autowired
	private TimesheetService timesheetService;
	
	@Autowired
	private TimesheetRowService timesheetRowService;
	
	@Autowired
	private TimesheetStatusService timesheetStatusService;
	
	@Autowired
	private TimesheetRowMapper timesheetRowMapper;
	
	@Autowired
	private EmployeeService employeeService;
	
    @Override
    public Timesheet dtoToTimesheet(TimesheetDto source, int id) {
    	log.debug("Entering getOne with id parameter of " + id);
    	
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
    public TimesheetDto timesheetToDto(Timesheet destination) {
    	log.debug("Entering timesheetToDto");
    	
    	if ( destination == null ) {
            return null;
        }

        TimesheetDto timesheetDto = new TimesheetDto();

        timesheetDto.setId( destination.getId() );
        timesheetDto.setTotal( destination.getTotal() );
        timesheetDto.setNotes( destination.getNotes() );
        timesheetDto.setStartDate( destination.getStartDate() );
        timesheetDto.setEndDate( destination.getEndDate() );
        timesheetDto.setEmployeeId( destination.getEmployeeId() );
        if ( destination.getTimesheetStatusId() != null ) {
            timesheetDto.setTimesheetStatusId( destination.getTimesheetStatusId() );
        }

        return timesheetDto;
    }
    
    public TimesheetComplex fromTimesheetToComplex(Timesheet timesheet, TimesheetComplex timesheetComplex) {
    	log.debug("Entering fromTimesheetToComplex");
		
    	List<TimesheetRowWithProject> timesheetRowTimeProjects = new ArrayList<TimesheetRowWithProject>();

		timesheetComplex.setId(timesheet.getId());
		timesheetComplex.setTotal(timesheet.getTotal());
		timesheetComplex.setEndDate(timesheet.getEndDate());
		timesheetComplex.setNotes(timesheet.getNotes());
		timesheetComplex.setStartDate(timesheet.getStartDate());
		
		List<TimesheetRow> rows = timesheetRowService.getByTimesheetId(timesheet.getId());
		
		for (TimesheetRow row : rows) {
			TimesheetRowDto rowDto = timesheetRowMapper.timesheetRowToDto(row);
			TimesheetRowWithProject rowTimeProject = timesheetRowMapper.rowDtoToRowWithProject(rowDto);
			
			timesheetRowTimeProjects.add(rowTimeProject);
		}
		
		timesheetComplex.setTimesheetRows(timesheetRowTimeProjects);

		if(timesheet.getTimesheetStatusId()!= null) {
			timesheetComplex.setTimesheetStatus(timesheetStatusService.getById(timesheet.getTimesheetStatusId()).get());

		}
		return timesheetComplex;
	}
    
    public TimesheetComplexWithEmployee addEmployeeToTimesheetComplex(TimesheetComplexWithEmployee timesheetWithEmployee, Timesheet timesheet) {
    	log.debug("Entering addEmployeeToTimesheetComplex");
    	
    	timesheetWithEmployee.setEmployee(employeeService.getById(timesheet.getEmployeeId()).get());
    	return timesheetWithEmployee;
    }
    
    public Timesheet fromComplexToTimesheet(TimesheetComplex timesheetComplex, int employeeId) {
    	log.debug("Entering fromComplexToTimesheet with employeeId parameter of " + employeeId);
    	
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
