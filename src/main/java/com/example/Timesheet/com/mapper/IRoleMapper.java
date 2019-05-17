package com.example.Timesheet.com.mapper;

import org.mapstruct.Mapper;

import com.example.Timesheet.com.dto.RoleDto;
import com.example.Timesheet.com.model.Role;

@Mapper
public interface IRoleMapper {
	 Role DTOtoRole(RoleDto source, int id);
	 RoleDto roleToDTO(Role destination);
}
