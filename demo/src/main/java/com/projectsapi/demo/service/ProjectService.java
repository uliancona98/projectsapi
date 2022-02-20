package com.projectsapi.demo.service;

import com.projectsapi.demo.dto.ProjectDTO;
import com.projectsapi.demo.dto.WorkerDTO;

import java.util.List;
import java.util.Set;

import com.projectsapi.demo.dto.PagedResult;
import com.projectsapi.demo.dto.request.ProjectRequest;
import com.projectsapi.demo.model.Project;

import org.springframework.data.domain.Pageable;

public interface ProjectService {

	PagedResult<ProjectDTO> findAll(String search, Pageable pageable);

	List<ProjectDTO> findMyProjects(String username);

	ProjectDTO findProjectById(Integer projectId, String username, boolean isAdmin);

	ProjectDTO saveProject(ProjectRequest projectRequest);

	ProjectDTO editProject(ProjectRequest projectRequest, Integer id, String username, boolean isAdmin);
	
	Set<WorkerDTO> editProjectDevelopers(List<Integer> developers, Integer id, String username, boolean isAdmin);

	Set<WorkerDTO> getProjectDevelopers(Integer projectId, String username, boolean isAdmin);

	ProjectDTO disableProjectById(Integer id);

	boolean validateIfBelongsToProjectOwnerFromProject(String username, Project project);

    boolean validateIfBelongsToDevelopersProject(String username, Project project);
    
}
