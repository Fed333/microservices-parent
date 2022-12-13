FROM openjdk:8

VOLUME /tmp

LABEL paltform-services="discovery"
LABEL description="Eureka server for registering client microservices"

COPY ./discovery-0.0.1-SNAPSHOT.jar /discovery-0.0.1-SNAPSHOT.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "/discovery-0.0.1-SNAPSHOT.jar"]