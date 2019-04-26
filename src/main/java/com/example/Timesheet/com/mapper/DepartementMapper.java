package com.example.Timesheet.com.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Timesheet.com.dto.DepartementDTO;
import com.example.Timesheet.com.model.Departement;
import com.example.Timesheet.com.service.DepartementService;

@Component
public class DepartementMapper implements IDepartementMapper {
	
	@Autowired
	private DepartementService departementService;
	
	@Override
    public Departement DTOtoDepartement(DepartementDTO source, int id) {
        if ( source == null ) {
            return null;
        }

        Departement departement = new Departement();

        departement.setId( id );
        departement.setName( source.getName() );
        
        Optional<Departement> optionalDepartement = this.departementService.getById(id);
        
        if(optionalDepartement.isPresent()) {
        	Departement dbDepartement = optionalDepartement.get();
        	
        	if(departement.getName() == null) {
        		departement.setName(dbDepartement.getName());
        	}
        }

        return departement;
    }

    @Override
    public DepartementDTO DepartementToDTO(Departement destination) {
        if ( destination == null ) {
            return null;
        }

        DepartementDTO departementDTO = new DepartementDTO();

        departementDTO.setId( destination.getId() );
        departementDTO.setName( destination.getName() );

        return departementDTO;
    }
}
