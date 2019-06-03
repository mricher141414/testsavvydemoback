package com.example.Timesheet.com.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Timesheet.com.dto.RoleDto;
import com.example.Timesheet.com.model.Role;
import com.example.Timesheet.com.service.RoleService;

@Component
public class RoleMapper implements IRoleMapper {
	
	@Autowired
	private RoleService roleService;
	
	@Override
    public Role DTOtoRole(RoleDto source, int id) {
        if ( source == null ) {
            return null;
        }

        Role role = new Role();
        
        Optional<Role> optionalRole = roleService.getById(id);
        
        if(optionalRole.isPresent()) {
        	role = optionalRole.get();
        }

        role.setId( id );
        	
    	if(source.getName() != null) {
    		role.setName(source.getName());
    	}
    	
    	if(source.getDescription() != null) {
    		role.setDescription(source.getDescription());
    	}

        return role;
    }

    @Override
    public RoleDto roleToDTO(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleDto roleDTO = new RoleDto();

        roleDTO.setId( role.getId() );
        roleDTO.setName( role.getName() );
        roleDTO.setDescription(role.getDescription());

        return roleDTO;
    }
}
