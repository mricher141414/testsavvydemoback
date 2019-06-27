package com.example.Timesheet.com.mapper;

import org.mapstruct.Mapper;

import com.example.Timesheet.com.dto.RoleDto;
import com.example.Timesheet.com.model.Role;

@Mapper
public interface IRoleMapper {
	 Role dtoToRole(RoleDto source, int id);
	 RoleDto roleToDto(Role destination);
}
