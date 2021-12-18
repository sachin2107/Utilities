package com.informatica.college.configprops;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "serverpop")
public class CustomServerProperties {

	private int port;

	private String hostname;

	private String from;

	private String to;

	private List<Object> servers1;

	private Map<String, String> countryCapitals;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public List<Object> getServers1() {
		return servers1;
	}

	public void setServers1(List<Object> servers1) {
		this.servers1 = servers1;
	}

	public Map<String, String> getCountryCapitals() {
		return countryCapitals;
	}

	public void setCountryCapitals(Map<String, String> countryCapitals) {
		this.countryCapitals = countryCapitals;
	}

}
