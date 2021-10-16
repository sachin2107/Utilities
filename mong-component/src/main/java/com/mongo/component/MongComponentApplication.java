package com.mongo.component;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class MongComponentApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongComponentApplication.class, args);
	}

	/*
	 * @Bean ConfigurableServletWebServerFactory webServerFactory() {
	 * TomcatServletWebServerFactory tomcatServletWebServerFactory = new
	 * TomcatServletWebServerFactory(); return tomcatServletWebServerFactory; }
	 */
}	
