package com.example.Timesheet.com.mapper;

import com.example.Timesheet.com.dto.ClientDto;
import com.example.Timesheet.com.model.Client;

public interface IClientMapper {
	Client dtoToClient(ClientDto source, int id);
	ClientDto clientToDto(Client destination);
}
