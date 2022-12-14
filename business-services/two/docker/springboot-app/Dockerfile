FROM openjdk:8

VOLUME /tmp

LABEL business-services="two"
LABEL description="Elaborated two module using servo metrics."

COPY ./two-0.0.1-SNAPSHOT.jar /two-0.0.1-SNAPSHOT.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/two-0.0.1-SNAPSHOT.jar"]