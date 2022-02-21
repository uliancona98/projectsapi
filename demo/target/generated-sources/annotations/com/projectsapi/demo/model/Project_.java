package com.projectsapi.demo.model;

import java.sql.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Project.class)
public abstract class Project_ {

	public static volatile SingularAttribute<Project, Double> expectedTime;
	public static volatile SingularAttribute<Project, Double> usedTime;
	public static volatile SetAttribute<Project, Worker> developers;
	public static volatile SingularAttribute<Project, String> name;
	public static volatile SingularAttribute<Project, String> description;
	public static volatile SingularAttribute<Project, Worker> projectOwner;
	public static volatile SingularAttribute<Project, Integer> id;
	public static volatile SingularAttribute<Project, ProjectStatus> state;
	public static volatile SingularAttribute<Project, Date> startDate;

	public static final String EXPECTED_TIME = "expectedTime";
	public static final String USED_TIME = "usedTime";
	public static final String DEVELOPERS = "developers";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String PROJECT_OWNER = "projectOwner";
	public static final String ID = "id";
	public static final String STATE = "state";
	public static final String START_DATE = "startDate";

}

