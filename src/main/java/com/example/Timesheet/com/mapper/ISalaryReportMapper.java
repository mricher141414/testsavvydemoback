package com.example.Timesheet.com.mapper;

import org.mapstruct.Mapper;

import com.example.Timesheet.com.dto.SalaryReportDto;
import com.example.Timesheet.com.model.SalaryReport;

@Mapper
public interface ISalaryReportMapper {
	SalaryReport dtoToSalaryReport(SalaryReportDto source, int id);
	SalaryReportDto SalaryReportToDto(SalaryReport destination);
}
