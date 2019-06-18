package com.example.Timesheet.com.mapper;

import java.io.Serializable;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Timesheet.com.dto.SalaryReportDto;
import com.example.Timesheet.com.model.SalaryReport;
import com.example.Timesheet.com.service.SalaryReportService;

@Component
public class SalaryReportMapper implements ISalaryReportMapper, Serializable {

	private static final long serialVersionUID = 6160108558823109697L;
	private static final Logger log = LogManager.getLogger(SalaryReportMapper.class);
	
	@Autowired
	private SalaryReportService salaryReportService;
	
	@Override
	public SalaryReport dtoToSalaryReport(SalaryReportDto source, int id) {
		log.debug("Entering dtoToSalaryReport with id parameter of " + id);
		
		if(source == null) {
			return null;
		}
		
		SalaryReport salaryReport = new SalaryReport();
		
		salaryReport.setId(id);
		salaryReport.setEmployeeId(source.getEmployeeId());
		salaryReport.setPaycheck(source.getPaycheck());
		
		Optional<SalaryReport> optionalSalaryReport = salaryReportService.getById(id);
		
		if(optionalSalaryReport.isPresent()) {
			SalaryReport dbSalaryReport = optionalSalaryReport.get();
			
			if(salaryReport.getEmployeeId() == null) {
				salaryReport.setEmployeeId(dbSalaryReport.getEmployeeId());
			}
			
			if(salaryReport.getPaycheck() == null) {
				salaryReport.setPaycheck(dbSalaryReport.getPaycheck());
			}
		}
		
		return salaryReport;
	}

	@Override
	public SalaryReportDto salaryReportToDto(SalaryReport destination) {
		log.debug("Entering salaryReportToDto");
		
		if(destination == null) {
			return null;
		}
		
		SalaryReportDto salaryReportDto = new SalaryReportDto();
		
		salaryReportDto.setId(destination.getId());
		salaryReportDto.setEmployeeId(destination.getEmployeeId());
		salaryReportDto.setPaycheck(destination.getPaycheck());
		
		return salaryReportDto;
	}

}
