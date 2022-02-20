package com.projectsapi.demo.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{
	
	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		//dataSource.setUrl("jdbc:postgresql://localhost:5432/projectsapi");
		dataSource.setUrl("jdbc:postgresql://db:5432/projectsapi");
		dataSource.setUsername("postgres");
		dataSource.setPassword("root");
		return dataSource;
	}
}
