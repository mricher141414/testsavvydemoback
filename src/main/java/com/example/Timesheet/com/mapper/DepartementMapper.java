package com.example.Timesheet.com.mapper;

import com.example.Timesheet.com.dto.DepartementDTO;
import com.example.Timesheet.com.model.Departement;

public class DepartementMapper implements IDepartementMapper {
	
	 @Override
	    public Departement DTOtoDepartement(DepartementDTO source) {
	        if ( source == null ) {
	            return null;
	        }

	        Departement departement = new Departement();

	        departement.setId( source.getId() );
	        departement.setName( source.getName() );

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
