package com.example.Timesheet.com.mapper;

import org.mapstruct.Mapper;

import com.example.Timesheet.com.dto.RoleDTO;
import com.example.Timesheet.com.model.Role;

@Mapper
public interface IRoleMapper {
	 Role DTOtoRole(RoleDTO source, int id);
	 RoleDTO roleToDTO(Role destination);
}
