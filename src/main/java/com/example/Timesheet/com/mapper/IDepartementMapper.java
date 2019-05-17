package com.example.Timesheet.com.mapper;

import org.mapstruct.Mapper;

import com.example.Timesheet.com.dto.DepartementDto;
import com.example.Timesheet.com.model.Departement;

@Mapper
public interface IDepartementMapper {
	
	Departement DTOtoDepartement(DepartementDto source, int id);
	DepartementDto DepartementToDTO(Departement destination);

}
