FROM openjdk:17-jdk-slim

VOLUME /tmp

COPY apiEncryptionKey.jks apiEncryptionKey.jks
COPY ./build/libs/configuration-server-0.0.1-SNAPSHOT.jar configuration-service.jar

ENTRYPOINT ["java", "-jar", "configuration-service.jar"]