package com.example.Timesheet.com.configuration;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.example.Timesheet.com"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(getApiInfo())
				.tags(new Tag("ClientController", "Set of endpoints for listing, creating, updating and deleting clients."),
						new Tag("DepartmentController", "Set of endpoints for listing, creating, updating and deleting departments."),
						new Tag("EmployeeController", "Set of endpoints for listing, creating, updating and deleting employee."),
						new Tag("ProjectController", "Set of endpoints for listing, creating, updating and deleting projects."),
						new Tag("ProjectEmployeeController", "Set of endpoints for listing, creating and deleting assignations of employees to projects."),
						new Tag("RoleController", "Set of endpoints for listing, creating, updating and deleting roles."),
						new Tag("TimeProjectController", "Set of endpoints for listing, creating, updating and deleting time_projects."),
						new Tag("TimesheetStatusController", "Set of endpoints for listing, creating, updating and deleting timesheet statuses."),
						new Tag("TimesheetRowController", "Set of endpoints for listing, creating, updating and deleting timesheet rows."),
						new Tag("TimesheetController", "Set of endpoints for listing, creating, updating and deleting timesheets."));
	}
	
	private ApiInfo getApiInfo() {
		return new ApiInfo(
				"Timesheet",
				"Application démo de TestSavvy",
				"0.0.1",
				null,  	//terms of service URL 
				null, 	//new Contact("NAME", "URL", "EMAIL") 
				null,	//License name
				null,	//License URL 
				Collections.emptyList());
	}
}