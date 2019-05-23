package com.example.Timesheet.com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Timesheet.com.GlobalFunctions;
import com.example.Timesheet.com.GlobalMessages;
import com.example.Timesheet.com.dto.ClientDto;
import com.example.Timesheet.com.mapper.ClientMapper;
import com.example.Timesheet.com.model.Client;
import com.example.Timesheet.com.model.Department;
import com.example.Timesheet.com.service.ClientService;
import com.example.Timesheet.com.service.ProjectService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "ClientController")
public class ClientController {

	@Autowired
	ClientService clientService = new ClientService();
	
	@Autowired
	ClientMapper clientMapper = new ClientMapper();
	
	@Autowired
	ProjectService projectService = new ProjectService();
	
	@GetMapping("/client")
	@ApiOperation("Returns a list of all clients in the system.")
	public List<Client> getAll(){
		return clientService.getAll();
	}
	
	@PostMapping("/client")
	@ApiOperation("Creates a new client in the system.")
	public ResponseEntity<String> create(@ApiParam(value = "Client information for the new client to be created.", required = true)@RequestBody ClientDto clientDto) {
		
		Client client = clientMapper.dtoToClient(clientDto, 0);
		
		clientService.save(client);
		
		return GlobalFunctions.createOkResponseFromObject(client);
	}
	
	@PutMapping("/client")
	@ApiOperation(value = "Updates a client in the system by their identifier.", notes = "404 if the client's identifier is not found")
	public ResponseEntity<?> edit(@ApiParam("client information to be modified")@RequestBody ClientDto clientDto,
										@ApiParam(value = "Id of the client to be modified. Cannot be null", required = true)@RequestParam(value="id") int id){
		
		if(clientService.getById(id).isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.ClientIdNotFound, "/client");
		}
		
		Client client = clientMapper.dtoToClient(clientDto, id);
		
		clientService.save(client);
		
		return GlobalFunctions.createOkResponseFromObject(client);
}
	
	@DeleteMapping("/client")
	@ApiOperation(value = "Deletes a client from the system.", notes = "404 if the client's identifier cannot be found")
	public ResponseEntity<?> delete(@ApiParam(value = "Id of the client to be deleted. Cannot be null.", required = true)@RequestParam(value="id") int id){
		
		Optional<Client> optionalClient = clientService.getById(id);
		
		if(optionalClient.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.ClientIdNotFound, "/client");
		}
		
		if(projectService.getByClientId(id).size() > 0) {
			return GlobalFunctions.createBadRequest(GlobalMessages.ProjectUsesClientCannotDelete, "/client");
		}
		
		Client client = optionalClient.get();
		
		clientService.delete(client);
		return GlobalFunctions.createOkResponseFromObject(client);
	}
}
