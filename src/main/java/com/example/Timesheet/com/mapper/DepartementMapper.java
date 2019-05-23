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

        department.setId( id );
        department.setName( source.getName() );
        department.setDescription(source.getDescription());
        
        Optional<Department> optionalDepartement = this.departementService.getById(id);
        
        if(optionalDepartement.isPresent()) {
        	
        	Department dbDepartment = optionalDepartement.get();
        	department.setVersion(dbDepartment.getVersion());
        	
        	if(department.getName() == null) {
        		department.setName(dbDepartment.getName());
        	}
        	
        	if(department.getDescription() == null) {
        		department.setDescription(dbDepartment.getDescription());
        	}
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
