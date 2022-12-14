# MICROSERVICES PARENT
- - -
#### Demonstrative project within EPAM Java Global MP_UA_2022.</br>This is to introduce you to the common microservice technologies</br>
- - -
### Used technologies:
* service discovery;
* apigateway;
* configuration management;
* rest services;
* metrics aggregation;
* containerization.
- - -
### Elaborated tools and modules:
* Eureka Server;
* Zuulserver;
* Archaius;
* Spring Boot;
* Netflix Servo metrics;
* Docker Toolbox.
- - -
## The application consists from separate maven modules with following structure:</br>
* microservices-parent:
* * business-services:
* * * common
* * * one
* * * two
* * * two-api
* * platform-services:
* * * api-gateway
* * * discovery
- - -
## Launch microservices.
### All web services are containerized using docker. </br> Containers are configured in "microservices-parent/platform-services/docker-compose.yml" file. </br> To get application up and running use "docker-compose up --build" command.<br>
- - -
### For more details see present README.md in elaborated modules.
- - -