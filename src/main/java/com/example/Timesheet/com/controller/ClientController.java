package com.example.Timesheet.com.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.Timesheet.com.Paths;
import com.example.Timesheet.com.dto.ClientDto;
import com.example.Timesheet.com.dto.ClientStatsEmployee;
import com.example.Timesheet.com.mapper.ClientMapper;
import com.example.Timesheet.com.model.Client;
import com.example.Timesheet.com.model.Department;
import com.example.Timesheet.com.model.Employee;
import com.example.Timesheet.com.model.Project;
import com.example.Timesheet.com.model.ProjectEmployee;
import com.example.Timesheet.com.model.Timesheet;
import com.example.Timesheet.com.service.ClientService;
import com.example.Timesheet.com.service.EmployeeService;
import com.example.Timesheet.com.service.ProjectEmployeeService;
import com.example.Timesheet.com.service.ProjectService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "ClientController")
public class ClientController implements Serializable {

	private static final long serialVersionUID = -1393042468260877317L;
	private static final Logger log = LogManager.getLogger(ClientController.class);

	@Autowired
	private ClientService clientService = new ClientService();
	
	@Autowired
	private ClientMapper clientMapper = new ClientMapper();
	
	@Autowired
	private ProjectService projectService = new ProjectService();
	
	@Autowired
	private ProjectEmployeeService projectEmployeeService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping(Paths.ClientBasicPath)
	@ApiOperation(value = "Returns a list of all clients in the system.",
					response = Client.class, responseContainer = "List")
	public List<Client> getAll(){		
		log.debug("Entering getAll");
		return clientService.getAll();
	}
	
	@GetMapping(Paths.ClientGetOne)
	@ApiOperation(value = "Returns the client with the specified identifier.", notes = "404 if the client's identifier cannot be found.",
					response = Client.class, responseContainer = "List")
	public ResponseEntity<?> getOne(@ApiParam(value = "Id of the client to be found.", required = true) @RequestParam(value="id") int id) {
		log.debug("Entering getOne with id parameter of " + id);
		
		Optional<Client> optionalClient = clientService.getById(id);
		
		if(optionalClient.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.ClientIdNotFound, Paths.ClientGetOne);
		}
		
		return GlobalFunctions.createOkResponseFromObject(optionalClient.get());
	}
	
	@PostMapping(Paths.ClientBasicPath)
	@ApiOperation(value = "Creates a new client in the system.",
					response = Client.class)
	public ResponseEntity<String> create(@ApiParam(value = "Client information for the new client to be created.", required = true)@RequestBody ClientDto clientDto) {
		log.debug("Entering create");
		
		Client client = clientMapper.dtoToClient(clientDto, 0);
		
		client = clientService.save(client);
		
		return GlobalFunctions.createOkResponseFromObject(client);
	}
	
	@PutMapping(Paths.ClientBasicPath)
	@ApiOperation(value = "Updates a client in the system by their identifier.", notes = "404 if the client's identifier is not found",
					response = Client.class)
	public ResponseEntity<?> edit(@ApiParam("client information to be modified")@RequestBody ClientDto clientDto,
										@ApiParam(value = "Id of the client to be modified. Cannot be null", required = true)@RequestParam(value="id") int id){
		log.debug("Entering edit with id parameter of " + id);
		
		if(clientService.getById(id).isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.ClientIdNotFound, Paths.ClientBasicPath);
		}
		
		Client client = clientMapper.dtoToClient(clientDto, id);
		
		clientService.save(client);
		
		return GlobalFunctions.createOkResponseFromObject(client);
}
	
	@DeleteMapping(Paths.ClientBasicPath)
	@ApiOperation(value = "Deletes a client from the system.", notes = "404 if the client's identifier cannot be found",
					response = Department.class)
	public ResponseEntity<?> delete(@ApiParam(value = "Id of the client to be deleted. Cannot be null.", required = true)@RequestParam(value="id") int id){
		log.debug("Entering delete with id parameter of " + id);
		
		Optional<Client> optionalClient = clientService.getById(id);
		
		if(optionalClient.isPresent() == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.ClientIdNotFound, Paths.ClientBasicPath);
		}
		
		if(projectService.getByClientId(id).size() > 0) {
			return GlobalFunctions.createBadRequest(GlobalMessages.ProjectUsesClientCannotDelete, Paths.ClientBasicPath);
		}
		
		Client client = optionalClient.get();
		
		clientService.delete(client);
		return GlobalFunctions.createOkResponseFromObject(client);
	}
	
	@GetMapping(Paths.ClientGetStatsEmployee)
	@ApiOperation(value = "Gets the amount of employees working on projects of the client and their average salary of those employees.", notes = "404 if the client's identifier cannot be found.", 
					response = ClientStatsEmployee.class)
	public ResponseEntity<String> getClientEmployeeStats(@ApiParam(value = "Id of the project")@RequestParam(value = "id") int id) {
		
		log.debug("Entering getClientEmployeeStats with id parameter of " + id);
		
		if(clientService.clientExists(id) == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.ClientIdNotFound, Paths.ClientGetStatsEmployee);
		}
		
		List<Integer> employeeIds = new ArrayList<Integer>();
		List<Project> projects = projectService.getByClientId(id);
		
		for(Project project : projects) {
			List<ProjectEmployee> assignations = projectEmployeeService.getByProjectId(project.getId());
			
			for(ProjectEmployee assignation : assignations) {
				
				if(employeeIds.contains(assignation.getEmployeeId()) == false) {	
					employeeIds.add(assignation.getEmployeeId());
				}
			}
			
		}
		
		List<Employee> employees = new ArrayList<Employee>();
		
		for(Integer employeeId: employeeIds) {
			employees.add(employeeService.getById(employeeId).get());
		}
		
		ClientStatsEmployee clientStats = new ClientStatsEmployee();
		
		clientStats.setClientName(clientService.getById(id).get().getName());
		clientStats.setNbEmployeeOnClientProjects(employees.size());
		clientStats.setAverageSalary(employeeService.calculateAverageSalary(employees));
		
		return GlobalFunctions.createOkResponseFromObject(clientStats);
	}
}
