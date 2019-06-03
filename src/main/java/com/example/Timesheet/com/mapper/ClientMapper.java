package com.example.Timesheet.com.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Timesheet.com.dto.ClientDto;
import com.example.Timesheet.com.model.Client;
import com.example.Timesheet.com.service.ClientService;

@Component
public class ClientMapper implements IClientMapper {

	@Autowired
	private ClientService clientService;
	
	@Override
	public Client dtoToClient(ClientDto source, int id) {
		if ( source == null ) {
			return null;
		}
		
		Client client = new Client();
		
		Optional<Client> optionalClient = clientService.getById(id);
		
		if(optionalClient.isPresent()) {
			client = optionalClient.get();
		}
		
		client.setId(id);
			
		if(source.getName() != null) {
			client.setName(source.getName());
		}
		
		if(source.getAddress() != null) {
			client.setAddress(source.getAddress());
		}
		
		if(source.getDescription() != null) {
			client.setDescription(source.getDescription());
		}
		
		return client;
	}

	@Override
	public ClientDto clientToDto(Client destination) {
		if ( destination == null ) {
			return null;
		}
		
		ClientDto clientDto = new ClientDto();
		
		clientDto.setId(destination.getId());
		clientDto.setName(destination.getName());
		clientDto.setAddress(destination.getAddress());
		clientDto.setDescription(destination.getDescription());
		
		return clientDto;
	}
}
