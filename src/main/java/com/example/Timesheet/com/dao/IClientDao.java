package com.example.Timesheet.com.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.Timesheet.com.model.Client;

@Repository
public interface IClientDao extends CrudRepository<Client, Integer> {
	
}
