package com.example.Timesheet.com.mapper;

import org.mapstruct.Mapper;

import com.example.Timesheet.com.dto.DepartementDTO;
import com.example.Timesheet.com.model.Departement;

@Mapper
public interface IDepartementMapper {
	
	Departement DTOtoDepartement(DepartementDTO source);
	DepartementDTO DepartementToDTO(Departement destination);

}
