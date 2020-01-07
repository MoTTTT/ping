FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} ping.jar
ENTRYPOINT ["java","-jar","/ping.jar"]
