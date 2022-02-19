package com.projectsapi.demo.repository;

import java.util.Optional;

import com.projectsapi.demo.model.Project;
import com.projectsapi.demo.model.Worker;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
@Repository
public interface WorkerRepository extends PagingAndSortingRepository<Worker, Integer>, JpaSpecificationExecutor<Project> {
    Optional<Worker>findById(Integer id);
    Optional<Worker>findByUsername(String username);

}

