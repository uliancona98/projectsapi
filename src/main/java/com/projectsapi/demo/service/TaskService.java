package com.projectsapi.demo.service;
import com.projectsapi.demo.dto.TaskDTO;
import java.util.List;

import com.projectsapi.demo.dto.request.TaskRequest;
import com.projectsapi.demo.dto.request.TaskUpdateRequest;


public interface TaskService {
	
	List<TaskDTO> findAll(Integer idProject,  String username, boolean isAdmin);

	TaskDTO findTaskById(Integer projectId, Integer taskId,  String username, boolean isAdmin);

	TaskDTO saveTask(Integer projectId, TaskRequest taskRequest,  String username, boolean isAdmin);

	TaskDTO editTask(Integer taskId, Integer projectId, TaskUpdateRequest taskRequest,  String username, boolean isAdmin);    
}
