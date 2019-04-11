package com.example.Timesheet.com.mapper;

import com.example.Timesheet.com.dto.RoleDTO;
import com.example.Timesheet.com.model.Role;

public class  RoleMapper implements IRoleMapper {
	
	@Override
    public Role DTOtoRole(RoleDTO roleDTO) {
        if ( roleDTO == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( roleDTO.getId() );
        role.setName( roleDTO.getName() );

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
