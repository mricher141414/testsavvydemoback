package com.example.Timesheet.com.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Timesheet.com.dto.RoleDTO;
import com.example.Timesheet.com.model.Role;
import com.example.Timesheet.com.service.RoleService;

@Component
public class RoleMapper implements IRoleMapper {
	
	@Autowired
	private RoleService roleService;
	
	@Override
    public Role DTOtoRole(RoleDTO roleDTO, int id) {
        if ( roleDTO == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( id );
        role.setName( roleDTO.getName() );
        
        Optional<Role> optionalRole = roleService.getById(id);
        
        if(optionalRole.isPresent()) {
        	Role dbRole = optionalRole.get();
        	
        	if(role.getName() == null) {
        		role.setName(dbRole.getName());
        	}
        }

        return role;
    }

    @Override
    public RoleDTO roleToDTO(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setId( role.getId() );
        roleDTO.setName( role.getName() );

        return roleDTO;
    }

}
