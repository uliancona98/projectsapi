package com.projectsapi.demo.repository;
import java.util.List;

import com.projectsapi.demo.model.Project;
import com.projectsapi.demo.model.Task;
import com.projectsapi.demo.model.TaskId;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
@Repository
public interface TaskRepository extends JpaRepository<Task, TaskId>, JpaSpecificationExecutor<Project> {
    @Query(value = "SELECT * FROM tasks WHERE id_project = ?1", nativeQuery = true)
    List<Task> findTaskByProject(Integer projectId);
}

