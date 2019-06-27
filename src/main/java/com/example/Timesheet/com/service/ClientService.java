package com.example.Timesheet.com.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.Timesheet.com.dao.IClientDao;
import com.example.Timesheet.com.model.Client;

@Service
public class ClientService implements Serializable {

	private static final long serialVersionUID = 1442793074673466186L;
	private static final Logger log = LogManager.getLogger(ClientService.class);
	
	@Autowired
	private IClientDao clientDao;
	
	public List<Client> getAll() {
		log.debug("Entering getAll");
		return (List<Client>) clientDao.findAll();
	}
	
	public Optional<Client> getById(int id) {
		log.debug("Entering getById with id parameter of " + id);
		return clientDao.findById(id);
	}
	
	public Client save(Client client) {
		Assert.notNull(client, "Parameter client must not be null");
		log.debug("Entering save");
		
		if(clientDao.existsById(client.getId()) == false) {
			client.setVersion(0);
		}
		
		return clientDao.save(client);
	}
	
	public void delete(Client client) {
		Assert.notNull(client, "Parameter client must not be null");
		log.debug("Entering delete");
		
		clientDao.delete(client);
	}
	
	public boolean clientExists(int id) {
		log.debug("Entering clientExists with id parameter of " + id);
		
		return clientDao.existsById(id);
	}
}
