package com.example.Timesheet.com.mapper;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Timesheet.com.dto.DepartmentDto;
import com.example.Timesheet.com.model.Department;
import com.example.Timesheet.com.service.DepartmentService;

@Component
public class DepartmentMapper implements IDepartmentMapper, Serializable {
	
	private static final long serialVersionUID = 1630201305884206499L;
	private static final Logger log = LogManager.getLogger(DepartmentMapper.class);
	
	@Autowired
	private DepartmentService departementService;
	
	@Override
    public Department dtoToDepartment(DepartmentDto source, int id) {
		log.debug("Entering dtoToDepartment with id parameter of " + id);
		
		if ( source == null ) {
            return null;
        }

        Department department = new Department();
        
        Optional<Department> optionalDepartment = this.departementService.getById(id);
        
        if(optionalDepartment.isPresent()) {        	
        	department = optionalDepartment.get();
        }
        	
    	if(source.getName() != null) {
    		department.setName(source.getName());
    	}
    	
    	if(source.getDescription() != null) {
    		department.setDescription(source.getDescription());
    	}
    	
    	if(source.getVersion() != null && department.getVersion() != null) {
    		if(source.getVersion() != department.getVersion()) {
				throw new OptimisticLockException("Wrong version");
			}
		}

        return department;
    }

    @Override
    public DepartmentDto departmentToDto(Department destination) {
    	log.debug("Entering departmentToDto");
    	
    	if ( destination == null ) {
            return null;
        }

        DepartmentDto departmentDto = new DepartmentDto();

        departmentDto.setId( destination.getId() );
        departmentDto.setName( destination.getName() );
        departmentDto.setDescription(destination.getDescription());
        departmentDto.setVersion(destination.getVersion());

        return departmentDto;
    }
}
