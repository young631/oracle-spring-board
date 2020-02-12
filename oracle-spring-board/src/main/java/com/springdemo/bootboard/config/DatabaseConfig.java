package com.springdemo.bootboard.config;


import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

//lecacy 방식에서는 root-context.xml 파일에 등록하는 내용
@Configuration
@PropertySource("classpath:/application.properties")
public class DatabaseConfig {
	
	@Bean // <bean id="hikariConfig" class="HikariConfig" ... />
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		HikariConfig config = new HikariConfig();

		return config;		
	}	
	@Bean
	public DataSource dataSource() {
		DataSource dataSource = new HikariDataSource(hikariConfig());
		return dataSource;
	}
	
	/////////////////////////////////////////////////
	
	@Bean
	@ConfigurationProperties(prefix="mybatis.configuration")
	public org.apache.ibatis.session.Configuration mybatisConfig() {		
		return new org.apache.ibatis.session.Configuration();
	}
	
	@Bean // <bean id="sqlSessionFactory" class="SqlSessionFactoryBean" ... />
	public SqlSessionFactory sqlSessionFactory() 
			throws Exception {		
		
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		
//		org.apache.ibatis.session.Configuration configuration = 
//				new org.apache.ibatis.session.Configuration();
//		configuration.setMapUnderscoreToCamelCase(true);
//		factoryBean.setConfiguration(configuration);
		
		factoryBean.setConfiguration(mybatisConfig());
		
		factoryBean.setDataSource(dataSource());
		
		return factoryBean.getObject();//SqlSessionFactory 반환
		
	}

}









