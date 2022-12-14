FROM openjdk:8

VOLUME /tmp

LABEL business-services="common"
LABEL description="Elaborated demo archaius module."

COPY ./common-0.0.1-SNAPSHOT.jar /common-0.0.1-SNAPSHOT.jar

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "/common-0.0.1-SNAPSHOT.jar"]