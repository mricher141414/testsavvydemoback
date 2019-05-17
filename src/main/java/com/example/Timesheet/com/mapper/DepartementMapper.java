package com.example.Timesheet.com.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Timesheet.com.dto.DepartementDto;
import com.example.Timesheet.com.model.Departement;
import com.example.Timesheet.com.service.DepartementService;

@Component
public class DepartementMapper implements IDepartementMapper {
	
	@Autowired
	private DepartementService departementService;
	
	@Override
    public Departement DTOtoDepartement(DepartementDto source, int id) {
        if ( source == null ) {
            return null;
        }

        Departement departement = new Departement();

        departement.setId( id );
        departement.setName( source.getName() );
        departement.setDescription(source.getDescription());
        
        Optional<Departement> optionalDepartement = this.departementService.getById(id);
        
        if(optionalDepartement.isPresent()) {
        	Departement dbDepartement = optionalDepartement.get();
        	
        	if(departement.getName() == null) {
        		departement.setName(dbDepartement.getName());
        	}
        	
        	if(departement.getDescription() == null) {
        		departement.setDescription(dbDepartement.getDescription());
        	}
        }

        return departement;
    }

    @Override
    public DepartementDto DepartementToDTO(Departement destination) {
        if ( destination == null ) {
            return null;
        }

        DepartementDto departementDTO = new DepartementDto();

        departementDTO.setId( destination.getId() );
        departementDTO.setName( destination.getName() );
        departementDTO.setDescription(destination.getDescription());

        return departementDTO;
    }
}
