package com.example.Timesheet.com.mapper;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Timesheet.com.dto.EmployeeComplex;
import com.example.Timesheet.com.dto.EmployeeComplexWithManager;
import com.example.Timesheet.com.dto.EmployeeDto;
import com.example.Timesheet.com.dto.TimesheetComplex;
import com.example.Timesheet.com.model.Employee;
import com.example.Timesheet.com.model.Timesheet;
import com.example.Timesheet.com.service.DepartmentService;
import com.example.Timesheet.com.service.EmployeeService;
import com.example.Timesheet.com.service.RoleService;
import com.example.Timesheet.com.service.TimesheetService;

@Component
public class EmployeeMapper implements IEmployeeMapper, Serializable {
	
	private static final long serialVersionUID = -1499901430978397355L;
	private static final Logger log = LogManager.getLogger(EmployeeMapper.class);

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private DepartmentService departementService;
	
	@Autowired
	private TimesheetMapper timesheetMapper;
	
	@Autowired
	private TimesheetService timesheetService;
	
	@Override
    public Employee dtoToEmployee(EmployeeDto source, int id) {
		log.debug("Entering dtoToEmployee with id parameter of " + id);
		
		if ( source == null ) {
            return null;
        }

        Employee employee = new Employee();
        
        Optional<Employee> optionalEmployee = this.employeeService.getById(id);
        
        if(optionalEmployee.isPresent()) {
        	employee = optionalEmployee.get();
        }
        
        employee.setId(id);
    	
    	if(source.getDateOfBirth() != null) {
    		employee.setDateOfBirth(source.getDateOfBirth());
    	}
    	
    	if(source.getEmail() != null) {
    		employee.setEmail(source.getEmail());
    	}
    	
    	if(source.getLastName() != null) {
    		employee.setLastName(source.getLastName());
    	}
    	
    	if(source.getFirstName() != null) {
    		employee.setFirstName(source.getFirstName());
    	}
    	
    	if(source.getPassword() != null) {
    		employee.setPassword(source.getPassword());
    	}
    	
    	if(source.getRoleId() != null) {
    		employee.setRoleId(source.getRoleId());
    	}
    	
    	if(source.getDepartmentId() != null) {
    		employee.setDepartmentId(source.getDepartmentId());
    	}
    	
    	if(source.getManagerId() != null) {
    		employee.setManagerId(source.getManagerId());
    	}
    	
    	if(source.getAddress() != null) {
    		employee.setAddress(source.getAddress());
    	}
    	
    	if(source.getSalary() != null) {
    		employee.setSalary(source.getSalary());
    	}
        
        employee.compensateTimezoneOnDates();

        return employee;
    }

    @Override
    public EmployeeDto employeeToDto(Employee destination) {
    	log.debug("Entering employeeToDto");
    	
    	if ( destination == null ) {
            return null;
        }

        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setDateOfBirth( destination.getDateOfBirth() );
        employeeDto.setId( destination.getId() );
        employeeDto.setEmail( destination.getEmail() );
        employeeDto.setLastName( destination.getLastName() );
        employeeDto.setFirstName( destination.getFirstName() );
        employeeDto.setPassword( destination.getPassword() );
        employeeDto.setRoleId( destination.getRoleId() );
        employeeDto.setDepartmentId( destination.getDepartmentId() );
        employeeDto.setManagerId( destination.getManagerId() );
        employeeDto.setAddress( destination.getAddress() );
        employeeDto.setSalary(destination.getSalary());

        return employeeDto;
    }

    public void fromEmployeeToComplex(Employee employee, EmployeeComplex employeeComplex) {
		employeeComplex.setId(employee.getId());
		employeeComplex.setLastName(employee.getLastName());
		employeeComplex.setFirstName(employee.getFirstName());
		employeeComplex.setAddress(employee.getAddress());
		employeeComplex.setEmailAddress(employee.getEmail());
		employeeComplex.setPassword(employee.getPassword());
		employeeComplex.setDateOfBirth(employee.getDateOfBirth());
		employeeComplex.setSalary(employee.getSalary());

		if(employee.getRoleId()!= null) {
			employeeComplex.setRole(this.roleService.getById(employee.getRoleId()).get());
		}

		if(employee.getDepartmentId() != null) {
			employeeComplex.setDepartment(this.departementService.getById(employee.getDepartmentId()).get());
		}

	}
	
	public void addTimesheetsToEmployeeComplexByStartDate(EmployeeComplex employee, Date startDate) {
		List<Timesheet> timesheetsEmployee = timesheetService.getByEmployeeIdAndStartDate(employee.getId(), startDate);
		
		for (Timesheet timesheet : timesheetsEmployee) {
			TimesheetComplex timesheetComplex = new TimesheetComplex();
			timesheetMapper.fromTimesheetToComplex(timesheet, timesheetComplex);
			employee.addToTimesheets(timesheetComplex);
		}
	}
	
	public EmployeeComplexWithManager addManagerToEmployeeComplexWithManager(EmployeeComplexWithManager employeeComplex, Employee employee) {
		employeeComplex.setManager(this.employeeService.getById(employee.getManagerId()).get());
		return employeeComplex;
	}
}
