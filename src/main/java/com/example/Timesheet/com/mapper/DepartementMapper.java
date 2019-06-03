package com.example.Timesheet.com.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Timesheet.com.dto.DepartmentDto;
import com.example.Timesheet.com.model.Department;
import com.example.Timesheet.com.service.DepartementService;

@Component
public class DepartementMapper implements IDepartementMapper {
	
	@Autowired
	private DepartementService departementService;
	
	@Override
    public Department DTOtoDepartement(DepartmentDto source, int id) {
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

        return department;
    }

    @Override
    public DepartmentDto DepartementToDTO(Department destination) {
        if ( destination == null ) {
            return null;
        }

        DepartmentDto departementDTO = new DepartmentDto();

        departementDTO.setId( destination.getId() );
        departementDTO.setName( destination.getName() );
        departementDTO.setDescription(destination.getDescription());

        return departementDTO;
    }
}
