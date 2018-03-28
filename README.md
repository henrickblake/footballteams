# footballteams
Football teams service

Service is built as a Spring Boot project

Maven
Logging - logback
Unit testing - JUnit/Mockito
In-memory database - H2

to test: mvn test from the project root
to build: mvn clean package fro the project root
to run locally: java -jar footballteams-0.0.1-SNAPSHOT.jar from root/target

URL to Cloud Foundry deployed version: https://footballteams.cfapps.io/

Service endpoints:
Post /rest/teams/create json example: {"name":"Arsenal","city":"London","owner":"Dave Smith","stadiumCapacity": 55000, "competition":"FA Cup","numberOfPlayers": 85}
Get  /getByName/{name}
Get  /listAll
Get  /listAllByCapacity

Add a Readme.md which contains a brief statement of why you used the tools or libraries
as well as any instructions about how to start and use the app. 
