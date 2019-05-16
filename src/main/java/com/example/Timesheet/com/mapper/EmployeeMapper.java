package com.example.Timesheet.com.mapper;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Timesheet.com.GlobalMessages;
import com.example.Timesheet.com.dto.EmployeeComplex;
import com.example.Timesheet.com.dto.EmployeeComplexWithManager;
import com.example.Timesheet.com.dto.EmployeeDto;
import com.example.Timesheet.com.dto.TimesheetComplex;
import com.example.Timesheet.com.model.Employee;
import com.example.Timesheet.com.model.Timesheet;
import com.example.Timesheet.com.service.DepartementService;
import com.example.Timesheet.com.service.EmployeeService;
import com.example.Timesheet.com.service.RoleService;
import com.example.Timesheet.com.service.TimesheetService;

@Component
public class EmployeeMapper implements IEmployeeMapper {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private DepartementService departementService;
	
	@Autowired
	private TimesheetMapper timesheetMapper;
	
	@Autowired
	private TimesheetService timesheetService;
	
	@Override
    public Employee dtoToEmployee(EmployeeDto source, int id) {
        if ( source == null ) {
            return null;
        }

        Employee employee = new Employee();

        employee.setId( id );
        employee.setDateOfBirth( source.getDateOfBirth() );
        employee.setEmail( source.getEmail() );
        employee.setLastName( source.getLastName() );
        employee.setFirstName( source.getFirstName() );
        employee.setPassword( source.getPassword() );
        employee.setRoleId( source.getRoleId() );
        employee.setDepartementId( source.getDepartementId() );
        employee.setManagerId( source.getManagerId() );
        employee.setAddress( source.getAddress() );
        employee.setSalary(source.getSalary());
        
        Optional<Employee> optionalEmployee = this.employeeService.getById(id);
        
        if(optionalEmployee.isPresent()) {
        	Employee dbEmployee = optionalEmployee.get();
        	
        	if(employee.getDateOfBirth() == null) {
        		employee.setDateOfBirth(dbEmployee.getDateOfBirth());
        	}
        	
        	if(employee.getEmail() == null) {
        		employee.setEmail(dbEmployee.getEmail());
        	}
        	
        	if(employee.getLastName() == null) {
        		employee.setLastName(dbEmployee.getLastName());
        	}
        	
        	if(employee.getFirstName() == null) {
        		employee.setFirstName(dbEmployee.getFirstName());
        	}
        	
        	if(employee.getPassword() == null) {
        		employee.setPassword(dbEmployee.getPassword());
        	}
        	
        	if(employee.getRoleId() == null) {
        		employee.setRoleId(dbEmployee.getRoleId());
        	}
        	
        	if(employee.getDepartementId() == null) {
        		employee.setDepartementId(dbEmployee.getDepartementId());
        	}
        	
        	if(employee.getManagerId() == null) {
        		employee.setManagerId(dbEmployee.getManagerId());
        	}
        	
        	if(employee.getAddress() == null) {
        		employee.setAddress(dbEmployee.getAddress());
        	}
        	
        	if(employee.getSalary() == 0) {
        		employee.setSalary(dbEmployee.getSalary());
        	}
        }
        
        employee.compensateTimezoneOnDates();

        return employee;
    }

    @Override
    public EmployeeDto employeeToDto(Employee destination) {
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
        employeeDto.setDepartementId( destination.getDepartementId() );
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

		if(employee.getDepartementId() != null) {
			employeeComplex.setDepartement(this.departementService.getById(employee.getDepartementId()).get());
		}

	}
	
	public void addTimesheetsToEmployeeComplexByStartDate(EmployeeComplex employee, Date startDate) {
		List<Timesheet> timesheetsEmployee = timesheetService.getTimesheetByEmployeeIdAndStartDate(employee.getId(), startDate);
		
		for (Timesheet timesheet : timesheetsEmployee) {
			TimesheetComplex timesheetComplex = timesheetMapper.fromTimesheetToComplex(timesheet);
			employee.addToTimesheets(timesheetComplex);
		}
	}
	
	public EmployeeComplexWithManager addManagerToEmployeeComplexWithManager(EmployeeComplexWithManager employeeComplex, Employee employee) {
		employeeComplex.setManager(this.employeeService.getById(employee.getManagerId()).get());
		return employeeComplex;
	}
}
