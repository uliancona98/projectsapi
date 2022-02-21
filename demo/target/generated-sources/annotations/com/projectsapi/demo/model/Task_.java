package com.projectsapi.demo.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Task.class)
public abstract class Task_ {

	public static volatile SingularAttribute<Task, Double> duration;
	public static volatile SingularAttribute<Task, String> description;
	public static volatile SingularAttribute<Task, TaskStatus> state;
	public static volatile SingularAttribute<Task, TaskType> type;
	public static volatile SingularAttribute<Task, TaskId> taskId;

	public static final String DURATION = "duration";
	public static final String DESCRIPTION = "description";
	public static final String STATE = "state";
	public static final String TYPE = "type";
	public static final String TASK_ID = "taskId";

}

