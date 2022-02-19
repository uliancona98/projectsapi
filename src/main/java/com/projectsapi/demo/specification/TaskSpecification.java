package com.projectsapi.demo.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.projectsapi.demo.model.Task;

import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification implements Specification<Task>{ 
    private static final long serialVersionUID = 1L;
    private SearchCriteria searchCriteria;

    public TaskSpecification(SearchCriteria searchCriteria){
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if(searchCriteria.getOperation().equals("∼")){
            return criteriaBuilder.like(root.get(searchCriteria.getKey()),"%"+searchCriteria.getValue().toString()+"%");
        }
        return null;
    }
    
}
