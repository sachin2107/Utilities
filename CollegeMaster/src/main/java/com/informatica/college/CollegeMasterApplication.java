package com.informatica.college;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.informatica.college.configprops.CustomServerProperties;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties
public class CollegeMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollegeMasterApplication.class, args);
	}

}
