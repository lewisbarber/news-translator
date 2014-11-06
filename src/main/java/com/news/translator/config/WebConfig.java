package com.news.translator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("com.news.translator")
public class WebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	Environment env;

	@Bean
	public InternalResourceViewResolver viewResolver() {

		InternalResourceViewResolver resolver = new InternalResourceViewResolver();

		resolver.setPrefix(env.getProperty("spring.view.prefix"));
		resolver.setSuffix(env.getProperty("spring.view.suffix"));

		return resolver;

	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler(env.getProperty("resource.handler")).addResourceLocations(env.getProperty("resource.locations"));

	}

}