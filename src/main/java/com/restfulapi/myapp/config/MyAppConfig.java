package com.restfulapi.myapp.config;

import java.util.List;
import javax.sql.DataSource;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import org.apache.commons.dbcp2.BasicDataSource;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("com.restfulapi.myapp")
@PropertySource("classpath:properties/myapp.properties")
@SuppressWarnings("deprecation")
public class MyAppConfig extends WebMvcConfigurerAdapter {

	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		return new MappingJackson2HttpMessageConverter(
				Jackson2ObjectMapperBuilder.json().indentOutput(true).build());
	}
	
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(0, mappingJackson2HttpMessageConverter());
	}
	
	@Bean
	public DataSource dataSource(@Value("${database.driverClassName}") String driverClassName,
								 @Value("${database.url}") String url,
								 @Value("${database.username}") String username,
								 @Value("${database.password}") String password,
								 @Value("${cp.maxTotal}") int maxTotal,
								 @Value("${cp.maxIdle}") int maxIdle,
								 @Value("${cp.minIdle}") int minIdle,
								 @Value("${cp.maxWaitMillis}") long millis) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setMaxTotal(maxTotal);
		dataSource.setMaxIdle(maxIdle);
		dataSource.setMinIdle(minIdle);
		dataSource.setMaxWaitMillis(millis);
		return dataSource;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("properties/messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
}
