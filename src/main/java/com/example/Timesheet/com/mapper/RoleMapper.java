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
    public Role DTOtoRole(RoleDto roleDTO, int id) {
        if ( roleDTO == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( id );
        role.setName( roleDTO.getName() );
        role.setDescription(roleDTO.getDescription());
        
        Optional<Role> optionalRole = roleService.getById(id);
        
        if(optionalRole.isPresent()) {
        	Role dbRole = optionalRole.get();
        	role.setVersion(dbRole.getVersion());
        	
        	if(role.getName() == null) {
        		role.setName(dbRole.getName());
        	}
        	
        	if(role.getDescription() == null) {
        		role.setDescription(roleDTO.getDescription());
        	}
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
