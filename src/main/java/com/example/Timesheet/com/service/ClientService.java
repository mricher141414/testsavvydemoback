package com.example.Timesheet.com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Timesheet.com.dao.IClientDao;
import com.example.Timesheet.com.model.Client;

@Service
public class ClientService {
	
	@Autowired
	private IClientDao clientDao;
	
	public Optional<Client> getById(int id) {
		return clientDao.findById(id);
	}
}
