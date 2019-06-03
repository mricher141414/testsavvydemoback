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
        
        Optional<Project> optionalProject = projectService.getById(id);
        
        if(optionalProject.isPresent()) {
        	project = optionalProject.get();
        }

        project.setId( id );
        	
    	if(source.getName() != null) {
    		project.setName(source.getName());
    	}
    	
    	if(source.getDescription() != null) {
    		project.setDescription(source.getDescription());
    	}
    	
    	if(source.getStartDate() != null) {
    		project.setStartDate(source.getStartDate());
    	}
    	
    	if(source.getEndDate() != null) {
    		project.setEndDate(source.getEndDate());
    	}
    	
    	if(source.getClientId() != null) {
    		project.setClientId(source.getClientId());
    	}
    	
    	if(source.getProjectManagerId() != null) {
    		project.setProjectManagerId(source.getProjectManagerId());
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
