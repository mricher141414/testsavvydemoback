package com.example.Timesheet.com.mapper;

import com.example.Timesheet.com.dto.PersonDTO;
import com.example.Timesheet.com.model.Person;

public class PersonMapper implements IPersonMapper {
	
	  @Override
	    public Person DTOtoPerson(PersonDTO source) {
	        if ( source == null ) {
	            return null;
	        }

	        Person person = new Person();

	        person.setId( source.getId() );
	        person.setEmail( source.getEmail() );
	        person.setLastName( source.getLastName() );
	        person.setFirstName( source.getFirstName() );
	        person.setPassword( source.getPassword() );
	        person.setRoleId( source.getRoleId() );
	        person.setDepartementId( source.getDepartementId() );
	        person.setManagerId( source.getManagerId() );
	        person.setAddress( source.getAddress() );

	        return person;
	    }

	    @Override
	    public PersonDTO personToDTO(Person destination) {
	        if ( destination == null ) {
	            return null;
	        }

	        PersonDTO personDTO = new PersonDTO();

	        personDTO.setId( destination.getId() );
	        personDTO.setEmail( destination.getEmail() );
	        personDTO.setLastName( destination.getLastName() );
	        personDTO.setFirstName( destination.getFirstName() );
	        personDTO.setPassword( destination.getPassword() );
	        personDTO.setRoleId( destination.getRoleId() );
	        personDTO.setDepartementId( destination.getDepartementId() );
	        personDTO.setManagerId( destination.getManagerId() );
	        personDTO.setAddress( destination.getAddress() );

	        return personDTO;
	    }

}
