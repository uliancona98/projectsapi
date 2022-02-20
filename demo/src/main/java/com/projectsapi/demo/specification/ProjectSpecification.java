package com.projectsapi.demo.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.projectsapi.demo.model.Project;

import org.springframework.data.jpa.domain.Specification;

public class ProjectSpecification implements Specification<Project>{ 
    private static final long serialVersionUID = 1L;
    private SearchCriteria searchCriteria;

    public ProjectSpecification(SearchCriteria searchCriteria){
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if(searchCriteria.getOperation().equals("∼")){
            return criteriaBuilder.like(root.get(searchCriteria.getKey()),"%"+searchCriteria.getValue().toString()+"%");
        }
        return null;
    }
    
}
