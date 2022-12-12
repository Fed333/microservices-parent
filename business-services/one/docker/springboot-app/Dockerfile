FROM openjdk:8

VOLUME /tmp

LABEL business-services="onse"
LABEL description="Elaborated one module using servo metrics."

COPY ./one-0.0.1-SNAPSHOT.jar /one-0.0.1-SNAPSHOT.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/one-0.0.1-SNAPSHOT.jar"]