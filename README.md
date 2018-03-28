# Football teams service

The football teams service is a restfull web service to mnage information about football teams that can be accessed via http.

## Software used

- [Spring Boot](https://projects.spring.io/spring-boot/) - The framework used
- [Maven](https://maven.apache.org/) - Dependency Management
- [JUnit](https://junit.org/junit4/) - Unit testing
- [Logback](https://logback.qos.ch/) - Logging
- [Git](https://git-scm.com/) - Source control
- [Tomcat](http://tomcat.apache.org/) - Embedded web server
- [H2](http://www.h2database.com/html/main.html) - In-memory database


### Run unit tests
```sh
From the project root
$ mvn test
builds and runs the unit tests
```

### Build and package
```sh
From the project root
$ mvn clean install
builds and packages into a fat jar
```

### Build and package
```sh
From the project root/target
$ java -jar footballteams-0.0.1-SNAPSHOT.jar
starts the application on localhost:8080
```

### Service endpoints
Create a team
```sh
Post 
/rest/teams/create to create a team
sample json: {"name":"Arsenal","city":"London","owner":"Dave Smith","stadiumCapacity": 55000, "competition":"FA Cup","numberOfPlayers": 35}
```
Retrieve details for a named team
```sh
Get
/getByName/{name}
```
Retrieve details of all teams
```sh
Get
/listAll
```
Retrieve details of all teams sorted by stadium capacity
```sh
Get
/listAllByCapacity
```
