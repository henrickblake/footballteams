package com.hblake.footballteams;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FootballTeamsApplication {

	private static final Logger logger = LoggerFactory.getLogger(FootballTeamsApplication.class);	
	public static void main(String[] args) {
		SpringApplication.run(FootballTeamsApplication.class, args);
		logger.info("Application started");
	}
}
