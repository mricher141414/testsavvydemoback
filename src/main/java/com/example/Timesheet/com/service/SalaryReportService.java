package com.example.Timesheet.com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Timesheet.com.dao.ISalaryReportDao;
import com.example.Timesheet.com.model.SalaryReport;

@Service
public class SalaryReportService {

	@Autowired
	private ISalaryReportDao salaryReportDao;
	
	public List<SalaryReport> getAll(){
		return (List<SalaryReport>) salaryReportDao.findAll();
	}
	
	public Optional<SalaryReport> getById(int id) {
		return salaryReportDao.findById(id);
	}
	
	public SalaryReport save(SalaryReport salaryReport) {
		return salaryReportDao.save(salaryReport);
	}
	
	public void delete(SalaryReport salaryReport) {
		salaryReportDao.delete(salaryReport);
	}
	
	public void deleteAll() {
		salaryReportDao.deleteAll();
	}
	
	public void deleteByEmployeeId(int employeeId) {
		salaryReportDao.deleteByEmployeeId(employeeId);
	}
}

