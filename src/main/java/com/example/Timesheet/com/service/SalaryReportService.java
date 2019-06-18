package com.example.Timesheet.com.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.Timesheet.com.dao.ISalaryReportDao;
import com.example.Timesheet.com.model.SalaryReport;

@Service
public class SalaryReportService implements Serializable {

	private static final long serialVersionUID = 8829066510363245052L;
	private static final Logger log = LogManager.getLogger(SalaryReportService.class);
	
	@Autowired
	private ISalaryReportDao salaryReportDao;
	
	public List<SalaryReport> getAll(){
		log.debug("Entering getAll");
		return (List<SalaryReport>) salaryReportDao.findAll();
	}
	
	public Optional<SalaryReport> getById(int id) {
		log.debug("Entering getById with id parameter of " + id);
		return salaryReportDao.findById(id);
	}
	
	public SalaryReport save(SalaryReport salaryReport) {
		Assert.notNull(salaryReport, "Parameter salaryReport must not be null");
		log.debug("Entering save");
		
		return salaryReportDao.save(salaryReport);
	}
	
	public void delete(SalaryReport salaryReport) {
		Assert.notNull(salaryReport, "Parameter salaryReport must not be null");
		log.debug("Entering delete");
		
		salaryReportDao.delete(salaryReport);
	}
	
	public void deleteAll() {
		log.debug("Entering getAll");
		salaryReportDao.deleteAll();
	}
	
	public void deleteByEmployeeId(int employeeId) {
		log.debug("Entering deleteByEmployeeId with id parameter of " + employeeId);
		salaryReportDao.deleteByEmployeeId(employeeId);
	}
}

