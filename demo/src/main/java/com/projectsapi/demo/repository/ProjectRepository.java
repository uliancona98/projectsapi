package com.projectsapi.demo.repository;

import java.util.List;
import java.util.Optional;

import com.projectsapi.demo.model.Project;
import com.projectsapi.demo.model.Worker;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project, Integer>, JpaSpecificationExecutor<Project> {

    Page<Project> findAll(Specification<Project> projectSpecification, Pageable pageable);
    
    List<Project> findByProjectOwner(Worker projectOwner);
    //Page<Project> findByProjectOwner(Specification<Project> projectSpecification, Pageable pageable, Worker projectOwner);
    Optional<Project> findByName(String name);

    
    @Modifying
    @Query("UPDATE Project p set p.usedTime=p.usedTime+?1 where p.id=?2")
    void updateUsedTimeInProject(Double usedTime, Integer projectId);

}

