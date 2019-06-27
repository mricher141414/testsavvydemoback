package com.example.Timesheet.com.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import com.example.Timesheet.com.model.SalaryReport;

public interface ISalaryReportDao extends CrudRepository<SalaryReport, Integer> {
	
	@Transactional
	@Modifying
	void deleteByEmployeeId(Integer employeeId);
}
