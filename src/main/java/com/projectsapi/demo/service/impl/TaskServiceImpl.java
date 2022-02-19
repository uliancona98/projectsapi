package com.projectsapi.demo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.projectsapi.demo.dto.TaskDTO;
import com.projectsapi.demo.dto.request.TaskRequest;
import com.projectsapi.demo.dto.request.TaskUpdateRequest;
import com.projectsapi.demo.exception.NotFoundException;
import com.projectsapi.demo.exception.UnprocessableEntityException;
import com.projectsapi.demo.model.Project;
import com.projectsapi.demo.model.ProjectStatus;
import com.projectsapi.demo.model.Task;
import com.projectsapi.demo.model.TaskId;
import com.projectsapi.demo.repository.ProjectRepository;
import com.projectsapi.demo.repository.TaskRepository;
import com.projectsapi.demo.service.ProjectService;
import com.projectsapi.demo.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class TaskServiceImpl implements TaskService {
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private ProjectRepository projectRepository;    
    @Autowired
	private ProjectService projectService; 
    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> findAll(Integer idProject, String username, boolean isAdmin) {
        Optional<Project> optProject = projectRepository.findById(idProject);
		if(optProject.isPresent()) {
            if(isAdmin || projectService.validateIfBelongsToDevelopersProject(username, optProject.get()) || projectService.validateIfBelongsToProjectOwnerFromProject(username, optProject.get())){
                List<Task> tasksByProject = taskRepository.findTaskByProject(idProject);
                List<TaskDTO> listTasks = tasksByProject.stream().map(this::convertTaskToDTO).collect(Collectors.toList());
                return listTasks;
            }
            throw new UnsupportedOperationException("Can't get the tasks because you don't belong to this project");
        }
		throw new NotFoundException("Project " + idProject + " was not found");
    }

	private TaskDTO convertTaskToDTO(Task task) {
		TaskDTO taskDTO = new TaskDTO();
        taskDTO.setDescription(task.getDescription());
        taskDTO.setDuration(task.getDuration());
        taskDTO.setIdProject(task.getTaskId().getIdProject());
        taskDTO.setIdTask(task.getTaskId().getIdTask());
        taskDTO.setState(task.getState());
        taskDTO.setType(task.getType());
		return taskDTO;
	}

    @Override
    public TaskDTO findTaskById(Integer idProject, Integer taskId,  String username, boolean isAdmin) {
        TaskId taskKey = new TaskId(taskId, idProject);
        Optional<Task> optTask = taskRepository.findById(taskKey);
        Optional<Project> optProject = projectRepository.findById(idProject);
		if(optProject.isPresent()) {
            if(optTask.isPresent()) {
                if(isAdmin || projectService.validateIfBelongsToDevelopersProject(username, optProject.get()) || projectService.validateIfBelongsToProjectOwnerFromProject(username, optProject.get())){
                    return convertTaskToDTO(optTask.get());
                }
                throw new UnsupportedOperationException("Can't get the task because you don't belong to this project");
            }
            throw new NotFoundException("Task with project " + idProject + " and task id "+taskId+" was not found");
        }
        throw new NotFoundException("Project " + idProject +" was not found");
    }

    @Transactional
    @Override
    public TaskDTO saveTask(Integer projectId, TaskRequest taskRequest,  String username, boolean isAdmin) {
        Optional<Project> optProject = projectRepository.findById(projectId);
        if(optProject.isPresent()) {
            Optional<Task> optTask = taskRepository.findById(new TaskId(taskRequest.getIdTask(), projectId));
            if(!optTask.isPresent()){
                if(isAdmin || projectService.validateIfBelongsToProjectOwnerFromProject(username, optProject.get())){
                    if(!optProject.get().getState().equals(ProjectStatus.disabled)){

                        Task task = new Task();
                        TaskId taskId = new TaskId(taskRequest.getIdTask(), projectId);
                        task.setTaskId(taskId);
                        task.setDescription(taskRequest.getDescription());
                        task.setDuration(taskRequest.getDuration());
                        task.setState(taskRequest.getState());
                        task.setType(taskRequest.getType());
                        task = taskRepository.save(task);
                        projectRepository.updateUsedTimeInProject(taskRequest.getDuration(), projectId);
                        return convertTaskToDTO(task);
                    }
                    throw new UnsupportedOperationException("Can't save the task the project is disabled");
                }
                throw new UnsupportedOperationException("Can't save the task because you don't belong to this project");
            }
            throw new UnprocessableEntityException("Task already exists");
        }
        throw new NotFoundException("Project " + projectId +" was not found");
    }

    @Transactional
    @Override
    public TaskDTO editTask(Integer taskId, Integer projectId, TaskUpdateRequest taskRequest,  String username, boolean isAdmin) {
        TaskId taskKey = new TaskId(taskId, projectId);
		Optional<Task> optTask = taskRepository.findById(taskKey);
        Optional<Project> optProject = projectRepository.findById(projectId);
        if(optProject.isPresent()) {
            Project project = optProject.get();
            if(optTask.isPresent()) {
                if(isAdmin || projectService.validateIfBelongsToProjectOwnerFromProject(username, optProject.get())){
                    if(!project.getState().equals(ProjectStatus.disabled)){
                        Task task = optTask.get();
                        task.setDescription(taskRequest.getDescription());
                        task.setDuration(taskRequest.getDuration());
                        task.setState(taskRequest.getState());
                        task.setType(taskRequest.getType());
                        projectRepository.updateUsedTimeInProject((taskRequest.getDuration() - project.getUsedTime()), projectId);
        
                        Task newTask = taskRepository.save(task);
                        return convertTaskToDTO(newTask);
                    }
                    throw new UnsupportedOperationException("Can't update the task because the project is disabled"); 
                }
                throw new UnsupportedOperationException("Can't edit the task because you don't belong to this project");
            }
            throw new NotFoundException("Task with project id " + projectId + " and task id "+taskId+" was not found");
        }
        throw new NotFoundException("Project with id " + projectId + " was not found");

    }


    
}
