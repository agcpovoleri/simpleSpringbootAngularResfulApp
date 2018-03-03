Serverside Spring boot RESTFUL application.

Requires: 
  - Postgres 9.4 database: forumapp (username: forumapp/ pass: 123456)
  - Redis Server

This is a Gradle project that runs Springboot within a container and available at port 8081 (configurable)
This application receives REST connections configured based on the security standarts.

Frameworks:
  - Springboot 
  - Liquibase: Database changelog
  - Redis: Session management
  - Swagger: API documentation
  
  
