package com.example.Timesheet.com.mapper;

import java.sql.Date;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Timesheet.com.GlobalVars;
import com.example.Timesheet.com.dto.PersonDTO;
import com.example.Timesheet.com.model.Person;
import com.example.Timesheet.com.service.PersonService;

@Component
public class PersonMapper implements IPersonMapper {
	
	@Autowired
	private PersonService personService;
	
	@Override
    public Person DTOtoPerson(PersonDTO source, int id) {
        if ( source == null ) {
            return null;
        }

        Person person = new Person();

        person.setId( id );
        person.setDateOfBirth( source.getDateOfBirth() );
        person.setEmail( source.getEmail() );
        person.setLastName( source.getLastName() );
        person.setFirstName( source.getFirstName() );
        person.setPassword( source.getPassword() );
        person.setRoleId( source.getRoleId() );
        person.setDepartementId( source.getDepartementId() );
        person.setManagerId( source.getManagerId() );
        person.setAddress( source.getAddress() );
        
        Optional<Person> optionalPerson = this.personService.findById(id);
        
        if(optionalPerson.isPresent()) {
        	Person dbPerson = optionalPerson.get();
        	
        	if(person.getDateOfBirth() == null) {
        		person.setDateOfBirth(dbPerson.getDateOfBirth());
        	}
        	
        	if(person.getEmail() == null) {
        		person.setEmail(dbPerson.getEmail());
        	}
        	
        	if(person.getLastName() == null) {
        		person.setLastName(dbPerson.getLastName());
        	}
        	
        	if(person.getFirstName() == null) {
        		person.setFirstName(dbPerson.getFirstName());
        	}
        	
        	if(person.getPassword() == null) {
        		person.setPassword(dbPerson.getPassword());
        	}
        	
        	if(person.getRoleId() == null) {
        		person.setRoleId(dbPerson.getRoleId());
        	}
        	
        	if(person.getDepartementId() == null) {
        		person.setDepartementId(dbPerson.getDepartementId());
        	}
        	
        	if(person.getManagerId() == null) {
        		person.setManagerId(dbPerson.getManagerId());
        	}
        	
        	if(person.getAddress() == null) {
        		person.setAddress(dbPerson.getAddress());
        	}
        }
        
        person.compensateTimezoneOnDates();

        return person;
    }

    @Override
    public PersonDTO personToDTO(Person destination) {
        if ( destination == null ) {
            return null;
        }

        PersonDTO personDTO = new PersonDTO();

        personDTO.setDateOfBirth( destination.getDateOfBirth() );
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
