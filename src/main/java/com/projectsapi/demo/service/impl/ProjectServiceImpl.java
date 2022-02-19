package com.projectsapi.demo.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


import com.projectsapi.demo.dto.PagedResult;
import com.projectsapi.demo.dto.ProjectDTO;
import com.projectsapi.demo.dto.WorkerDTO;
import com.projectsapi.demo.dto.request.ProjectRequest;
import com.projectsapi.demo.exception.NotFoundException;
import com.projectsapi.demo.model.Project;
import com.projectsapi.demo.model.ProjectStatus;
import com.projectsapi.demo.model.Worker;
import com.projectsapi.demo.repository.ProjectRepository;
import com.projectsapi.demo.repository.WorkerRepository;
import com.projectsapi.demo.service.ProjectService;
import com.projectsapi.demo.specification.ProjectSpecification;
import com.projectsapi.demo.specification.SearchCriteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class ProjectServiceImpl implements ProjectService {
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private WorkerRepository workerRepository;
    
    //Admin
    @Override
    @Transactional(readOnly = true)
    public PagedResult<ProjectDTO> findAll(String search, Pageable pageable) {
        Specification<Project> projectSpecification = Specification.where(null);
        if(search!=null){
            Specification<Project> nameSpecification = Specification.where(new ProjectSpecification(new SearchCriteria("name", "∼", search)));
            projectSpecification = projectSpecification.or(nameSpecification);

            Specification<Project> descrSpecification = Specification.where(new ProjectSpecification(new SearchCriteria("description", "∼", search)));
            projectSpecification = projectSpecification.or(descrSpecification);

            /*Specification<Project> projectStateSpecification = Specification.where(new ProjectSpecification(new SearchCriteria("state", "∼", search)));
            projectSpecification = projectSpecification.or(projectStateSpecification);

            Specification<Project> descriTaskSpecification = Specification.where(new ProjectSpecification(new SearchCriteria("description_task", "∼", search)));
            projectSpecification = projectSpecification.or(descriTaskSpecification);

            Specification<Project> taskTypeSpecification = Specification.where(new ProjectSpecification(new SearchCriteria("type", "∼", search)));
            projectSpecification = projectSpecification.or(taskTypeSpecification);*/
        }

        Page<Project> pageProjects = projectRepository.findAll(projectSpecification, pageable);
        
        List<ProjectDTO> listBooks = pageProjects.getContent().stream().map(this::convertProjectToDTO).collect(Collectors.toList());
        PagedResult<ProjectDTO> projectsDTO =  new PagedResult<ProjectDTO>();
        projectsDTO.setData(listBooks);
        projectsDTO.setPageNumber(pageProjects.getNumber());
        projectsDTO.setPageSize(pageProjects.getSize());
        projectsDTO.setTotalElements(pageProjects.getTotalElements());
        projectsDTO.setTotalPages(pageProjects.getTotalPages());

        return projectsDTO;
    }

	private ProjectDTO convertProjectToDTO(Project project) {
		ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setDescription(project.getDescription());
        projectDTO.setExpectedTime(project.getExpectedTime());
        projectDTO.setUsedTime(project.getUsedTime());
        projectDTO.setName(project.getName());
        projectDTO.setProjectOwner(convertDeveloperToDTO(project.getProjectOwner()));
        projectDTO.setStartDate(project.getStartDate());
        projectDTO.setState(project.getState());
	
        return projectDTO;
	}

    public boolean validateIfBelongsToProjectOwnerFromProject(String username, Project project){
        Optional<Worker> optWorker = workerRepository.findByUsername(username);
        if(project.getProjectOwner().equals(optWorker.get())){
            return true;
        }
        return false;
    }

    public boolean validateIfBelongsToDevelopersProject(String username, Project project){
        Optional<Worker> optWorker = workerRepository.findByUsername(username);
        if(project.getDevelopers().contains(optWorker.get())){
            return true;
        }
        return false;
    }

	private WorkerDTO convertDeveloperToDTO(Worker worker) {
		WorkerDTO authorDTO = new WorkerDTO();
		authorDTO.setName(worker.getName());
		authorDTO.setLastName(worker.getLastName());
        authorDTO.setSecondLastName(worker.getSecondLastName());

		return authorDTO;
	}

    //project_owner
    @Override
    @Transactional(readOnly = true)
    public PagedResult<ProjectDTO> findMyProjects(String username, String search, Pageable pageable) {
        Specification<Project> projectSpecification = Specification.where(null);
        if(search!=null){
            Specification<Project> nameSpecification = Specification.where(new ProjectSpecification(new SearchCriteria("name", "∼", search)));
            projectSpecification = projectSpecification.or(nameSpecification);

            Specification<Project> descrSpecification = Specification.where(new ProjectSpecification(new SearchCriteria("description", "∼", search)));
            projectSpecification = projectSpecification.or(descrSpecification);

            /*Specification<Project> projectStateSpecification = Specification.where(new ProjectSpecification(new SearchCriteria("state", "∼", search)));
            projectSpecification = projectSpecification.or(projectStateSpecification);

            Specification<Project> descriTaskSpecification = Specification.where(new ProjectSpecification(new SearchCriteria("description_task", "∼", search)));
            projectSpecification = projectSpecification.or(descriTaskSpecification);

            Specification<Project> taskTypeSpecification = Specification.where(new ProjectSpecification(new SearchCriteria("type", "∼", search)));
            projectSpecification = projectSpecification.or(taskTypeSpecification);*/
        }

        Optional<Worker> projectOwner = workerRepository.findByUsername(username);

        Page<Project> pageProjects = projectRepository.findByProjectOwner(projectSpecification, pageable, projectOwner.get());
        
        List<ProjectDTO> listProjects = pageProjects.getContent().stream().map(this::convertProjectToDTO).collect(Collectors.toList());
        PagedResult<ProjectDTO> projectsDTO =  new PagedResult<ProjectDTO>();
        projectsDTO.setData(listProjects);
        projectsDTO.setPageNumber(pageProjects.getNumber());
        projectsDTO.setPageSize(pageProjects.getSize());
        projectsDTO.setTotalElements(pageProjects.getTotalElements());
        projectsDTO.setTotalPages(pageProjects.getTotalPages());

        return projectsDTO;
    }
    //Admin-project_owner only his projets
    @Override
    public ProjectDTO findProjectById(Integer projectId, String username, boolean isAdmin) {
        Optional<Project> optProject = projectRepository.findById(projectId);
        if(optProject.isPresent()) {
            if( isAdmin || validateIfBelongsToProjectOwnerFromProject(username, optProject.get())){
                return convertProjectToDTO(optProject.get());
            }
            throw new UnsupportedOperationException("You can't get this project");
        }
        throw new NotFoundException("Project " + projectId + " was not found");
    }

    //admin
    @Override
    @Transactional(readOnly = false)
    public ProjectDTO saveProject(ProjectRequest projectRequest) {
        Project project = new Project();
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());
        project.setExpectedTime(projectRequest.getExpectedTime());
        project.setStartDate(projectRequest.getStartDate());
        project.setUsedTime(0.0);
        project.setState(projectRequest.getState());
        Optional<Worker> optWorker = workerRepository.findById(projectRequest.getProjectOwner());
        if(optWorker.isPresent()){
            project.setProjectOwner(optWorker.get());
        }else{
            throw new NotFoundException("Project owner was not found");
        }
        try{
            project.setDevelopers(projectRequest.getDevelopers().stream()	
            .map(developer -> workerRepository.findById(developer).orElseGet(null))
            .filter(Objects::nonNull)
            .collect(Collectors.toSet()));                
        }catch(Exception e){
            e.printStackTrace();
            throw new NotFoundException("Developer or developers were not found");
        }
        project = projectRepository.save(project);
        return convertProjectToDTO(project);
        
    }

    @Override
	public Set<WorkerDTO> getProjectDevelopers(Integer projectId, String username, boolean isAdmin){
        //check if is the project owner
		Optional<Project> optProject = projectRepository.findById(projectId);
        
        if(optProject.isPresent()) {
            if(isAdmin || validateIfBelongsToProjectOwnerFromProject(username, optProject.get())){
                return optProject.get().getDevelopers().stream()
                .map(this::convertDeveloperToDTO)
                .collect(Collectors.toSet());
            }
            throw new UnsupportedOperationException("Can't get this project");
        }
        throw new NotFoundException("Project " + projectId + " was not found");
        
    }


    @Override
    public Set<WorkerDTO> editProjectDevelopers(List<Integer> developersRequest, Integer id, String username, boolean isAdmin){
		Optional<Project> optProject = projectRepository.findById(id);
        if(optProject.isPresent()) {
            if(isAdmin || validateIfBelongsToProjectOwnerFromProject(username, optProject.get())){
                Project project = optProject.get();
                if(!project.getState().equals(ProjectStatus.disabled)){
                    try{
                        project.setDevelopers(developersRequest.stream()	
                        .map(developer -> workerRepository.findById(developer).orElseGet(null))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet()));  
                        project = projectRepository.save(project);
                        return project.getDevelopers().stream()
                        .map(this::convertDeveloperToDTO)
                        .collect(Collectors.toSet());
                    }catch(Exception e){
                        e.printStackTrace();
                        throw new NotFoundException("Developer or developers were not found");
                    }		
                }
                throw new UnsupportedOperationException("Can't upload disabled projects");
            }
            throw new UnsupportedOperationException("Can't edit this project");
        }
        throw new NotFoundException("Project " + id + " was not found"); 
    }


    //admin
    @Override
    public ProjectDTO editProject(ProjectRequest projectRequest, Integer id, String username, boolean isAdmin) {
		Optional<Project>optProject = projectRepository.findById(id);
        if(optProject.isPresent()) {
            if(isAdmin || validateIfBelongsToProjectOwnerFromProject(username, optProject.get())){
                Project project = optProject.get();
                if(!project.getState().equals(ProjectStatus.disabled)){
                    project.setName(projectRequest.getName());
                    project.setDescription(projectRequest.getDescription());
                    project.setExpectedTime(projectRequest.getExpectedTime());
                    project.setUsedTime(0.0);
                    project.setStartDate(projectRequest.getStartDate());
                    project.setState(projectRequest.getState());
                    Optional<Worker> optWorker = workerRepository.findById(projectRequest.getProjectOwner());
                    if(optWorker.isPresent()){
                        project.setProjectOwner(optWorker.get());
                    }else{
                        throw new NotFoundException("Project owner was not found");
                    }
                    try{
                        project.setDevelopers(projectRequest.getDevelopers().stream()	
                        .map(developer -> workerRepository.findById(developer).orElseGet(null))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet()));                
                    }catch(Exception e){
                        e.printStackTrace();
                        throw new NotFoundException("Developer or developers were not found");
                    }
                    project = projectRepository.save(project);
                    return convertProjectToDTO(project);
                }
                throw new UnsupportedOperationException("Can't upload disabled projects");
            }
            throw new UnsupportedOperationException("Can't edit this project");
        }
        throw new NotFoundException("Project " + id + " was not found");       
    }

    //admin
    @Override
    public ProjectDTO disableProjectById(Integer id) {
		Optional<Project> optProject = projectRepository.findById(id);
		if(optProject.isPresent()) {
            Project project = optProject.get();
            project.setState(ProjectStatus.disabled);
            project = projectRepository.save(project);
            return convertProjectToDTO(project);
		}
		throw new NotFoundException("Project " + id + " was not found");        
    }
    
}
