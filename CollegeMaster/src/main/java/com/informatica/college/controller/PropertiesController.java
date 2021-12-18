package com.informatica.college.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.informatica.college.configprops.CustomServerProperties;

@RestController
public class PropertiesController {

	@Autowired
	CustomServerProperties customServerProps;
	
	@Value("#{'${javadevjournal.init.keys.new.delimiter}'.split(';')}")
	private List<Integer> newKeys;
	
	@Value("${my.list.values}")
	private List<String> myList;
	
	@Value("#{${my.map.values}}")
	private Map<String,String> myMap;
	
	@GetMapping(value = {"/serverproperties"})
	public ResponseEntity<String> getServerProps(){
		System.out.println(customServerProps.getPort());
		System.out.println("List of servers = "+customServerProps.getServers1());
		System.out.println("Map of country capitals="+customServerProps.getCountryCapitals());
		
		System.out.println(myList.toString());
		System.out.println("MyMap = "+myMap.get("India"));
		System.out.println("NewKeys = "+newKeys.toString());
		return new ResponseEntity<String>(String.valueOf(customServerProps.getTo()), HttpStatus.OK);
	}
} 
