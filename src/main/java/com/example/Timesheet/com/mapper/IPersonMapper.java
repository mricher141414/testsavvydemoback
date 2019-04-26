package com.example.Timesheet.com.mapper;

import org.mapstruct.Mapper;

import com.example.Timesheet.com.dto.PersonDTO;
import com.example.Timesheet.com.model.Person;

@Mapper
public interface IPersonMapper {
	Person DTOtoPerson(PersonDTO source, int id);
	PersonDTO personToDTO(Person destination);

}
