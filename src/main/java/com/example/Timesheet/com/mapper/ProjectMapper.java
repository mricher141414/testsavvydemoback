package com.example.Timesheet.com.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Timesheet.com.dto.ProjectDto;
import com.example.Timesheet.com.model.Project;
import com.example.Timesheet.com.service.ProjectService;

@Component
public class ProjectMapper implements IProjectMapper {

	@Autowired
	private ProjectService projectService;
	
	@Override
	public Project dtoToProject(ProjectDto source, int id) {
		if ( source == null ) {
            return null;
        }

        Project project = new Project();

        project.setId( id );
        project.setName( source.getName() );
        project.setDescription( source.getDescription() );
        project.setStartDate( source.getStartDate() );
        project.setEndDate( source.getEndDate() );
        project.setClientId( source.getClientId() );
        project.setProjectManagerId( source.getProjectManagerId() );
        
        Optional<Project> optionalProject = projectService.getById(id);
        
        if(optionalProject.isPresent()) {
        	Project dbProject = optionalProject.get();
        	
        	if(project.getName() == null) {
        		project.setName(dbProject.getName());
        	}
        	
        	if(project.getDescription() == null) {
        		project.setDescription(dbProject.getDescription());
        	}
        	
        	if(project.getStartDate() == null) {
        		project.setStartDate(dbProject.getStartDate());
        	}
        	
        	if(project.getEndDate() == null) {
        		project.setEndDate(dbProject.getEndDate());
        	}
        	
        	if(project.getClientId() == null) {
        		project.setClientId(dbProject.getClientId());
        	}
        	
        	if(project.getProjectManagerId() == null) {
        		project.setProjectManagerId(dbProject.getProjectManagerId());
        	}
        }
        return project;
	}

	@Override
	public ProjectDto projectToDto(Project destination) {
		 if ( destination == null ) {
	            return null;
	        }

	        ProjectDto projectDto = new ProjectDto();

	        projectDto.setName( destination.getName() );
	        projectDto.setId( destination.getId() );
	        projectDto.setDescription( destination.getDescription() );
	        projectDto.setStartDate( destination.getStartDate() );
	        projectDto.setEndDate( destination.getEndDate() );
	        projectDto.setClientId( destination.getClientId() );
	        projectDto.setProjectManagerId( destination.getProjectManagerId() );

	        return projectDto;
	}

}
