FROM openjdk:8

VOLUME /tmp

LABEL paltform-services="api-gateway"
LABEL description="Zuul server for proxying http requests to microservice's endpoints."

COPY ./zuulserver-0.0.1-SNAPSHOT.jar /zuulserver-0.0.1-SNAPSHOT.jar

EXPOSE 8765

ENTRYPOINT ["java", "-jar", "/zuulserver-0.0.1-SNAPSHOT.jar"]