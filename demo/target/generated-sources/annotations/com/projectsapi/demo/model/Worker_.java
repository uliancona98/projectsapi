package com.projectsapi.demo.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Worker.class)
public abstract class Worker_ {

	public static volatile SingularAttribute<Worker, String> lastName;
	public static volatile SingularAttribute<Worker, String> secondLastName;
	public static volatile SingularAttribute<Worker, String> name;
	public static volatile SingularAttribute<Worker, Integer> id;
	public static volatile SingularAttribute<Worker, String> username;

	public static final String LAST_NAME = "lastName";
	public static final String SECOND_LAST_NAME = "secondLastName";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String USERNAME = "username";

}

